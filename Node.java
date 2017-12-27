public class Node {
	Block data;
	Node nextNode;

	Node(Block b, Node next) {
		this.data = b;
		this.nextNode = next;
	}

	public String toString() {
		return this.data.toString();
	}
}
