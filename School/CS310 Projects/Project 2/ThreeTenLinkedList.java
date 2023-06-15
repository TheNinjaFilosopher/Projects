//TODO: Linked list implementation (optional)
//      JavaDocs (not optional)
//      Static sorting methods (not optional)

import java.awt.Color;
import java.util.Comparator;
/**
 * The Linked List implementation for the project, that contains all the necessary methods to make project work.
 * @author Logan France
 *
 * @param <T> The Generic Value.
 */
class ThreeTenLinkedList<T> {
	//You may, but are not required to, implement some or
	//all of this generic linked list class to help you with
	//this project. If you do, you MUST use the provided
	//Node class for this linked list class. This means
	//that it must be a doubly linked list (and links in
	//both directions MUST work).
	
	//Alternatively, you may implement this project using
	//only the Node class itself (i.e. use "bare nodes"
	//in the classes that require linked lists).
	
	//Either way, you MUST do all your own work. Any other
	//implementations you have done in the past, anything
	//from the book, etc. should not be in front of you,
	//and you certainly shouldn't copy and paste anything
	//from any other source.
	
	//This is the only class where you are allowed to
	//implement public methods.
	
	/**
	 * The size of the list.
	 */
	private int size;
	/**
	 * The head of the list.
	 */
	private Node<T> head;
	/**
	 * The tail of the list.
	 */
	private Node<T> tail;
	/**
	 * The constructor that sets the size to 0, and the head and tail to null.
	 */
	public ThreeTenLinkedList() {
		size = 0;
		head = null;
		tail=null;
	}
	/**
	 * Adds a node with the inputted data to the start.
	 * @param data The data of the node.
	 */
	public void addStart(T data) {
		//Check if data is null before anything else.
		if(data==null)
			return;
		//Create a new node with the given data.
		Node<T> node = new Node<T>(data);
		//Check if the head is null. If it is, the list is empty and tail must equal the node.
		//If not, then head.prev must be made to equal the node to ensure
		//the node is at the start.
		if(head==null)
			tail = node;
		else
			head.prev=node;
		//Set node.next equal to head, ensuring that you can go back and forth with them.
		node.next=head;
		//Set head equal to node, changing the pointer.
		head=node;
		//Increase the size by one to represent the new size of the list.
		size++;
	}
	
	/**
	 * Adds a node with the given data to the given index.
	 * @param index The index the node is being placed at.
	 * @param data The data of the node.
	 */
	public void addMiddle(int index, T data) {
		
		if(index==0) {//If the index is 0, then it's the head of the list and addStart() can be used.
			addStart(data);
			return;
		}
		else if(index ==getSize()-1) {//If the index is size-1, then it's the tail of the list and addBack() can be used.
			addBack(data);
			return;
		}
		//Check if data is null before anything else.
		if(data==null)
			return;
		//Use getNode to find the node that comes before the desired index.
		Node<T> prevNode = getNode(index-1);
		//Create a node using the given data.
		Node<T> node = new Node<T>(data);
		//Give the new node the prevNode's .next, then set the prevNode's .next equal to the new node.
		//Afterwards, set the prev of the new node to prevNode.
		node.next=prevNode.next;
		prevNode.next=node;
		node.prev=prevNode;
		
		//Check if the new node's .next does not equal null, and if it does not, set .next's .prev to the new node.
		if(node.next!=null)
			node.next.prev=node;
		
		//Increment the size.
		size++;
	}
	/**
	 * Add a node to the back of the list with the given data.
	 * @param data The data of the node.
	 */
	public void addBack(T data) {
		//Check if data is null before anything else.
		if(data==null)
			return;
		//Create a new node with the data.
		Node<T> node = new Node<T>(data);
		//If the list is empty, then set the head equal to the new node.
		//Otherwise, set tail.next equal to the new node to append it to the end of the list.
		if(tail==null)
			head = node;
		else
			tail.next=node;
		//Set node.prev equal to tail to make its prev the current tail pointer
		node.prev=tail;
		//Change the tail pointer to the new node.
		tail=node;
		//Increment the size.
		size++;
	}
	/**
	 * Setting the head to the given node.
	 * @param newHead The new head.
	 */
	public void setHead(Node<T> newHead) {
		this.head = newHead;
	}
	/**
	 * Setting the tail to the given node.
	 * @param newTail The new tail.
	 */
	public void setTail(Node<T> newTail) {
		this.tail = newTail;
	}
	/**
	 * Returns the size of the list.
	 * @return The size of the list.
	 */
	public int getSize(){
		return size;
	}
	/**
	 * Returns the head of the list.
	 * @return The head of the list.
	 */
	public Node<T> getHead(){
		return head;
	}
	/**
	 * Returns the tail of the list.
	 * @return The tail of the list.
	 */
	public Node<T> getTail(){
		return tail;
	}
	/**
	 * Returns a node at the given index.
	 * @param index The index where the node is.
	 * @return A node at the given index.
	 */
	public Node<T> getNode(int index){
		
		if(index==0) //If the index is 0, then it is at the front, so use getHead()
			return getHead();
		else if(index==getSize()-1) //If the index is size-1, then it is at the back, so use getTail()
			return getTail();
		else if(index<0||index>=getSize()) //If the index is less than 0 or greater than/equal to size, then it is out of bounds.
			throw new IndexOutOfBoundsException();
		//Set a temp node equal to head
		Node<T> temp = head;
		//Using a for loop, iterate through the list index amount of times
		for(int i=0;i<index;i++) {
			temp = temp.next;
		}
		return temp;// Return the temp node, which points to the correct node in the list.
	}
	/**
	 * Deletes the first node in the list.
	 * @return The deleted node.
	 */
	public Node<T> deleteHead(){
		if(isEmpty()) //Checks if the list is empty, returns null if so.
			return null;
		//Create a temp node equal to head.
		Node<T> temp = head;
		//If the list only has a single node, then set tail equal to null
		//Otherwise, set the head's .next's .prev to null
		if(head.next==null)
			tail=null;
		else
			head.next.prev=null;
		//Make head equal its next, removing it from the list.
		head=head.next;
		//Decrement the size.
		size--;
		//Return the temp variable.
		return temp;
	}
	/**
	 * Deletes the last node in the list.
	 * @return The deleted node.
	 */
	public Node<T> deleteTail(){
		if(isEmpty()) //Checks if the list is empty, returns null if so.
			return null;
		//Create a temp node equal to head.
		Node<T> temp = tail;
		//If the list only has a single node, then set head equal to null
		//Otherwise, set the tail's .prev's .next to null
		if(tail.prev==null)
			head=null;
		else
			tail.prev.next=null;
		//Make tail equal to its next, removing it from the list.
		tail=tail.prev;
		//Decrement the size.
		size--;
		//Return the temp variable.
		return temp;
	}
	/**
	 * Deletes a node at the given index.
	 * @param index The index of the node being deleted.
	 * @return The deleted node.
	 */
	public Node<T> deleteMiddle(int index){
		//Check if the list is empty or if the index is out of bounds.
		if(isEmpty())
			return null;
		if(index<0||index>=getSize())
			throw new IndexOutOfBoundsException();
		//Check if the index is 0 or size-1
		//If 0, it is at the beginning, return deleteHead()
		//If size-1, it is at the end, return deleteTail()
		if(index==0)
			return deleteHead();
		if (index==getSize()-1)
			return deleteTail();
		//Use getNode with the index to set the target node.
		Node<T> target = getNode(index);
		
		//Set the nodes on either sides to be related to each other.
		//Effectively deleting the target from the list.
		target.next.prev = target.prev;
		target.prev.next = target.next;
		
		//Decrement the size.
		size--;
		return target;
	}
	/**
	 * Returns true or false depending on if the list is empty or not.
	 * @return True if the list is empty, false if not.
	 */
	public boolean isEmpty() {
		if(getSize()==0)
			return true;
		return false;
	}
	
	//In "Part 5" of the project, you will also be implementing
	//the following static methods:
	/**
	 * Checks if the list from the inputted NodePair is sorted and returns true if so.
	 * @param <X> The generic value.
	 * @param pairs The pairs being checked for sorting.
	 * @param comp The comparator that contains the criteria for proper sorting.
	 * @return True if the list is sorted, False if not.
	 */
	static <X> boolean isSorted(NodePair<X> pairs, Comparator<X> comp) {
		//Determine if the provided list is sorted based on the comparator.
		
		//For an empty linked list (e.g. the head-tail pair contains two nulls)
		//return true (an empty list is sorted).

		//For a null comparator or null pairs, throw an IllegalArgumentException.
		
		//O(n)
		
		//< YOUR_CODE_HERE >
		
		//Checks if comp or pairs are null and throws an exception if either of them are.
		if(comp==null||pairs==null) {
			throw new IllegalArgumentException();
		}
		
		//Checks if the list is empty, then returns true if so, since an empty list is always sorted.
		if(pairs.head==null&&pairs.tail==null) {
			return true;
		}
		
		//Sets a temp variable equal to the head of the NodePair
		Node<X> temp = pairs.head;
		//While iterating through temp, check the data of the current temp node and the node that comes after it.
		//If it gives a result that is greater than 0, then the list is unsorted.
		while(temp.next!=null) {
			if(comp.compare(temp.data, temp.next.data)>0) {
				return false;
			}
			temp=temp.next;
		}
		return true; //If the while loop never caught an unsorted pair in the array,
	}
	/**
	 * A method that sorts the given NodePair based on the criteria in the given Comparator.
	 * @param <X> The generic value.
	 * @param pairs The pairs being checked for sorting.
	 * @param comp The comparator that contains the criteria for proper sorting.
	 * @return A sorted NodePair.
	 */
	static <X> NodePair<X> sort(NodePair<X> pairs, Comparator<X> comp) {
		
		//Using the comparator, sort the linked list. It is recommended that
		//you sort by moving *values* around rather than moving nodes.
		//Two simple sorting algorithms which will work well here (and
		//meet the big-O requirements if implemented correctly) are the
		//insertion sort (see textbook Ch8.3) and the selection sort.

		//Insertion sort quick summary: Go to each element in the linked list,
		//shift it "left" into the correct position.
		//Example: 1,3,0,2
		// 1 is at the start of the list, leave it alone
		// 3 is bigger than 1, leave it alone
		// 0 is smaller than 3, move it left: 1,0,3,2
		// 0 is smaller than 1, move it to the left: 0,1,3,2
		// 0 is at the start of the list, stop moving it left
		// 2 is smaller than 3, move it to the left: 0,1,2,3
		// 2 is bigger than 1, stop moving it to the left

		//Selection sort quick summary: Go to each index in the linked list,
		//find the smallest thing from that index and to the "right",
		//and swap it into that index.
		//Example: 1,3,0,2
		// index 0: the smallest thing from index 0 to the end is 0, swap it into the right place: 0,3,1,2
		// index 1: the smallest thing from index 1 to the end is 1, swap it into the right place: 0,1,3,2
		// index 2: the smallest thing from index 2 to the end is 2, swap it into the right place: 0,1,2,3
		// index 3: there is only one item from index 3 to the end, so this is in the right places
		
		//Regardless of the method you choose, your sort should be a stable sort.
		//This means that if there are two equal values, they do not change their
		//order relative to each other.
		//Example: 1, 2, 1
		//The first "1" (which I'll call "1a") should be sorted before
		//the second "1" (1b), so that the output is "1a, 1b, 2" and
		//never "1b, 1a, 2". The easiest way to test this is to put two
		//equal items in the list, sort, and confirm using == that the
		//correct object is in the correct place.
		
		//For an empty linked list (e.g. the head-tail pair contains two nulls)
		//return the original pairs back to the user.
		
		//For a null comparator or null pairs, throw an IllegalArgumentException.
		
		//O(n^2)
		
		//< YOUR_CODE_HERE >
		
		if(comp==null||pairs==null) {//Checks if comp or pairs are null and throws an exception if either of them are.
			throw new IllegalArgumentException();
		}
		
		
		if(pairs.head==null&&pairs.tail==null) {//If the pair is empty, return it, since there is nothing to sort.
			return pairs;
		}
		
		//Create a temporary node and set it equal to the head of pairs.
		Node<X> temp = pairs.head;
		//Create another temporary node for the purpose of traversing.
		Node<X> traversalTemp;
		//Create one more temporary node for the purpose of holding the node with the minimum value.
		Node<X> minNode;
		
		//Iterate through the list with temp.
		//Before iterating through traversalTemp, set minNode equal to temp and traversalTemp equal to the variable after temp.
		while(temp!=null) {
			minNode = temp;
			traversalTemp = temp.next;
			//Iterate through the list with traversalTemp to compare to the minNode.
			//If comp.compare gives a number greater than 0, then minNode is greater than traversalTemp
			//and should be set to traversalTemp.
			while(traversalTemp!=null) {
				if(comp.compare(minNode.data, traversalTemp.data)>0) {
					minNode = traversalTemp;
				}
				traversalTemp = traversalTemp.next;
			}
			//Swap the values of temp and minNode
			valueSwap(temp, minNode);
			//Set temp equal to temp.next to find the element that goes in the next position.
			temp = temp.next;
		}
		return pairs;
	}
	/**
	 * Swaps the values of the given Nodes.
	 * @param <X> The generic value.
	 * @param node1 The first node.
	 * @param node2 The second node.
	 */
	private static <X> void valueSwap(Node<X> node1, Node<X> node2) {
		X temp = node1.data;
		node1.data = node2.data;
		node2.data = temp;
	}
	
	//Which uses the following nested class:
	/**
	 * A class with a pair of nodes that serve as the head and tail of a list.
	 * @author CS310 Teachers
	 *
	 * @param <Y> The generic value.
	 */
	public static class NodePair<Y> {
		/**
		 * The head of the pair.
		 */
		public Node<Y> head;
		/**
		 * The tail of the pair.
		 */
		public Node<Y> tail;
		/**
		 * The constructor that initializes the head and tail with the given values.
		 * @param head The given head.
		 * @param tail The given tail.
		 */
		public NodePair(Node<Y> head, Node<Y> tail) {
			this.head = head;
			this.tail = tail;
		}
	}
	
	//You may also use the above nested class elsewhere in your
	//project if you'd find that helpful.
	
	/*public static void main (String[] args) {
		ThreeTenLinkedList<Square> ttll = new ThreeTenLinkedList<Square>();
		for(int i = 0;i<10;i++) {
			ttll.addStart(new Square(i,i, i*10,Color.getHSBColor(i, i*3, i*9)));
		}
		
		for(int i = 11;i<22;i+=2) {
			ttll.addStart(new Square(i,i, i*10,Color.getHSBColor(i, i*3, i*9)));
		}
		
		
		Node<Square> temp = ttll.head;
		
		while(temp!=null) {
			System.out.println(temp.data.id());
			temp = temp.next;
		}
		Comparator<Square> comp = new Comparator<>() {
			public int compare(Square s1, Square s2) {
				return s1.id()-s2.id();//(w1.getWidth()*w1.getHeight())-(w2.getWidth()*w2.getHeight());
			}
		};
		NodePair<Square> pair = new NodePair<Square>(ttll.getHead(), ttll.getTail());
		
		
		sort(pair, comp);
		ttll.head = pair.head;
		ttll.tail = pair.tail;
		
		
		temp = ttll.head;
		
		while(temp!=null) {
			System.out.println(temp.data.id());
			temp = temp.next;
		}
	}*/
	
}