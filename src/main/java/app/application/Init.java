package app.application;

import app.application.model.Employee;
import app.application.model.dto.Role;
import app.application.repository.EmployeeRepository;
import app.application.repository.QrRespository;
import app.application.service.BaeldungCipherService;
import app.application.service.CipherService;
import app.application.service.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
@RequiredArgsConstructor
public class Init implements InitializingBean {

    private final QrService qrService;
    private final EmployeeRepository employeeService;
    private final QrRespository qrRespository;
    private final CipherService cipherService;
    private final BaeldungCipherService baeldungCipherService;


    @Override
    public void afterPropertiesSet() throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
//
        Employee employeeDto = employeeService.save(new Employee("Mikołaj", "Hołowko", "mholowko", "mholowko", "email", Role.ROLE_ADMIN));
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

        baeldungCipherService.rsa();

    }

//    public void cipherJWTValue() throws NoSuchAlgorithmException, FileNotFoundException {
//        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
//        generator.initialize(2048);
//        KeyPair pair = generator.generateKeyPair();
//
//
//        PrivateKey privateKey = pair.getPrivate();
//        PublicKey publicKey = pair.getPublic();
//
//
//
//        System.out.println(Arrays.toString(privateKey.getEncoded()));
//        System.out.println(Arrays.toString(publicKey.getEncoded()));
//
//        try (FileOutputStream fos = new FileOutputStream("public.txt"))  {
//            fos.write(publicKey.getEncoded());
//           // fos.write(privateKey.getEncoded());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }


}
