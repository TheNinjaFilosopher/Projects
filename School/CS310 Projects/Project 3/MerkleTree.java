//TODO: Complete java docs and code in missing spots.

import java.util.*;

/**
 * This is the class representing the complete Merkle Tree.
 * @author Logan France
 *
 */
public class MerkleTree {
	/**
	 * The root of the tree.
	 */
	public static MerkleTreeNode root;
	/**
	 * The number of files in the tree.
	 */
	public int numberOfFiles;
	/**
	 * The leaves of the tree.
	 */
	public ArrayList<MerkleTreeNode> leaves;
    
	/**
     * Receives a String array representing the files to be added to the leaf nodes of the Merkle Tree and returns the value of the root node of the Merkle Tree.
     * Also sets the value of the numberOfFiles instance variable.
     * @param files The files for the Merkle Tree.
     * @return The String representing the value of the root node, Str0,0.
     */
	public String constructMerkleTree(String[] files){
		//Task 3: You are required to develop the code for the constructMerkleTree method.
		//Running time complexity of this method: O(n) where n is the number of files (size of the files array)
		//You can assume that the size of the files will be given as 2^n
		//throw IllegalArgumentException for invalid parameters
		
		//Checks if files is null or if it contains 0 files.
		//throws an IllegalArgumentException() if so.
		if(files==null||files.length<1) {
			throw new IllegalArgumentException();
		}
		
		//Initializes the root, numberOfFiles, and leaves instance variables.
		root = new MerkleTreeNode();
		numberOfFiles = files.length;
		leaves = new ArrayList<MerkleTreeNode>();
		
		//Initializes the tempParent and tempChild ArrayLists.
		ArrayList<MerkleTreeNode> tempParent = new ArrayList<MerkleTreeNode>();
		ArrayList<MerkleTreeNode> tempChild = new ArrayList<MerkleTreeNode>();
		
		//Initializes the hash String variable.
		String hash = "";
		
		//Add every file to leaves as nodes
		for(int i = 0;i<numberOfFiles;i++) {
			leaves.add(new MerkleTreeNode(null,null,null,files[i]));
		}
		//Add every file in leaves to tempChild.
		tempChild = new ArrayList<MerkleTreeNode>(leaves);
		//For loop that creates a variable called Depth that is equal to log2(k), then decrements on each loop.
		//Once depth==0, set the root to tempChild.get(0), which will be the root of the tree, then end the loop.
		for(int depth = (int)(Math.log(numberOfFiles)/Math.log(2));depth>=0;depth--) {
			//If depth == 0, set root equal to tempChild.get(0) and then end the loop.
			if(depth == 0) {
				root = tempChild.get(0);
				break;
			}
			
			//Sets hash to the cryptohash of the concatenation of i and i+1 in the tempChild ArrayList
			//Creates a MerkleTreeNode called parent with a null parent, tempChild.get(i) and tempChild.get(i+1) as children and hash as a String
			//Add parent to tempParent and then set tempChild.get(i) and tempChild.get(i+1)'s parent to parent.
			for(int i = 0;i<tempChild.size();i+=2) {
				hash = tempChild.get(i).getStr()+tempChild.get(i+1).getStr();
				hash = Hashing.cryptHash(hash);
				
				MerkleTreeNode parent = new MerkleTreeNode(null, tempChild.get(i), tempChild.get(i+1),hash);
				
				tempParent.add(parent);
				tempChild.get(i).setParent(parent);
				tempChild.get(i+1).setParent(parent);
			}
			//Sets tempChild to tempParent before moving higher in the tree
			tempChild = new ArrayList<MerkleTreeNode>(tempParent);

			//Clears tempParent, allowing new nodes to be added.
			tempParent.clear();
		}
		//Return the String of the root.
		return root.getStr();	
	}
	
	/**
	 * Creates an ArrayList of SiblingPair that contains all the sibling pairs of the Authentication Pairs Route to Root.
	 * @param fileIndex The file whose route is being tracked.
	 * @return An ArrayList of SiblingPair that contains all the sibling pairs of the Authentication Pairs Route to Route
	 */
	public ArrayList<SiblingPair<String>> sendAppr(int fileIndex){
		//Task 4: You are required to develop the code for the sendAppr method.
		//Running time complexity of this method: O(logn)
		//throw IllegalArgumentException for invalid parameters
		
		//If the file is out of bounds, throw an IllegalArgumentException().
		if(fileIndex<0||fileIndex>=numberOfFiles) {
			throw new IllegalArgumentException();
		}
		//Creates an ArrayList to pass into the sendApprRecursion() method.
		ArrayList<SiblingPair<String>> pairs = new ArrayList<SiblingPair<String>>();
		//Returns the sendApprRecursion() method that accepts pairs and the MerkleTreeNode at the given index.
		return sendApprRecursion(pairs, leaves.get(fileIndex));
	}
	
	/**
	 * Recursive Helper method for sendAppr.
	 * @param current The current node being checked.
	 * @param pairs The ArrayList of SiblingPairs being returned.
	 * @return Pairs.
	 */
	private ArrayList<SiblingPair<String>> sendApprRecursion(ArrayList<SiblingPair<String>> pairs, MerkleTreeNode current){
		//Base case
		//If current.getParent() == null, then current is the root.
		//Add the root and null to the sibling pair, then return pairs.
		if(current.getParent()==null) {
			pairs.add(new SiblingPair<String>(root.getStr(), "null"));
			return pairs;
		}
		
		//Checks if current equals the left or right of it's parent and adds to pair in a certain order based on that.
		if(current==current.getParent().getLeft()) {
			
			pairs.add(new SiblingPair<String>(current.getStr(), current.getParent().getRight().getStr()));
		}
		else {
			pairs.add(new SiblingPair<String>(current.getParent().getLeft().getStr(),current.getStr()));
		}
		
		//Follows the Authentication path to the root by calling current.getParent()
		return sendApprRecursion(pairs, current.getParent());
	}
	/**
	 * Verifies the integrity of the given pair. Complete.
	 * @param aprr The Authentication Pair Route to Root.
	 * @param rootValue The root value being checked.
	 * @return True or False.
	 */
	public static boolean verifyIntegrity(ArrayList<SiblingPair<String>> aprr, String rootValue){
		//Task 5: You are required to develop the code for the verifyIntegrity method
		//Running time complexity of this method: O(logn)
		//throw IllegalArgumentException for invalid parameters
		
		//If either given value is null, then throw an IllegalArgumentException()
		if(aprr == null || rootValue == null)
			throw new IllegalArgumentException();
		
		//
		String checkString = "";
		
		//For loop that runs through each pair and checks it against the pairs above it until it reaches the pair before the root
		//Upon reaching that pair, check against the given rootValue
		for(int i = 0;i<aprr.size()-1;i++) {
			checkString = aprr.get(i).getLeftSibling()+aprr.get(i).getRightSibling();
			checkString = Hashing.cryptHash(checkString);
			
			if(i == aprr.size()-2) {
				if(!checkString.equals(rootValue)) {
					return false;
				}
			}
			else {
				if(!checkString.equals(aprr.get(i+1).getLeftSibling()) && !checkString.equals(aprr.get(i+1).getRightSibling())) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * Replaces the file at fileIndex with updatedFile, then updates the tree and returns the new root value.
	 * @param fileIndex The index of the file being changed.
	 * @param updatedFile The new data for the file.
	 * @return The new root value.
	 */
	public String replaceFile(int fileIndex, String updatedFile){
		//Task 6: You are required to develop the code for the replaceFile method.
		//Running time complexity of this method: O(logn)
		//throw IllegalArgumentException for invalid parameters
		
		//
		if(fileIndex<0 || fileIndex>numberOfFiles || updatedFile == null) {
			throw new IllegalArgumentException();
		}
		//Change the file at leaves
		MerkleTreeNode newFile = new MerkleTreeNode(leaves.get(fileIndex).getParent(), leaves.get(fileIndex).getLeft(), leaves.get(fileIndex).getRight(), updatedFile);
		leaves.set(fileIndex, newFile);
		
		//Create a checkString and a temp node that is equal the file at fileIndex
		String checkString = "";
		MerkleTreeNode temp = leaves.get(fileIndex);
		//If temp.getParent() == null, then you are at the root and there is nothing else to change.
		while(temp.getParent()!=null) {
			//Check if the temp node is on the left or the right and modify checkString based on that.
			if(temp == temp.getParent().getLeft()) {
				checkString = temp.getStr()+temp.getParent().getRight().getStr();
			}
			else {
				checkString = temp.getParent().getLeft().getStr()+temp.getStr();
			}
			//Set checkString to its hash
			checkString  = Hashing.cryptHash(checkString);
			//Set the parent of temp's string to checkString.
			temp.getParent().setStr(checkString);
			
			//After everything, set temp to its parent.
			temp = temp.getParent();
		}
		return root.getStr();
	}
}
