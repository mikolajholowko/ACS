package app.application.cipher;

import app.application.exception.ACSException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class ACSCipherConfiguration {

    @Value("${RS256.private.key}")
    private byte[] privateKey;
    @Value("${RS256.public.key}")
    private byte[] publicKey;

    @Bean
    public PrivateKey privateKey() {
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKey);
        try {
            return keyFactory().generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new ACSException("Unable to create privateKey: " + e);
        }
    }

    @Bean
    public PublicKey publicKey() {
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKey);
        try {
            return keyFactory().generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new ACSException("Unable to create publicKey: " + e);
        }
    }

    @Bean
    public KeyFactory keyFactory() {
        try {
            return KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new ACSException("Unable to create KeyFactory: " + e);
        }
    }


}
