//TODO: Complete java docs and code in missing spots.

/**
 * This class is used to represent nodes in the Merkle Tree.
 * @author Logan France
 *
 */
public class MerkleTreeNode{
	/**
	 * The parent of the node.
	 */
	private MerkleTreeNode parent;
    /**
     * The left child of the node.
     */
    private MerkleTreeNode left;
    /**
     * The right child of the node.
     */
    private MerkleTreeNode right;
    /**
     * The data of the node in the form of a string.
     */
    private String str;

    /**
     * A default MerkleTreeNode constructor that initializes the instance variables to null.
     */
    public MerkleTreeNode(){
		this(null,null,null,null);
	}
	
	/**
	 * A MerkleTreeNode Constructor that initiates the object with the parent, left, and right MerkleTreeNode objects.
	 * @param parent The parent of the node.
	 * @param left The left child of the node.
	 * @param right The right child of the node.
	 * @param str The data of the node in the form of a string.
	 */
	public MerkleTreeNode(MerkleTreeNode parent,MerkleTreeNode left,MerkleTreeNode right,String str){
		this.parent = parent;
		this.left = left;
		this.right = right;
		this.str = str;
	}

	/**
	 * Develop the set and get methods for the instance variables parent, left, right and str.
	 * Returns the Parent of the Node.
	 * @return The Parent of the Node.
	 */
	public MerkleTreeNode getParent(){
		return parent;
	}
	/**
	 * Returns the Left child of the Node.
	 * @return The Left child of the Node.
	 */
	public MerkleTreeNode getLeft(){
		return left;
	}
	/**
	 * Returns the Right child of the Node.
	 * @return The Right child of the Node.
	 */
	public MerkleTreeNode getRight(){
		return right;
	}
	/**
	 * Returns the String of the Node.
	 * @return The String of the Node.
	 */
	public String getStr(){
		return str;
	}
	/**
	 * Sets the parent of the node to the input value.
	 * @param parent The new parent.
	 */
	public void setParent(MerkleTreeNode parent){
		//throw IllegalArgumentException for invalid parameters
		if(parent == null) {
			throw new IllegalArgumentException();
		}
		this.parent = parent;
	}
	/**
	 * Sets the left child of the node to the input value.
	 * @param left The new left child.
	 */
	public void setLeft(MerkleTreeNode left){
		//throw IllegalArgumentException for invalid parameters
		if(left == null) {
			throw new IllegalArgumentException();
		}
		this.left = left;
	}
	/**
	 * Sets the right child of the node to the input value.
	 * @param right The new right child.
	 */
	public void setRight(MerkleTreeNode right){
		//throw IllegalArgumentException for invalid parameters
		if(right == null) {
			throw new IllegalArgumentException();
		}
		this.right = right;
	}
	/**
	 * Sets the string value of the node to the input value.
	 * @param str The new string value.
	 */
	public void setStr(String str){
		//throw IllegalArgumentException for invalid parameters
		if(str == null) {
			throw new IllegalArgumentException();
		}
		this.str = str;
	}        
        
}