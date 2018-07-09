package nz.ac.auckland.SPARER;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class KeyDerivationFunction {
	
	private static final String _algorithm = "PBKDF2WithHmacSHA512";

	public byte[] hashPassword(final char[] password, final byte[] salt, final int iterations, final int keyLength ) {

		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance(_algorithm);
			PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
			SecretKey key = factory.generateSecret( spec );
			byte[] result = key.getEncoded( );
			return result;
		} catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
			throw new RuntimeException(e);
		}
	}
	
}
