import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.math.BigInteger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.util.Arrays;
import java.io.IOException;

public class KeyStringConverter {

	/**
	 * Returns the public key object associated with the public key string
	 *
	 * @param  key the public key in string form
	 * @return the public key object
	 */
	public PublicKey stringToPublicKey(String key) throws GeneralSecurityException {
		PublicKey ret = null;

		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] data = decoder.decodeBuffer(key);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
			KeyFactory fact = KeyFactory.getInstance("EC");
			ret = fact.generatePublic(spec);
		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		}

		return ret;
	}

	/**
	 * Returns the private key object associated with the private key string
	 *
	 * @param  key the private key in string form
	 * @return the private key object
	 */
	public PrivateKey stringToPrivateKey(String key) throws GeneralSecurityException {
		PrivateKey ret = null;

		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] data = decoder.decodeBuffer(key);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(data);
			KeyFactory fact = KeyFactory.getInstance("EC");
			PrivateKey priv = fact.generatePrivate(keySpec);
			Arrays.fill(data, (byte) 0);
			ret = priv;
		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		}

		return ret;
	}

	/**
	 * Returns a string version of the public key object
	 *
	 * @param  key the public key object
	 * @return the public key in string form
	 */
	public String publicKeyToString(PublicKey key) {
		String ret = null;

		try {
			KeyFactory fact = KeyFactory.getInstance("EC");
			X509EncodedKeySpec spec = fact.getKeySpec(key, X509EncodedKeySpec.class);
			BASE64Encoder encoder = new BASE64Encoder();
			ret = encoder.encodeBuffer(spec.getEncoded());
		} catch (GeneralSecurityException e) {
			System.err.println("Caught GeneralSecurityException: " + e.getMessage());
		}

		return ret;
	}

	/**
	 * Returns a string version of the private key object
	 *
	 * @param  key the private key object
	 * @return the private key in string form
	 */
	public String privateKeyToString(PrivateKey key) {
		String ret = null;

		try {
			KeyFactory fact = KeyFactory.getInstance("EC");
			PKCS8EncodedKeySpec spec = fact.getKeySpec(key, PKCS8EncodedKeySpec.class);
			byte[] packed = spec.getEncoded();
			BASE64Encoder encoder = new BASE64Encoder();
			ret = encoder.encodeBuffer(packed);
			Arrays.fill(packed, (byte) 0);
		} catch (GeneralSecurityException e) {
			System.err.println("Caught GeneralSecurityException: " + e.getMessage());
		}

		return ret;
	}

}
