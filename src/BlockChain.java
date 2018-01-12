import java.security.NoSuchAlgorithmException;

public class BlockChain {
	Node first;
	Node last;

	/**
   * Create a new blockchain with the given amount
   *
   * @param initial the initial amount
   */
	public BlockChain(int initial) throws NoSuchAlgorithmException {
		Block b = new Block(0, initial, null);
		Node n = new Node(b, null);
		this.first = n;
		this.last = n;
	}

	/**
   * Return the size of the blockchain
   *
   * @return the size of the blockchain
   */
	public int getSize() {
		Node temp = this.first;
		int count = 0;
		while (temp.nextNode != null) {
			temp = temp.nextNode;
			count++;
		}
		return count;
	}

	/**
   * Append the given block to the end of the blockchain
   *
   * @param blk the block to append
   */
	public void append(Block blk) throws IllegalArgumentException {
		this.last.nextNode = new Node(blk, null);
		this.last = this.last.nextNode;
	}

	/**
   * Removes the last block in the blockchain
   *
   * @return returns whether the removal was successful
   */
	private boolean removeLast() {
		if (getSize() < 2) {
			return false;
		}

		Node temp = this.first;
		while (temp.nextNode != this.last) {
			temp = temp.nextNode;
		}
		temp.nextNode = null;

		return true;
	}

	/**
   * Returns the hash of the last block in the chain
   *
   * @return the Hash of the last block
   */
	public Hash getHash() {
		return this.last.data.hash;
	}

	/**
   * Returns whether or not the blockchain is valid
   *
   * @return whether or not the blockchain is valid
   */
	public boolean isValidBlockChain() {
		// TODO
		return true;
	}

	/**
   * Prints out the block chain in a readable format
   */
	public void printChain() {
		System.out.println(toString());
	}

	/**
   * Returns the blockchain in a readable string
   *
   * @return the blockchain as a string
   */
	public String toString() {
		String s = "";
		Node temp = this.first;
		while (temp != null) {
			s += temp.toString() + "\n";
			temp = temp.nextNode;
		}

		return s;
	}

}
