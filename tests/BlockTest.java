import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

class BlockTest {

	@Test
	void testSigningBlockCorrectly() throws NoSuchAlgorithmException {
		Block b = new Block(0, 0, null);
	    User user = new User();

	    b.signBlock(user.secretKey);
	    boolean valid = b.isValidSignature(user.publicKey);
	    assertEquals(true, valid);
	}
	
	@Test
	void testSigningBlockIncorrectly() throws NoSuchAlgorithmException {
		Block b = new Block(0, 0, null);
	    User user1 = new User();
		User user2 = new User();

	    b.signBlock(user1.secretKey);
	    boolean valid = b.isValidSignature(user2.publicKey);
	    assertEquals(false, valid);
	}

}
