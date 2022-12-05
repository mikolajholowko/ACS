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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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


}
