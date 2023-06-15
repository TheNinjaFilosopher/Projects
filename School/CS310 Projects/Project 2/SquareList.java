//TODO: Complete java docs and code in missing spots.
//Missing spots are marked by < YOUR_CODE_HERE >.
//Do NOT edit any other parts of the code.

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.awt.Color;
import java.util.Comparator;

/**
 *  A list of squares within a single window.
 *  
 *  <p>Adapterion of Nifty Assignment (http://nifty.stanford.edu/) by
 *  Mike Clancy in 2001. Original code by Mike Clancy. Updated Fall
 *  2022 by K. Raven Russell.</p>
 */
public class SquareList {
	//You'll need some instance variables probably...
	//< YOUR_CODE_HERE >
	/**
	 * The linked list that contains the squares.
	 */
	private ThreeTenLinkedList<Square> list;
	/**
	 *  Initialize an empty list of squares.
	 */
	public SquareList() {
		//Any initialization code you need.
		
		//O(1)
		
		//< YOUR_CODE_HERE >
		list = new ThreeTenLinkedList<Square>();
	}
	/**
	 * A method that returns the head of the list.
	 * @return The head of the list.
	 */
	public Node<Square> getHead() {
		//Returns the head of the list of squares.
		
		//O(1)
		
		//We will use this method to test your
		//linked list implementaiton of this
		//class, so whether or not you are using
		//the generic linked list class or bare
		//nodes, you must still be able to return
		//the appropriate head of the list.
		
		//< YOUR_CODE_HERE >
		
		return list.getHead(); //Uses the getHead() from ThreeTenLinkedList.
	}
	/**
	 * A method that returns the tail of the list.
	 * @return The tail of the list.
	 */
	public Node<Square> getTail() {
		//Returns the tail of the list of squares.
		
		//O(1)
		
		//We will use this method to test your
		//linked list implementation of this
		//class, so whether or not you are using
		//the generic linked list class or bare
		//nodes, you must still be able to return
		//the appropriate tail of the list.
		
		//< YOUR_CODE_HERE >
		
		return list.getTail(); //Uses the getTail() from ThreeTenLinkedList.
	}
	/**
	 * The number of squares in the list.
	 * @return The number of squares in the list.
	 */
	public int numSquares() {
		//Gets the number of squares in the list.
		
		//O(1)
		
		//< YOUR_CODE_HERE >
		
		return list.getSize();// uses the getSize() from ThreeTenLinkedList.
	}
	/**
	 * Adds a square to the back of the list, unless the square is invalid.
	 * @param sq The Square being added.
	 */
	public void add(Square sq) {
		//Add a square to the list. Newly added squares
		//should be at the back end of the list.
		
		//O(1)
		
		//throw IllegalArgumentException for invalid squares
		
		//< YOUR_CODE_HERE >
		//Checks if sq is null or not an instance of Square, throws an IllegalArgumentException() if it is.
		if(sq == null || !(sq instanceof Square))
			throw new IllegalArgumentException();
		list.addBack(sq); //Uses the addback() method from ThreeTenLinkedList to add to the back of the list.
	}
	/**
	 * A method that deletes all squares from the list that contain the position (x,y).
	 * @param x The x position.
	 * @param y The y position.
	 * @return True if any squares get deleted, False if not.
	 */
	public boolean handleClick (int x, int y) {
		//Deletes all squares from the list that contain the 
		//position (x,y). Returns true if any squares get
		//deleted and returns false otherwise.
		
		//Returns true if any squares were deleted.
		
		//O(n) where n is the size of the list of squares
		
		//< YOUR_CODE_HERE >
		//Checks if the list is empty before anything else.
		//If the list is empty, then there are no squares to delete.
		if(list.isEmpty())
			return false;
		
		//startingSize is used to keep track of the original size of the list before any possible deletions.
		//If the starting size is greater than the current number of squares, then there was a deletion, and handleClick() must be true.
		//Otherwise, handleClick() must be false, because the number of squares is the same as the starting size, or somehow, greater.
		int startingSize = numSquares();
		
		//Using the getNode() method of ThreeTenLinkedList to find each node and its data
		//to check if it contains the (x,y) position before deleting it with deleteMiddle() if so.
		//In order to check every node in the list, this for loop starts from the back.
		for(int i = numSquares()-1;i>=0;i--) {
			if(list.getNode(i).data.contains(x, y)) {
				list.deleteMiddle(i);
			}
		}
		
		//Checks if startingSize is greater than the current number of Squares.
		return startingSize>numSquares();
	}

	/**
	 *  Gets an iterator for the list of squares.
	 *  Squares are returned in the order added.
	 *  
	 *  @return the iterator requested
	 */
	public Iterator<Square> elements() {
		//Note that this method uses your linked list!
		//so if the iterator doesn't work, that's on you...
		
		return new Iterator<>() {
			/**
			 *  The current node pointed to by the
			 *  iterator (containing the next value
			 *  to be returned).
			 */
			private Node<Square> current = getHead();
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public Square next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				Square ret = current.data;
				current = current.next;
				return ret;
			}
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public boolean hasNext() {
				return (current != null);
			}
		};
	}
	/**
	 * Sorts the squares in the selected window by their creation time, where lower ids were created first.
	 */
	public void sortCreation() {
		//Sorts the squares in the window by their creation time
		//(lower ids were created first). This should use your
		//ThreeTenLinkedList.sort() method you write in Part 5,
		//so don't do this until you have (a) read part 5,
		//(b) looked at the example in WindowStack, and (c) are 
		//sure you understand comparators.
		
		//O(n^2)
		
		//< YOUR_CODE_HERE >
		
		//create a way to compare squares by id
		Comparator<Square> comp = new Comparator<>() {
			public int compare(Square s1, Square s2) {
				return s1.id()-s2.id();
			}
		};
		
		//create a pair of nodes to pass into the sort function
		ThreeTenLinkedList.NodePair<Square> pair = new ThreeTenLinkedList.NodePair<>(getHead(), getTail());
		
		//call the sort function with the comparator
		ThreeTenLinkedList.NodePair<Square> ret = ThreeTenLinkedList.sort(pair, comp);
		

		list.setTail(ret.tail);
		list.setHead(ret.head);
		

	}
	/**
	 * Sorts the squares in the window by their location in the window, using the same rules as WindowStack.
	 */
	public void sortLoc() {
		//Sorts the squares in the window by their location
		//in the window. Same rules as sorting the windows
		//in WindowStack. This should use your
		//ThreeTenLinkedList.sort() method you write in Part 5,
		//so don't do this until you have (a) read part 5,
		//(b) looked at the example in WindowStack, and (c) are 
		//sure you understand comparators
		
		//O(n^2)
		
		//< YOUR_CODE_HERE >
		//The Comparator that checks the locations of two squares.
		Comparator<Square> comp = new Comparator<>() {
			public int compare(Square s1, Square s2) {
				if(s1.getUpperLeftX()>s2.getUpperLeftX())
					return 1;
				else if(s1.getUpperLeftX()<s2.getUpperLeftX())
					return -1;
				else {
					if(s1.getUpperLeftY()>s2.getUpperLeftY())
						return 1;
					else if (s1.getUpperLeftY()<s2.getUpperLeftY())
						return -1;
					else
						return 0;
				}
			}
		};
		
		//create a pair of nodes to pass into the sort function
		ThreeTenLinkedList.NodePair<Square> pair = new ThreeTenLinkedList.NodePair<>(getHead(), getTail());
		
		//call the sort function with the comparator
		ThreeTenLinkedList.NodePair<Square> ret = ThreeTenLinkedList.sort(pair, comp);
		
		
		//Sets the tail and the head using the ret values.
		list.setTail(ret.tail);
		list.setHead(ret.head);
	}
	
	/*public static void main (String[] args) {
		SquareList sql = new SquareList();
		for(int i = 0; i<100;i++) {
			if(i%2==1)
				sql.add(new Square(i,i,i*10, Color.getHSBColor(i, i*3, i*9)));
			else
				sql.add(new Square(i*100,i*100,i, Color.getHSBColor(i, i*3, i*9)));
		}
		
	}*/
}
