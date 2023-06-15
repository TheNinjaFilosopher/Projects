// TO DO: add your implementation and JavaDocs.
/**
 * The Class that handles the methods for the array.
 * @author Logan France
 *
 * @param <T> The Generic variable.
 */
public class MySortedArray<T extends Comparable<T>> {

	//default initial capacity / minimum capacity
	/**
	 * The default capacity of the array. The absolute minimum.
	 */
	private static final int DEFAULT_CAPACITY = 2;
	
	//underlying array for storage -- you MUST use this for credit!
	//Do NOT change the name or type
	/**
	 * The array that holds the data.
	 */
	private T[] data;
	
	// ADD MORE PRIVATE MEMBERS HERE IF NEEDED!
	/**
	 * Variable for the current number of elements in the array.
	 */
	private int currentCapacity;
	
	/**
	 * Constructor that creates an array with a default capacity of 2.
	 */
	@SuppressWarnings("unchecked")
	public MySortedArray() {
		// Constructor
		
		// Initial capacity of the storage should be DEFAULT_CAPACITY
		// Hint: Can't remember how to make an array of generic Ts? It's in the textbook...
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor that allows a specific initial capacity to be set.
	 * @param initialCapacity The initial capacity of the array.
	 */
	@SuppressWarnings("unchecked")
	public MySortedArray(int initialCapacity) {
		// Constructor

		// Initial capacity of the storage should be initialCapacity

		// Throw IllegalArgumentException if initialCapacity is smaller than 
		// 2 (which is the DEFAULT_CAPACITY). 
		// Use this _exact_ error message for the exception 
		//  (quotes are not part of the message):
		//    "Capacity must be at least 2!"
		if(initialCapacity<DEFAULT_CAPACITY)
			throw new IllegalArgumentException("Capacity must be at least 2!");
		
		data = (T[]) new Comparable[initialCapacity];
		currentCapacity = 0;
	}
	
	/**
	 * Method that returns the current number of elements.
	 * @return The current number of elements.
	 */
	public int size() {	
		// Report the current number of elements
		// O(1)
		
		return currentCapacity; //default return, remove/change as needed
	}  
	
	/**
	 * Method that returns the max number of elements allowed before expansion.
	 * @return The max number of elements allowed before expansion.
	 */
	public int capacity() { 
		// Report max number of elements before the next expansion
		// O(1)
		
		return data.length; //default return, remove/change as needed
	}

	/**
	 * Adds a new value to the array and automatically sorts it in the proper place.
	 * @param value The new value being added.
	 */
	@SuppressWarnings("unchecked")
	public void add(T value){
		// Insert the given value into the array and keep the array _SORTED_ 
		// in ascending order. 

		// If the array already contains value(s) that are equal to the new value,
		// make sure that the new value is added at the end of the group. Check examples
		// in project spec and main() below.
		
		// Remember to use .compareTo() to perform order/equivalence checking.
				
		// Note: You _can_ append an item (and increase size) with this method.
		// - You must call doubleCapacity() if no space is available. 
		// - Check below for details of doubleCapacity().
		// - For the rare case that doubleCapacity() fails to increase the max 
		//   number of items allowed, throw IllegalStateException.
		// - Use this _exact_ error message for the exception 
		//  (quotes are not part of the message):
		//    "Cannot add: capacity upper-bound reached!"

		
		// Note: The value to be added cannot be null.
		// - Throw IllegalArgumentException if value is null. 
		// - Use this _exact_ error message for the exception 
		//  (quotes are not part of the message):
		//    "Cannot add: null value!"
		
		// O(N) where N is the number of elements in the storage
		
		if(value==null) {
			throw new IllegalArgumentException("Cannot add: null value!");
		}
		
		if(size()+1>capacity())
			if(!doubleCapacity())
				throw new IllegalStateException("Cannot add: capacity upper-bound reached!");
		if(size()==0){
			data[0] = value;
			currentCapacity++;
			return;
		}
		T[] tempArray = (T[]) new Comparable[capacity()];
		if(value.compareTo(data[0])<=0) {
			tempArray[0] = value;
			for(int i = 0,a=1;i<size();i++,a++) {
				tempArray[a] = data[i];
			}
			data = tempArray;
			currentCapacity++;
			return;
		}
		if (data[size()-1]!=null&&value.compareTo(data[size()-1])>0) {
			tempArray=data;
			tempArray[size()] = value;
			data = tempArray;
			currentCapacity++;
			return;
		}
		boolean addedValue = false;
		for(int i = 0, a = 0;i<size();i++,a++) {
			
			if(data[i+1]!=null && value.compareTo(data[i+1])<=0 && !addedValue) {
				tempArray[a++] = data[i];
				tempArray[a] = value;
				addedValue = true;
				continue;
			}
			tempArray[a]=data[i];
		}
		data = tempArray;
		currentCapacity++;
	}
	/**
	 * Returns an element at a specific index.
	 * @param index The index where the element is getting selected from.
	 * @return The element at the specific index.
	 */
	public T get(int index) {
		// Return the item at the given index
		
		// O(1)
				
		// For an invalid index, throw an IndexOutOfBoundsException.
		// Use this code to produce the correct error message for
		// the exception (do not use a different message):
		//	  "Index " + index + " out of bounds!"
		if(index<0||index>=size())
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		return data[index]; //default return, remove/change as needed

	}
	
	/**
	 * Replaces an element at the given index with the given value.
	 * @param index The index whose element is getting replaced.
	 * @param value The new value.
	 * @return True if the value is successfully replaced, False if not.
	 */
	public boolean replace(int index, T value) {
		// Change the item at the given index to be the given value.
		
		// For an invalid index, throw an IndexOutOfBoundsException. 
		// Use the same error message as get(index).
		// Note: You _cannot_ add new items with this method.
		
		// For a valid index, if value is null, throw IllegalArgumentException.
		// Use the exact same error message as add(value).
				
		// The array must keep to be sorted in ascending order after updating. 
		// For a valid index, return false if setting the value at index violates 
		// the required order hence can not be performed; no change should be made 
		// to the array.  Otherwise, change the item and return true. 
		
		// O(1)
		
		if(index<0||index>currentCapacity)
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		
		if(get(index)==null)
			throw new IllegalArgumentException("Cannot add: null value!");
		
		if(value.compareTo(data[index])==0)
			return true;
		
		if(index==0) {
			if(value.compareTo(data[index+1])<0) {
				data[index] = value;
				return true;
			}
		}
		else if(index==size()-1) {
			if(value.compareTo(data[index-1])>0) {
				data[index] = value;
				return true;
			}
		}
		else {
			if(data[index+1]!=null && value.compareTo(data[index+1])<=0 && value.compareTo(data[index-1])>0) {
				data[index] = value;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a new element at the given index, unless adding this element would cause the array to be unsorted, in which case it returns false and doesn't add the element.
	 * @param index The index where the new value is being added.
	 * @param value The new value.
	 * @return True if the element could be added, False if the element cannot.
	 */
	public boolean add(int index, T value) {
		// Insert the given value at the given index. Shift elements if needed.
		// Double capacity if no space available. 

		// For an invalid index, throw an IndexOutOfBoundsException. 
		// Use the same error message as get(index).
		// Note: You _can_ append items with this method, which is different from replace().
		
		// For a valid index, if value is null, throw IllegalArgumentException.
		// Use the exact same error message as add(value). See add(value) above.

		// The array must keep to be sorted in ascending order after updating. 
		// For a valid index, return false if inserting the value at index violates 
		// the required order hence can not be performed; no change should be made 
		// to the array.  Otherwise, insert the value and return true. 
		
		// You must call doubleCapacity() if no space is available. 
		// Throw IllegalStateException if doubleCapacity() fails.
		// Use the exact same error message as add(value). See add(value) above.

		// O(N) where N is the number of elements in the storage
		
		if(index<0||index>capacity())
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		
		if(value==null) {
			throw new IllegalArgumentException("Cannot add: null value!");
		}
		
		if(size()+1>capacity())
			if(!doubleCapacity())
				throw new IllegalStateException("Cannot add: capacity upper-bound reached!");
		if(size()==0){
			//System.out.println("The array was empty, so we just added the number at zero.");//possibly change back to data[index]
			data[0] = value;
			currentCapacity++;
			return true;
		}
		
		@SuppressWarnings("unchecked")
		T[] tempArray = (T[]) new Comparable[capacity()];
		
		if(index==0) {
			tempArray[0] = value;
			for(int i = 0,a=1;i<size();i++,a++) {
				tempArray[a] = data[i];
			}
			if(isSorted(tempArray)) {
				data = tempArray;
				currentCapacity++;
				return true;
			}
			return false;
		}
		if (index == size()-1) {
			tempArray=data;
			tempArray[size()] = value;
			if(isSorted(tempArray)) {
				data = tempArray;
				currentCapacity++;
				return true;
			}
			return false;
		}
		//boolean addedValue = false;
		
		for(int i = 0;i<index;i++) {
			tempArray[i] = data[i];
		}
		tempArray[index] = value;
		for(int i = index;i<size();i++) {
			tempArray[i+1] = data[i]; 
		}
		if(isSorted(tempArray)) {
			data = tempArray;
			currentCapacity++;
			return true;
		}
		return false; //default return, remove/change as needed
	} 
	
	/**
	 * Deletes an element at a given element.
	 * @param index The index whose element is being deleted.
	 * @return The deleted element.
	 */
	@SuppressWarnings("unchecked")
	public T delete(int index) {
		// Remove and return the element at the given index. Shift elements
		// to remove the gap. Throw an exception when there is an invalid
		// index (see replace(), get(), etc. above).
		
		// After deletion, if the number of elements falls below 1/3 
		// of the capacity, you must call halveCapacity() to shrink the storage.
		// - Check halveCapacity() below for details.
		// - If the capacity would fall below the DEFAULT_CAPACITY, 
		//   shrink to the DEFAULT_CAPACITY. This should be implemented by
		//   halveCapacity().
		
		// O(N) where N is the number of elements currently in the storage
		if(index<0||index>=capacity())
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		
		T tempElement = data[index];
		
		
		if(index==size()-1) {
			data[index] = null;
			currentCapacity--;
			if(size()<capacity()/3)
				halveCapacity();
			return tempElement;
		}
		
		T[] tempArray = (T[]) new Comparable[capacity()];
		data[index] = null;
		
		for(int i = 0, a=0;i<size();i++) {
			if(data[i]!=null) {
				tempArray[a++] = data[i];
				//System.out.print(a+" : "+tempArray[a]);
				//a++;
			}
			//System.out.println(" - Followed by Data[" +i+"]: "+data[i]);
		}
		
		data = tempArray;
		currentCapacity--;
		if(size()<capacity()/3)
			halveCapacity();
		return tempElement; //default return, remove/change as needed
	}  
	
	/**
	 * Doubles the capacity when called.
	 * @return True if the capacity could be doubled, False if not.
	 */
	@SuppressWarnings("unchecked")
	public boolean doubleCapacity(){
		// Double the max number of items allowed in data storage.
		// Remember to copy existing items to the new storage after expansion.

		// - Out of abundance of caution, we will use (Integer.MAX_VALUE - 50)
		//   as the upper-bound of our capacity.
		// - If double the current capacity would go beyond this upper-bound,
		//   use this upper-bound value as the new capacity.
		// - If the current capacity is already this upper-bound (Integer.MAX_VALUE - 50), 
		//   do not expand and return false.
		
		// Return true for a successful expansion.

		// O(N) where N is the number of elements in the array
		
		if(capacity()==Integer.MAX_VALUE-50)
			return false;
		int newCapacity = 0;
		if(capacity()*2>Integer.MAX_VALUE-50)
			newCapacity = Integer.MAX_VALUE-50;
		else
			newCapacity = capacity()*2;
		T[] tempArray = (T[]) new Comparable[newCapacity];
		for(int i = 0;i<capacity();i++) {
			tempArray[i] = data[i];
		}
		data = tempArray;
		return true; //default return, remove/change as needed

	}
	
	/**
	 * Halves the capacity when called.
	 * @return True if the capacity could be halved, False if not.
	 */
	@SuppressWarnings("unchecked")
	public boolean halveCapacity(){
		// Reduce the max number of items allowed in data storage by half.
		// - If the current capacity is an odd number, _round down_ to get the 
		//   new capacity;
		// - If the new capacity would fall below the DEFAULT_CAPACITY, 
		//   shrink to the DEFAULT_CAPACITY;
		// - If the new capacity (after necessary adjustment to DEFAULT_CAPACITY) 
		//   cannot hold all existing items, do not shrink and return false;
		// - Return true for a successful shrinking.

		// Remember to copy existing items to the new storage after shrinking.
		
		// O(N) where N is the number of elements in the array
		//System.out.println("Size: "+size());
		//System.out.println("Capacity: "+capacity());
		//System.out.println("Math floor: "+Math.floor(capacity()/2));
		int newCapacity = (int) Math.floor(capacity()/2);
		//System.out.println("New Capacity: "+newCapacity);
		if(newCapacity<DEFAULT_CAPACITY) {
			newCapacity=DEFAULT_CAPACITY;
		}
		//System.out.println("New Capacity After Check: "+newCapacity);
		if(size()>newCapacity)
			return false;
		
		T[] tempArray = (T[]) new Comparable[newCapacity];
		//System.out.println("Length of tempArray: "+tempArray.length);
		for(int i = 0;i<size();i++) {
			tempArray[i] = data[i];
		}
		data = tempArray;
		//System.out.println("New Capacity: "+ capacity()+" and "+data.length);
		return true; //default return, remove/change as needed
		

	}
	//******************************************************
	//*******     BELOW THIS LINE IS TESTING CODE    *******
	//*******      Edit it as much as you'd like!    *******
	//*******		Remember to add JavaDoc			 *******
	//******************************************************
	/**
	 * Returns a string that prints out the array in an easy to view form.
	 * @return A string that prints out the array in an easy to view form.
	 */
	public String toString() {
		//This method is provided for debugging purposes
		//(use/modify as much as you'd like), it just prints
		//out the MySortedArray for easy viewing.
		StringBuilder s = new StringBuilder("MySortedArray with " + size()
			+ " items and a capacity of " + capacity() + ":");
		for (int i = 0; i < size(); i++) {
			s.append("\n  ["+i+"]: " + get(i));
		}
		return s.toString();
		
	}
	/**
	 * A method that checks if the array that is input is properly sorted.
	 * @param array The array being tested for sorting.
	 * @return True if the array is sorted, False if not.
	 */
	private boolean isSorted(T[] array) {
		
		for(int i = 0;i<array.length-1;i++) {
			if(array[i+1]==null)
				break;
			if(array[i].compareTo(array[i+1])>0) {
				return false;
			}
		}
		return true;
	}
	/**
	 * The main method where the tests are done.
	 * @param args The arguments passed in. Not used for this class.
	 */
	public static void main(String[] args){		
		//These are _sample_ tests. If you're seeing all the "yays" that's
		//an excellend first step! But it might not mean your code is 100%
		//working... You may edit this as much as you want, so you can add
		//own tests here, modify these tests, or whatever you need!

		//create a MySortedArray of integers
		MySortedArray<Integer> nums = new MySortedArray<>();
		
		if((nums.size() == 0) && (nums.capacity() == 2)){
			System.out.println("Yay 1");
		}
		
		//append some numbers 
		for(int i = 0; i < 3; i++) {
			nums.add(i,i*2);
		}
		//uncomment to check the array details
		//System.out.println(nums.toString());
		
		
		if(!nums.add(nums.size(),1) && nums.size() == 3 && nums.get(2) == 4 && nums.capacity() == 4){
			System.out.println("Yay 2");
		}
		//System.out.println(nums.toString());
		
		//add more numbers, your insertion need to keep the array sorted
		nums.add(1);
		nums.add(-1);
		nums.add(5);
		if (nums.size() == 6 && nums.get(0)==-1 && nums.get(2) == 1 && nums.get(5) == 5){
			System.out.println("Yay 3");
		}
		//System.out.println(nums.toString());
		
		//add with index
		if (nums.add(4,2) && nums.add(3,2) && nums.get(3) == nums.get(4) 
			&& nums.get(4) == nums.get(5) && nums.get(5)== 2){ 	
			System.out.println("Yay 4");		
		}
		//System.out.println(nums.toString());
		
		//replace with index
		
		if (nums.replace(5,3) && nums.get(5)==3 && nums.replace(6,5) && nums.get(6)==5
			&& !nums.replace(1,2) && nums.get(1) == 0){
			System.out.println("Yay 5");				
		}
		//System.out.println(nums.toString());
		
		MySortedArray<String> names = new MySortedArray<>();
		
		//insert some strings
		names.add("alice");
		names.add("charlie");
		names.add("bob");
		names.add("adam");
		//System.out.println("This should have four names\n"+names.toString());

		//delete
		
		if (names.add(4,"emma") && names.delete(3).equals("charlie")){
			System.out.println("Yay 6");
		}
		//System.out.println(names.toString());
		names.delete(0);
		names.delete(0);
		
		System.out.println("This should have two names\n"+names.toString());
		
		//shrinking
		
		if (names.size()==2 && names.capacity() == 4){
			System.out.println("Yay 7");
		}
		System.out.println(names.toString());
		
		//insert equal values: sorted by insertion order
		String dylan1 = new String("dylan");
		String dylan2 = new String("dylan");
		names.add(dylan1);
		names.add(dylan2);
		if (names.size()==4 && names.get(1) == dylan1 && names.get(2) == dylan2
			&& names.get(1)!=names.get(2)){
			System.out.println("Yay 8");		
		}
		System.out.println(names.toString());
		
		// exception checking example
		// make sure you write more testings by yourself
		try{
			names.get(-1);
		}
		catch(IndexOutOfBoundsException ex){
			if (ex.getMessage().equals("Index -1 out of bounds!"))
				System.out.println("Yay 9");
		}
		
		// call doubleCapacity() and halveCapacity() directly
		if (names.doubleCapacity() && names.capacity() == 8 
			&& names.halveCapacity() && names.capacity() == 4
			&& !names.halveCapacity() && names.capacity() == 4){
			System.out.println("Yay 10");
		
		}
		//System.out.println(names.toString());

	}
	

}