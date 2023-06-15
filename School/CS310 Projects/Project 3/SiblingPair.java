//TODO: Complete java docs and code in missing spots.

/**
 * This class is used to store each element in the Authentication Pairs Route to Root (APRR).
 * Task 2: You are required to develop the constructor and the set and get methods of the leftSibling and rightSibling instance variables of this class.
 * @author Maha Shamseddine
 * @param <X> Type of input of SiblingPair class
 */
public class SiblingPair<X> {
	/**
	 * The left sibling of the pair.
	 */
	public X leftSibling;
	/**
	 * The right sibling of the pair.
	 */
	public X rightSibling;
	/**
	 * A constructor that initializes the left and right siblings to the given values.
	 * @param leftS The given left sibling.
	 * @param rightS The given right sibling.
	 */
	public SiblingPair(X leftS, X rightS) {
		//throw IllegalArgumentException for invalid parameters
		if(!(leftS instanceof String) || !(rightS instanceof String)) {
    		throw new IllegalArgumentException();
    	}
    	leftSibling = leftS;
    	rightSibling = rightS;
    }
    /**
     * A default constructor that initializes the left and right sibling to null.
     */
    public SiblingPair(){
    	this(null, null);
    }
    /**
     * Returns the left sibling of the pair.
     * @return The left sibling of the pair.
     */
    public X getLeftSibling() {
        return leftSibling;
    }
    /**
     * Returns the right sibling of the pair.
     * @return The right sibling of the pair.
     */
    public X getRightSibling() {
        return rightSibling;
    }
}
