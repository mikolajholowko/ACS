package app.application.cipher;

import app.application.exception.ACSException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class ACSCipherUtils {

    private final PrivateKey privateKey;

    public byte[] encode(String messageToEncode, PublicKey publicKey) {
        try {
            Cipher encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] secretMessageBytes = messageToEncode.getBytes(StandardCharsets.UTF_8);
            return encryptCipher.doFinal(secretMessageBytes);
        } catch (Exception e) {
            throw new ACSException("Unable to encode message: [" + messageToEncode + "] " + e);
        }
    }

    public String decode(byte[] messageToDecode) {
        try {
            Cipher decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedMessageBytes = decryptCipher.doFinal(messageToDecode);
            return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new ACSException("Unable to decode message: [" + messageToDecode + "] " + e);
        }
    }


}
