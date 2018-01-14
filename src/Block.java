import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;
import java.util.Random;
import java.nio.ByteBuffer;
import java.math.BigInteger;

public class Block {
	int num;
	int amount;
	long nonce;
	Hash prevHash;
	Hash hash;
	Block next;
	byte[] signature;

	/**
     * Uses the private key to sign the block
     *
     * @param secretKey the secretKey of the user to sign the block
     */
	public void signBlock(PrivateKey secretKey) {
		try {
			Signature dsa = Signature.getInstance("SHA1withECDSA");
			dsa.initSign(secretKey);

			Hash signatureHash = calculateHash(0);
			byte[] hashByte = signatureHash.data;
			dsa.update(hashByte);

			this.signature = dsa.sign();
		} catch (SignatureException | InvalidKeyException e) {
			System.err.println("Caught Exception: "+e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Caught Exception: "+e.getMessage());
		}
	}

	/**
	 * Creates a new block
	 *
	 * @param num the number of the block
	 * @param amount the amount of the transaction
	 * @param prevHash the previous hash
	 */
	public Block(int num, int amount, Hash prevHash) {
		this.num = num;
		this.amount = amount;
		this.prevHash = prevHash;
	}

	/**
     * Creates a new hash with the given nonce and encryption algorithm
     *
     * @param nonce the given nonce
     * @return the calculated hash
     */
	private Hash calculateHash(long nonce) {
		ByteBuffer b = ByteBuffer.allocate(64);
		b.putInt(this.num);
		b.putInt(this.amount);
		if (this.prevHash != null) {b.put(this.prevHash.getData());}
		b.putLong(nonce);

		Hash hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("sha-256");
			md.update(b.array());
			byte[] h = md.digest();
			hash = new Hash(h);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Caught NoSuchAlgorithmException: "+e.getMessage());
		}

		return hash;
	}

	/**
     * Mines a nonce for the block
     *
     * @return the nonce
     */
	public long mineNonce() {
		long nonce = 0;
		Hash h = calculateHash(nonce);
		while (!h.isValid()) {
			nonce++;
			h = calculateHash(nonce);
		}

		return nonce;
	}

	/**
	 * Returns whether or not the block should be considered valid
	 *
	 * @param  publicKey the public key of the user who signed the block
	 * @return whether or not the signature matches the public key
	 */
	public boolean isValid(PublicKey publicKey) {
		if (!isValidSignature(publicKey)) {
			return false;
		}

		this.nonce = mineNonce();
		this.hash = calculateHash(nonce);
		return true;
	}

	/**
     * Returns whether or not the signature on the block was signed by
	 * the user with the corresponding public key
     *
     * @param  publicKey the public key of the user
     * @return whether or not the specified used signed the block
     */
	public boolean isValidSignature(PublicKey publicKey) {
		boolean isValid = false;

		try {
			Signature sig = Signature.getInstance("SHA1withECDSA");
			sig.initVerify(publicKey);
			Hash hash = calculateHash(0);
			sig.update(hash.data);
			isValid = sig.verify(this.signature);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		} catch (InvalidKeyException | SignatureException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

		return isValid;
	}

	/**
	 * Returns the block's number
	 *
	 * @return the block's number
     */
	public int getNum() {
		return this.num;
	}

	/**
	 * Returns the block's amount
	 *
	 * @return the block's amount
     */
	public int getAmount() {
		return this.amount;
	}

	/**
	 * Returns the block's nonce
	 *
	 * @return the block's nonce
     */
	public long getNonce() {
		return this.nonce;
	}

	/**
	 * Returns the previous block's hash
	 *
	 * @return the previous block's hash
     */
	public Hash getPrevHash() {
		return this.prevHash;
	}

	/**
	 * Turns the block into a readable string and returns the string
	 *
	 * @return the block as a readable string
     */
	public String toString() {
		String s = "Block " + this.num;
		s += " (Amount: " + this.amount;
		s += ", Nonce: " + this.nonce;
		s += ", Hash: ";
		s += ( (this.hash == null) ? "null" : this.hash.toString());
		s += ") ";

		return s;
	}

}
