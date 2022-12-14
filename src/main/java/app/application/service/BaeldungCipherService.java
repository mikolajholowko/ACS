package app.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

@Service
@Slf4j
public class BaeldungCipherService {


    @SneakyThrows
    public void rsa() {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();


        PrivateKey privateKey = pair.getPrivate();
        System.out.println("private");
        System.out.println(new String(privateKey.getEncoded(), StandardCharsets.UTF_8));
        PublicKey publicKey = pair.getPublic();
        System.out.println("public");
        System.out.println(new String(publicKey.getEncoded(), StandardCharsets.UTF_8));

        try (
                FileOutputStream fos = new FileOutputStream("public1.key")) {
            fos.write(publicKey.getEncoded());
        }

        File publicKeyFile = new File("public1.key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        keyFactory.generatePublic(publicKeySpec);

        log.info(Arrays.toString(privateKey.getEncoded()));
        log.info(Arrays.toString(publicKey.getEncoded()));

        String secretMessage = "Baeldung secret message ";

        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);

        String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        try (
                FileOutputStream fos = new FileOutputStream("private1.key")) {
            fos.write(privateKey.getEncoded());
        }


        byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
        String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);

        System.out.println(decryptedMessage);

    }


}
