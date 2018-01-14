public class Node {
	Block data;
	Node nextNode;

	/**
     * Constructs a new node given a block and the next node
     *
     * @param b the block that is held in the Node
     * @param next the next node in the chain
     */
	public Node(Block b, Node next) {
		this.data = b;
		this.nextNode = next;
	}

	/**
     * Returns the block of this node in a formatted string
     *
     * @return  the data in a readable string
     */
	public String toString() {
		return this.data.toString();
	}
}
