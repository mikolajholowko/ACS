package app.application.service;


import app.application.exception.ACSException;
import app.application.model.Employee;
import app.application.model.Entrance;
import app.application.model.Qr;
import app.application.model.Room;
import app.application.model.dto.*;
import app.application.repository.EmployeeRepository;
import app.application.repository.EntranceRepository;
import app.application.repository.QrRespository;
import app.application.repository.RoomRepository;
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

    private final RoomRepository roomRepository;

    private final EntranceRepository entranceRepository;

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

    public ValidationResultDto qrValidation(QrDto qrDto) {
        boolean isValidCode = getDateTimeFromTimestamp(qrDto.getTimestamp()).isBefore(LocalDateTime.now().minusDays(1));
        if (isValidCode) {
            EmployeeDto employeeDto = Employee.mapToDto(employeeRepository.findById(qrDto.getEmployeeId())
                    .orElseThrow(() -> new ACSException("User with id: " + qrDto.getEmployeeId() + " does not found!")));
            Room room = roomRepository.findAll().stream()
                    .filter(e -> e.getRoomName().equals(qrDto.getRoomNumber()))
                    .findFirst()
                    .orElseThrow(() -> new ACSException("Room with number: " + qrDto.getRoomNumber() + " does not found!"));
            if (qrDto.getRole().getValue() >= room.getRoomAccessibility()) {
                entranceRepository.save(new Entrance(LocalDateTime.now(), LocalDateTime.now(), Employee.mapToEntity(employeeDto), room));
                return new ValidationResultDto(200, buildEmployeeValidationResultDependedOnStatus(200, qrDto));
            } else {
                return new ValidationResultDto(401, buildEmployeeValidationResultDependedOnStatus(200, qrDto));
            }
        } else {
            return new ValidationResultDto(404, buildEmployeeValidationResultDependedOnStatus(200, qrDto));
        }
    }


    private LocalDateTime getDateTimeFromTimestamp(long timestamp) {
        if (timestamp == 0)
            return null;
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone
                .getDefault().toZoneId());
    }

    EmployeeValidationResult buildEmployeeValidationResultDependedOnStatus(int status, QrDto qrDto) {

        if (status == 200) {
            return new EmployeeValidationResult(qrDto.getId(),
                    qrDto.getRole(),
                    "Dostęp do pomieszczenia " + qrDto.getRoomNumber() + " nadany.");
        } else if (status == 401) {
            return new EmployeeValidationResult(qrDto.getId(),
                    qrDto.getRole(),
                    "Niewystarczające uprawnienia do nadania dostępu do pokoju o numerze" + qrDto.getRoomNumber() + ".");
        } else {
            return new EmployeeValidationResult(qrDto.getId(),
                    qrDto.getRole(),
                    "Kod QR wygasł.");

        }


    }


}
