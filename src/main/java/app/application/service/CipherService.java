package app.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.*;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor


public class CipherService {

    private final ObjectMapper objectMapper;
    @Value("${QRACS.private.key}")
    private String privateKey;
    @Value("${QRACS.public.key}")
    private String publicKey;


    public String encode(String messageToEncode) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {

        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, readPrivateKey());
        byte[] secretMessageBytes = messageToEncode.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
//        return Base64.getEncoder().encodeToString(encryptedMessageBytes);
        return "dupa;";
    }


    public String decode(String massageToDecode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, IOException {

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, readPublicKey());
        byte[] decryptedMessageBytes = decryptCipher.doFinal(massageToDecode.getBytes(StandardCharsets.UTF_8));
        String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        System.out.println(decryptedMessage);
        return decryptedMessage;
    }

    public PublicKey readPublicKey() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        File publicKey = new File("public.key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKey.toPath());

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(publicKeySpec);
    }

    public PrivateKey readPrivateKey() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        File privateKey = new File("private.key");
        byte[] privateKeyBytes = Files.readAllBytes(privateKey.toPath());

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return keyFactory.generatePrivate(privateKeySpec);
    }


}
