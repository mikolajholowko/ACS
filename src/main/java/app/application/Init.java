package app.application;

import app.application.cipher.ACSCipherUtils;
import app.application.model.Employee;
import app.application.model.Qr;
import app.application.model.dto.EmployeeDto;
import app.application.model.dto.Role;
import app.application.repository.EmployeeRepository;
import app.application.repository.QrRespository;
import app.application.service.BaeldungCipherService;
import app.application.service.CipherService;
import app.application.service.EmployeeService;
import app.application.service.QrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class Init implements InitializingBean {

    private final QrService qrService;
    private final EmployeeService employeeService;
    private final QrRespository qrRespository;
    private final BaeldungCipherService baeldungCipherService;

    private final ACSCipherUtils acsCipherUtils;

    private final PublicKey publicKey;


    @Override
    public void afterPropertiesSet() {

        byte[] asdfasdf = acsCipherUtils.encode("ASDFASDF", publicKey);

        log.info(Arrays.toString(asdfasdf));

        String decode = acsCipherUtils.decode(asdfasdf);

        log.info(decode);

        baeldungCipherService.rsa();
        doSmth();

    }


    private void doSmth() {
        EmployeeDto employeeDto = employeeService.save(new EmployeeDto("Mikołaj", "Hołowko", "1234", Role.ROLE_ADMIN,"mholowko"));
        EmployeeDto employeeDto1 = employeeService.save(new EmployeeDto("Filip", "Ratajczak", "12345", Role.ROLE_ADMIN,"mholowko"));
        EmployeeDto employeeDto2 = employeeService.save(new EmployeeDto("Jan", "Debilski", "debilski@gmail.com", Role.ROLE_EMPLOYEE,"mholowko"));

//
//        Qr qr = qrService.generateQrCodeForEmployee(Employee.mapToDto(employeeDto));
//
//        System.out.println(Arrays.toString(qrRespository.findAll().get(0).getQrImage()));
//
//        File outputFile = new File("outputFile.jpg");
//        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
//            outputStream.write(qr.getQrImage());
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        cipherService.generateKeys();

//        String dupa = cipherService.encode("dupa");
//        String decode = cipherService.decode(dupa);
//        System.out.println(decode);

//        File outputFile = new File("outputFile.jpg");
//        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
//             outputStream.write(qr.getQrImage());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    }

}
