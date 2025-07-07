package com.skyproton.kyc_backend.tools;

import com.password4j.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class Crypto {
    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String INIT_VECTOR = "encryptionIntVec";
    private final Config config;

    @Autowired
    public Crypto(Config config) {
        this.config = config;
    }

    public String hashPassword(String data) {
        return Password.hash(data)
                .withBcrypt()
                .getResult();
    }

    public boolean validatePassword(String password, String hashedPassword) {
        return Password.check(password, hashedPassword)
                .withBcrypt();
    }

    public String encrypt(String data) {
        try {
            IvParameterSpec iv = new IvParameterSpec("encryptionIntVec".getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKey = new SecretKeySpec(
                    config.getKey().getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting: " + e.getMessage());
        }
    }

    public String decrypt(String encryptedData) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKey = new SecretKeySpec(
                    config.getKey().getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting: " + e.getMessage());
        }
    }
}
