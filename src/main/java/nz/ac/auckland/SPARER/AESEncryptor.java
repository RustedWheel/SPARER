package nz.ac.auckland.SPARER;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptor {
	
	private static final String ALGORITHM = "AES";
	
    public String encrypt(String data, String key) {
        try {
        	
            SecretKeySpec sspec = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, sspec);
            byte[] encryptedValue = c.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedValue);
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
        	throw new RuntimeException(e);
        }
    }

    public String decrypt(String encryptedData, String key) {
        try {
        	
            SecretKeySpec sspec = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, sspec);
            byte[] decordedValue = Base64.getDecoder().decode(encryptedData.getBytes());
            byte[] decryptedData = c.doFinal(decordedValue);
            return new String(decryptedData);
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
        	throw new RuntimeException(e);
        }
    }
}
