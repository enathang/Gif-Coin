import java.security.NoSuchAlgorithmException;

public class BlockChain {
	Node first;
	Node last;

	BlockChain(int initial) throws NoSuchAlgorithmException {
		Block b = new Block(0, initial, null);
		Node n = new Node(b, null);
		this.first = n;
		this.last = n;
	}

	Block mine(int amount) throws NoSuchAlgorithmException {
		Block b = new Block(getSize(), amount, this.last.data.hash);
		return b;
	}

	public int getSize() {
		Node temp = this.first;
		int count = 0;
		while (temp.nextNode != null) {
			temp = temp.nextNode;
			count++;
		}
		return count;
	}

	public void append(Block blk) throws IllegalArgumentException {
		this.last.nextNode = new Node(blk, null);
		this.last = this.last.nextNode;
	}

	public boolean removeLast() {
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

	public Hash getHash() {
		return this.last.data.hash;
	}

	public boolean isValidBlockChain() {
		// TODO
		return true;
	}

	public void printBalances() {
		// TODO
		System.out.println("balances: ");
	}

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
