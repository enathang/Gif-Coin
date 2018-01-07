import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class Block {
	int num;
	int amount;
	Hash prevHash;
	long nonce;
	Hash hash;
	Block next;
	String signature;

	/**
   * Creates a new hash with the given nonce and encryption algorithm
   *
   * @param nonce the given nonce
   * @return the calculated hash
   */
	private Hash calculateHash(long nonce) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("sha-256");

		ByteBuffer b = ByteBuffer.allocate(64);
		b.putInt(this.num);
		b.putInt(this.amount);
		if (this.prevHash != null) {
			b.put(this.prevHash.getData());
		}
		b.putLong(nonce);

		md.update(b.array());
		byte[] h = md.digest();
		Hash hash = new Hash(h);
		return hash;
	}

	/**
   * Mines a nonce for the block
   *
   * @return the nonce
   */
	private long mineNonce() throws NoSuchAlgorithmException {
		long nonce = 0;
		Hash h = calculateHash(nonce);
		while (!h.isValid()) {
			nonce++;
			h = calculateHash(nonce);
		}

		return nonce;
	}

	/**
   * Creates a new block
   *
   * @param num the number of the block
	 * @param amount the amount of the transaction
	 * @param prevHash the previous hash
   */
	public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
		this.num = num;
		this.amount = amount;
		this.prevHash = prevHash;

		this.nonce = mineNonce();
		this.hash = calculateHash(this.nonce);
	}

	/**
	 * Creates a new block
	 *
	 * @param num the number of the block
	 * @param amount the amount of the transaction
	 * @param prevHash the previous hash
	 * @param nonce the nonce of the block
   */
	public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
		this.num = num;
		this.amount = amount;
		this.prevHash = prevHash;

		this.nonce = nonce;
		this.hash = calculateHash(this.nonce);
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
	 * Turns the block into a readable string and returns that
	 *
	 * @return the block as a readable string
   */
	public String toString() {
		String s;

		if (this.prevHash != null) {
      s = "Block " + this.num + " (Amount: " + this.amount + ", Nonce: "
				+ this.nonce +", prevHash: " + this.prevHash.toString() + " hash: "
        + this.hash.toString() + ") ";
		} else {
      s = "Block " + this.num + " (Amount: " + this.amount + ", Nonce: "
				+ this.nonce + ", prevHash: null, hash: " + this.hash.toString() + ") ";
		}

		return s;
	}

}
