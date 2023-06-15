//TODO: Add JavaDocs ONLY. No other Editing.

/**
 * The class that handles the implementation of the Node.
 * @author Logan France
 *
 * @param <T> The generic variable.
 */
class Node<T> {
	/**
	 * The data of the node, can be anything due to being a generic value.
	 */
	public T data;
	/**
	 * The next Node in the Linked List.
	 */
	public Node<T> next;
	/**
	 * The previous Node in the Linked List.
	 */
	public Node<T> prev;
	/**
	 * The basic constructor that leaves all values as null.
	 */
	public Node() {
		
	}
	/**
	 * The constructor that sets the data to the imported value.
	 * @param data The imported value.
	 */
	public Node(T data) {
		this.data = data;
	}
}