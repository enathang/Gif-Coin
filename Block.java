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

	Hash calculateHash(long nonce) throws NoSuchAlgorithmException {
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

	long mineNonce() throws NoSuchAlgorithmException {
		long nonce = 0;
		Hash h = calculateHash(nonce);
		while (!h.isValid()) {
			nonce++;
			h = calculateHash(nonce);
		}

		return nonce;
	}

	Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
		this.num = num;
		this.amount = amount;
		this.prevHash = prevHash;

		this.nonce = mineNonce();
		this.hash = calculateHash(this.nonce);
	}

	Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
		this.num = num;
		this.amount = amount;
		this.prevHash = prevHash;

		this.nonce = nonce;
		this.hash = calculateHash(this.nonce);
	}

	public int getNum() {
		return this.num;
	}

	public int getAmount() {
		return this.amount;
	}

	public long getNonce() {
		return this.nonce;
	}

	public Hash getPrevHash() {
		return this.prevHash;
	}

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
