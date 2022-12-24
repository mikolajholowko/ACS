package app.application.service;


import app.application.exception.ACSException;
import app.application.model.Employee;
import app.application.model.Qr;
import app.application.model.dto.EmployeeDto;
import app.application.model.dto.QrDto;
import app.application.model.dto.Role;
import app.application.repository.EmployeeRepository;
import app.application.repository.QrRespository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QrService {
    private final QrRespository qrRespository;
    private final ObjectMapper objectMapper;
    private final EmployeeRepository employeeRepository;

    public byte[] getByEmployeeId(UUID employeeId) {
        return qrRespository.findByEmployeeId(employeeId)
                .map(Qr::getQrImage)
                .orElseThrow(() -> new RuntimeException("QR not found"));
    }

    public List<QrDto> getAll() {
        return qrRespository.findAll()
                .stream()
                .map(Qr::mapToDto)
                .collect(Collectors.toList());
    }

    public void deleteById(UUID id) {
        qrRespository.findById(id)
                .ifPresent(employee -> employeeRepository.deleteById(id));
    }

    public QrDto save(QrDto qrDto) {
        return Qr.mapToDto(qrRespository.save(mapToEntity(qrDto)));
    }

    @Transactional
    public Qr generateQrCodeForEmployee(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found!"));

        if (!Objects.isNull(employee.getQr())) {
            log.info("User with id: [{}] has already generated QR!", id);
            return employee.getQr();
        } else {
            long timestamp = System.currentTimeMillis();
            byte[] qrBytes = Try.of(() ->
                            QRCode.from(objectMapper.writeValueAsString(new QrDto(id, timestamp, employee.getRole())))
                                    .withSize(250, 250)
                                    .stream())
                    .onFailure(RuntimeException.class, (ex) -> log.error("Cannot create QR code for user with id: [{}]", id, ex))
                    .map(ByteArrayOutputStream::toByteArray)
                    .getOrElse(new byte[]{});
            Qr qr = new Qr(qrBytes, timestamp, employee);
            employee.setQr(qr);
            qrRespository.save(qr);
            return qr;
        }
    }

    @Transactional
    public Qr refreshQrForEmployee(UUID id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found!"));

        Qr qr = employee.getQr();

        long timestamp = System.currentTimeMillis();
        byte[] qrBytes = Try.of(() ->
                        QRCode.from(objectMapper.writeValueAsString(new QrDto(id, timestamp, employee.getRole())))
                                .withSize(250, 250)
                                .stream())
                .onFailure(RuntimeException.class, (ex) -> log.error("Cannot create QR code for user with id: [{}]", id, ex))
                .map(ByteArrayOutputStream::toByteArray)
                .getOrElse(new byte[]{});

        qr.setQrImage(qrBytes);
        employee.setQr(qr);
        qrRespository.save(qr);
        return qr;
    }


    public Qr mapToEntity(QrDto qrDto) {
        Employee employee;
        Optional<Employee> employeeOptional = employeeRepository.findById(qrDto.getEmployeeId());
        if (employeeOptional.isPresent()) {
            employee = employeeOptional.get();
        } else {
            throw new RuntimeException("EMPLOYEE NOT FOUND");
        }
        Qr qr = new Qr();
        qr.setId(qrDto.getId());
        qr.setTimestamp(qrDto.getTimestamp());
        qr.setEmployee(employee);
        return qr;
    }

    //TODO return proper object instead of just int
    public int qrValidation(QrDto qrDto) {
        boolean isValidCode = getDateTimeFromTimestamp(qrDto.getTimestamp()).isBefore(LocalDateTime.now().minusDays(1));
        if (isValidCode) {
            EmployeeDto employeeDto = Employee.mapToDto(employeeRepository.findById(qrDto.getEmployeeId())
                    .orElseThrow(() -> new ACSException("User with id: " + qrDto.getEmployeeId() + " does not found!")));
            if (employeeDto.getId() == qrDto.getEmployeeId()) {
                if (qrDto.getRole().getValue() >= employeeDto.getRole().getValue()) {
                    return 200;
                } else {
                    return 401;
                }
            } else {
                return 404;
            }
        } else {
            return 404;
        }
    }

    private LocalDateTime getDateTimeFromTimestamp(long timestamp) {
        if (timestamp == 0)
            return null;
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone
                .getDefault().toZoneId());
    }
}
