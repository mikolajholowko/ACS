package app.application;

import app.application.model.Employee;
import app.application.model.Qr;
import app.application.model.dto.EmployeeDto;
import app.application.model.dto.Role;
import app.application.repository.EmployeeRepository;
import app.application.repository.QrRespository;
import app.application.service.QrService;
import com.google.zxing.qrcode.encoder.QRCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class Init implements InitializingBean {

    private final QrService qrService;
    private final EmployeeRepository employeeService;
    private final QrRespository qrRespository;

    @Override
    public void afterPropertiesSet() {

        Employee employeeDto = employeeService.save(new Employee("Mikołaj", "Hołowko", "mholowko", "mholowko", "email", Role.ROLE_ADMIN));

        Qr qr = qrService.generateQrCodeForEmployee(Employee.mapToDto(employeeDto));

        System.out.println(Arrays.toString(qrRespository.findAll().get(0).getQrImage()));

        File outputFile = new File("outputFile.jpg");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(qr.getQrImage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
