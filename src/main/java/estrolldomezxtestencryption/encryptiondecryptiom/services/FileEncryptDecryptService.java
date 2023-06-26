package estrolldomezxtestencryption.encryptiondecryptiom.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

@Service
public class FileEncryptDecryptService {

    private static final String ALGORITHM = "AES";
    private static final String PROVIDER = "BC";

    private static final String SECRET_KEY = "YourSecretKey";

    private static SecretKey secretKey;

    public FileEncryptDecryptService() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public String encryptData(String data) throws NoSuchPaddingException, NoSuchAlgorithmException,
            NoSuchProviderException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM, PROVIDER);
        SecureRandom secureRandom = new SecureRandom(SECRET_KEY.getBytes());

        keyGenerator.init(256, secureRandom);
        secretKey = keyGenerator.generateKey();

        Cipher cipher = Cipher.getInstance(ALGORITHM + "/ECB/PKCS5Padding", PROVIDER);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptData(String encryptedData) throws NoSuchPaddingException, NoSuchAlgorithmException,
            NoSuchProviderException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM, PROVIDER);
        SecureRandom secureRandom = new SecureRandom(SECRET_KEY.getBytes());
        keyGenerator.init(256, secureRandom);
        // SecretKey secretKey = keyGenerator.generateKey();

        Cipher cipher = Cipher.getInstance(ALGORITHM + "/ECB/PKCS5Padding", PROVIDER);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

        return new String(decryptedBytes);
    }
}
