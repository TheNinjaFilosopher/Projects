/**
 * This class represents a bag that can hold any single type of object (decided at bag-creation time), and only one item of that type at a time.
 * @author Logan France
 *
 * @param <T> The generic object.
 */
public class OneItemBag<T> {
	/**
	 * The single type of item the bag holds.
	 */
	private T item;
	/**
	 * Constructor for the OneItemBag.
	 */
	public OneItemBag() {}
	/**
	 * A method that puts an item in the bag which returns whether or not it was successfully added.
	 * @param item The item that is attempting to be placed in the bag.
	 * @return True or False.
	 */
	public boolean addItem(T item) {
		if(hasItem()) {
			return false;
		}
		this.item = item;
		return true;
	}
	/**
	 * A method that removes an item from the bag and returns it, return null if there is no item.
	 * @return The removed item or null if there is no item to return.
	 */
	public T removeItem() {
		if(!hasItem())
			return null;
		T temp = item;
		item = null;
		return temp;
	}
	/**
	 * A method to check if an item is in the bag which returns true or false.
	 * @return True or False.
	 */
	public boolean hasItem() {
		return item != null;
	}
}
