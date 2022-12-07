package app.application.service;


import app.application.model.Employee;
import app.application.model.Qr;
import app.application.model.dto.EmployeeDto;
import app.application.model.dto.QrDto;
import app.application.repository.EmployeeRepository;
import app.application.repository.QrRespository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QrService {
    private final QrRespository qrRespository;
    private final ObjectMapper objectMapper;
    private final EmployeeRepository employeeRepository;


    private final EmployeeService employeeService;

    public QrDto getById(UUID id) {
        Optional<Qr> qr = qrRespository.findById(id);
        if (qr.isPresent()) {
            return Qr.mapToDto(qr.get());
        } else {
            throw new RuntimeException("User with id: [" + id + "] not found.");
        }
    }

    public List<QrDto> getAll() {
        return qrRespository.findAll().stream().map(Qr::mapToDto).collect(Collectors.toList());
    }

    public void deleteById(UUID id) {
        employeeRepository.findById(id).ifPresent(employee -> employeeRepository.deleteById(id));
    }

    public QrDto save(QrDto qrDto) {
        return Qr.mapToDto(qrRespository.save(mapToEntity(qrDto)));
    }

    public Qr generateQrCodeForEmployee(EmployeeDto employeeDto) {
        ByteArrayOutputStream stream;
        long timestamp = System.currentTimeMillis();

        try {
            stream = QRCode
                    .from(objectMapper.writeValueAsString(new QrDto(employeeDto.getId(), timestamp)))
                    .withSize(250, 250)
                    .stream();
            ByteArrayInputStream bais = new ByteArrayInputStream(stream.toByteArray());
            BufferedImage bufferedImage = ImageIO.read(bais);
            ImageIO.write(bufferedImage, "png", new File("image.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return qrRespository.save(new Qr(stream.toByteArray(), timestamp, Employee.mapToEntity(employeeDto)));
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

    private LocalDateTime getDateTimeFromTimestamp(long timestamp) {
        if (timestamp == 0)
            return null;
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone
                .getDefault().toZoneId());
    }

    //TODO return proper object instead of int
    public int qrValidation(QrDto qrDto) {
        boolean isValidCode = (getDateTimeFromTimestamp(qrDto.getTimestamp())).isBefore(LocalDateTime.now().minusDays(1));
        System.out.println(isValidCode);
        if (isValidCode) {
            EmployeeDto employeeDto = employeeService.getById(qrDto.getEmployeeId());
            if (!Objects.isNull(employeeDto) && employeeDto.getId() == qrDto.getEmployeeId()) {
                if (Role.valueOf(qrDto.getRole()).getValue() >= employeeDto.getRole().getValue()) {
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
}
