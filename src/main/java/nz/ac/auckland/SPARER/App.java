package nz.ac.auckland.SPARER;
import org.apache.commons.codec.binary.Hex;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Hello world!
 *
 */
public class App {
	
	
	
	public static void main( String[] args ) {
		
		AESEncryptor encryptor = new AESEncryptor();
		
		// Data to be encrypted (EHR)
		String data = "Jack Huang";
		// Patient key
		String patientKey = "e68f3d2f8f0a3c1b6d84ca72796d052e";
		// EHR data encrypted with patient key
		String encryptedData =  encryptor.encrypt(data, patientKey);
		
		
		// Patient's password
		String password = "davidqi";
		
		// Random string generator to generate to generate salt
		SaltGenerator randomstring = new SaltGenerator(32, new SecureRandom());
		
		String salt = randomstring.nextString();
		
		System.out.println("Salt: " + salt);
		
        int iterations = 10000;
        int keyLength = 128;
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();

        KeyDerivationFunction kdf = new KeyDerivationFunction();
        
        // Use PBKDF2 key derivation function to stretch the patient's password
        byte[] hashedBytes = kdf.hashPassword(passwordChars, saltBytes, iterations, keyLength);
        
        // Patient's password key (PassKey)
        String patientPassKey = Hex.encodeHexString(hashedBytes);
        
        System.out.println("Patient's password key: " + patientPassKey);
        System.out.println("Hash size: " + patientPassKey.length());
        
        // Encrypt the patient key using the patient's password key (PassKey)
        String encryptedPKey = encryptor.encrypt(patientKey, patientPassKey);
        System.out.println("Encrypted patient key: " + encryptedPKey);
        
        // Decrypt the patient key using the patient's password key (PassKey)
        String decryptedPKey = encryptor.decrypt(encryptedPKey, patientPassKey);
        System.out.println("Decrypted patient key: " + decryptedPKey);
        
        // Decrypt the encrypted EHR data
        String recoveredData = encryptor.decrypt(encryptedData, decryptedPKey);
        System.out.println("Decrypted data: " + recoveredData);
        
        String HPPassKey = "e9eac4294b10e4017b494c28e48d5645";
        
        // Encrypt the patient key using the HP's password key (HP PassKey)
        String encryptedPKeyHP = encryptor.encrypt(patientKey, HPPassKey);
        System.out.println("Encrypted patient key: " + encryptedPKeyHP);
        
        // Decrypt the patient key using the HP's password key (HP PassKey)
        String decryptedPKeyHP = encryptor.decrypt(encryptedPKeyHP, HPPassKey);
        System.out.println("Decrypted patient key: " + decryptedPKeyHP);
        
        // Decrypt the encrypted EHR data
        String recoveredDataHP = encryptor.decrypt(encryptedData, decryptedPKeyHP);
        System.out.println("Decrypted data: " + recoveredDataHP);
	}

}
