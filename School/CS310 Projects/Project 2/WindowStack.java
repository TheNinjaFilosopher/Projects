//TODO: Complete java docs and code in missing spots.
//Missing spots are marked by < YOUR_CODE_HERE >.
//Do NOT edit any other parts of the code.

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.awt.Color;
import java.util.Comparator;

/**
 *  A stack of windows within the window.
 *  
 *  <p>Adapterion of Nifty Assignment (http://nifty.stanford.edu/) by
 *  Mike Clancy in 2001. Original code by Mike Clancy. Updated Fall
 *  2022 by K. Raven Russell.</p>
 */
public class WindowStack {
	//You'll need some instance variables probably...
	//< YOUR_CODE_HERE >
	/**
	 * The list of Windows.
	 */
	ThreeTenLinkedList<Window> list;
	/**
	 * The constructor that initializes the list as a ThreeTenLinkedList for Windows.
	 */
	public WindowStack() {
		//Any initialization code you need.
		
		//O(1)
		
		//< YOUR_CODE_HERE >
		list = new ThreeTenLinkedList<Window>();
	}
	/**
	 * Returns the head of the list.
	 * @return The head of the list.
	 */
	public Node<Window> getHead() {
		//Returns the head (top) of the stack of windows.
		
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
	 * Returns the tail of the list.
	 * @return The tail of the list.
	 */
	public Node<Window> getTail() {
		//Returns the tail (bottom) of the stack of windows.
		
		//O(1)
		
		//We will use this method to test your
		//linked list implementaiton of this
		//class, so whether or not you are using
		//the generic linked list class or bare
		//nodes, you must still be able to return
		//the appropriate tail of the list.
		
		//< YOUR_CODE_HERE >
		
		return list.getTail(); //Uses the getTail() from ThreeTenLinkedList.
	}
	/**
	 * Returns the number of windows in the stack.
	 * @return The number of Windows in the stack.
	 */
	public int numWindows() {
		//Gets the number of windows in the stack.
		
		//O(1)
		
		return list.getSize(); //Uses the getSize() from ThreeTenLinkedList.
	}
	/**
	 * Adds a window to the top of the stack.
	 * @param r The window being added.
	 */
	public void add(Window r) {
		//Add a window at the top of the stack.
		
		//O(1)
		
		//throw IllegalArgumentException for invalid windows
		
		//Note: the "top" of the stack should
		//be the head of your linked list.
		
		//< YOUR_CODE_HERE >
		//Checks if r is null or not a window and throws an IllegalArgumentException if so.
		if(r==null|| !(r instanceof Window))
			throw new IllegalArgumentException();
		//Uses the addStart() from ThreeTenLinkedList.
		list.addStart(r);	
	}
	/**
	 * A method that handles the clicking of the window, based on whether or not it was right clicked or left clicked.
	 * @param x The x position.
	 * @param y The y position.
	 * @param leftClick True if left click, False if right click.
	 * @return True if the click was handled, false if no window was found.
	 */
	public boolean handleClick (int x, int y, boolean leftClick) {
		//The mouse has been clicked at position (x,y).
		//Left clicks are move windows to the top of the
		//stack or pass the click on to the window at the
		//top. Right clicks remove windows.
		
		//Returns true if the click was handled, false
		//if no window was found.
		
		//O(n) where n is the number of windows in the stack
		
		
		//Details:
		
		//Find the top-most window on the stack (if any)
		//that contains the given coordinate.
		
		
		//For a left click:
		
		//If the window is not at the top of the stack,
		//move it to the top of the stack without
		//disturbing the order of the other windows.
		//Mark this window as the "selected" window (and
		//ensure the previous selected window is no longer
		//selected).
		
		//If the window is at the top of the stack already,
		//ask the window to handle a click-event (using the
		//Window's handleClick() method).
		
		//If none of the windows on the stack were clicked
		//on, just return.
		
		
		//For a right click:
		
		//Remove the window from the stack completely. The
		//window at the top of the stack should be the 
		//selected window.
		
		
		//Hint #1: This would be a great time to use helper
		//methods! If you just write one giant method...
		//it'll be much harder to debug...
		
		//Hint #2: Make sure to use the methods you wrote
		//in the Window class. Don't write those again!

		
		//< YOUR_CODE_HERE >
		
		//Checks if the list is empty before anything else
		//If the list is empty, then there is nothing to delete
		//And handleClick() must be false.
		if(list.isEmpty()) {
			return false;
		}
		//Create a node variable to hold the first Window to contain the (x,y) position.
		Node<Window> node=null;
		//Create an index to use deleteMiddle.
		int index = 0;
		//Iterate through the list with a for loop, check every node to see if it contains the (x,y) position
		//If it does, set node equal to the node at i, set index equal to i, then stop the loop with break
		for(int i = 0;i<numWindows();i++) {
			if(list.getNode(i).data.contains(x, y)) {
				node = list.getNode(i);
				index = i;
				break;
			}
		}
		//Check if node equals null, if it does, then no Window in the list contained the coordinates.
		//handleClick() can then return false.
		if(node==null)
			return false;
		
		//Check if it was a left click or a right click.
		if(leftClick) {
			//If it was a left click, check whether or not node equals head.
			//If it doesn't, then delete the node at index and add it back to the top of the stack.
			//Then set the top's selected value equal to true.
			if(!node.equals(getHead())) {
				add(list.deleteMiddle(index).data);
				unselect();
				list.getHead().data.setSelected(true);
			}
			else {//otherwise, call the handleClick() method in Window on the top Window.
				list.getHead().data.handleClick(x, y);
			}
		}
		else {
			//Delete the node at the index
			//Afterwards, if the list isn't empty, call the handleClick() method for Window
			//Next use unselect() to unselect any value in the list.
			//Then select the head of the list with setSelected from Window.
			list.deleteMiddle(index);
			if(!list.isEmpty()) {
				list.getHead().data.handleClick(x, y);
				unselect();
				list.getHead().data.setSelected(true);
			}

		}
		return true; //true, since the method ran smoothly.
	}
	/**
	 * Unselects all windows in the list.
	 */
	private void unselect() {
		//If the list is empty, there is nothing to unselect.
		if(list.isEmpty())
			return;
		//Create a temp node that is equal to head.
		Node<Window> temp = getHead();
		//Index for the use of getNode().
		int index = 0;
		//Iterate through the list with temp, if any windows are selected, set selected to false.
		while(temp!=null) {
			if(temp.data.getSelected()) {
				list.getNode(index).data.setSelected(false);
				break;
			}
			index++;
			temp=temp.next;
		}
	}

	/**
	 *  Gets an iterator for the stack of windows.
	 *  Windows are returned from bottom to top.
	 *  
	 *  @return the iterator requested
	 */
	public Iterator<Window> windows() {
		//Note that this method uses your linked list!
		//so if the iterator doesn't work, that's on you...
		
		return new Iterator<>() {
			/**
			 *  The current node pointed to by the
			 *  iterator (containing the next value
			 *  to be returned).
			 */
			private Node<Window> current = getTail();
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public Window next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				Window ret = current.data;
				current = current.prev;
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
	 * Sorts the windows in the stack by their area (length x window).
	 */
	public void sortSize() {
		//Sorts the windows in the stack by their area (length x width).
		//MOST of this is done for you, but you still need to assign
		//the returned head and tail back.
		
		//unselect the top window
		this.getHead().data.setSelected(false);
		
		//create a way to compare windows by area
		Comparator<Window> comp = new Comparator<>() {
			public int compare(Window w1, Window w2) {
				return (w1.getWidth()*w1.getHeight())-(w2.getWidth()*w2.getHeight());
			}
		};
		
		//create a pair of nodes to pass into the sort function
		ThreeTenLinkedList.NodePair<Window> pair = new ThreeTenLinkedList.NodePair<>(getHead(), getTail());
		
		//call the sort function with the comparator
		ThreeTenLinkedList.NodePair<Window> ret = ThreeTenLinkedList.sort(pair, comp);
		
		//make the returned list the head and tail of this list
		//this is for PART 5 of the project... don't try to do this
		//before you complete ThreeTenLinkedList.sort()!
		//< YOUR_CODE_HERE >
		
		//Make a list setHead and setTail method, that should allow me to do things right
		list.setTail(ret.tail);
		list.setHead(ret.head);
		
		//re-select the top of the stack
		this.getHead().data.setSelected(true);
	}
	/**
	 * Sorts the windows in the stack by their upper left corner location.
	 */
	public void sortLoc() {
		//Sorts the windows in the stack by their upper left
		//corner loction. Left things (bigger-X) are on top
		//of right things (smaller-X). Tie-breaker: lower
		//things (bigger-Y) top of  higher things (smaller-Y).

		//This should use your ThreeTenLinkedList.sort() method you
		//write in Part 5, so don't do this until you have (a) read
		//part 5, (b) looked at the example in sortSize() above, and
		//(c) are sure you understand comparators.
		
		//O(n^2)
		
		//unselect the top window
		this.getHead().data.setSelected(false);
		
		//create a way to compare windows by area
		Comparator<Window> comp = new Comparator<>() {
			public int compare(Window w1, Window w2) {
				if(w1.getUpperLeftX()>w2.getUpperLeftX())
					return 1;
				else if(w1.getUpperLeftX()<w2.getUpperLeftX())
					return -1;
				else {
					if(w1.getUpperLeftY()>w2.getUpperLeftY())
						return 1;
					else if (w1.getUpperLeftY()<w2.getUpperLeftY())
						return -1;
					else
						return 0;
				}
			}
		};
		
		//create a pair of nodes to pass into the sort function
		ThreeTenLinkedList.NodePair<Window> pair = new ThreeTenLinkedList.NodePair<>(getHead(), getTail());
		
		//call the sort function with the comparator
		ThreeTenLinkedList.NodePair<Window> ret = ThreeTenLinkedList.sort(pair, comp);
		
		
		//Make a list setHead and setTail method
		list.setTail(ret.tail);
		list.setHead(ret.head);
		//re-select the top of the stack
		this.getHead().data.setSelected(true);
		
	}
	
	/*public static void main (String[] args) {
		/*Square sq = new Square(0, 0, 11, Color.BLACK);
		System.out.println(sq.getUpperLeftX()+"|"+sq.getUpperLeftY());
		System.out.println(sq.id());
		
		System.out.println(sq.contains(2,2));
		WindowStack winSta = new WindowStack();
		
		for(int i = 0;i<10;i++) {
			winSta.add(new Window(i, i, i*10, i*10, Color.black));
		}
	}*/
}

