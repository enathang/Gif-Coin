import static org.junit.jupiter.api.Assertions.*;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.jupiter.api.Test;

class KeyStringConverterTest {

	@Test
	void testCorrectConversionBetweenPublicKeyAndString() throws GeneralSecurityException {
		User user1 = new User();
		KeyStringConverter conv = new KeyStringConverter();
		String keyString = conv.publicKeyToString(user1.getPublicKey());
		PublicKey publicKey = conv.stringToPublicKey(keyString);
		assertEquals(user1.getPublicKey(), publicKey);
	}
	
	@Test
	void testIncorrectConversionBetweenPublicKeyAndString() throws GeneralSecurityException {
		User user1 = new User();
		User user2 = new User();
		KeyStringConverter conv = new KeyStringConverter();
		String keyString = conv.publicKeyToString(user1.getPublicKey());
		PublicKey publicKey = conv.stringToPublicKey(keyString);
		assertNotEquals(user2.getPublicKey(), publicKey);
	}
	
	@Test
	void testCorrectConversionBetweenPrivateKeyAndString() throws GeneralSecurityException {
		User user1 = new User();
		KeyStringConverter conv = new KeyStringConverter();
		String keyString = conv.privateKeyToString(user1.secretKey);
		PrivateKey privateKey = conv.stringToPrivateKey(keyString);
		assertEquals(user1.secretKey, privateKey);
	}
	
	@Test
	void testIncorrectConversionBetweenPrivateKeyAndString() throws GeneralSecurityException {
		User user1 = new User();
		User user2 = new User();
		KeyStringConverter conv = new KeyStringConverter();
		String keyString = conv.privateKeyToString(user1.secretKey);
		PrivateKey privateKey = conv.stringToPrivateKey(keyString);
		assertNotEquals(user2.secretKey, privateKey);
	}

}
