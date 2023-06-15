import java.util.*;

public class ExecutionTest{
	public static void main(String[] args) {
		String[] files = {"Merkle Tree Authentication Project", "CS 310 Project 3", "CS 310", "Data Structures Project 3"};
		String root = null;

		MerkleTree merkleTree = new MerkleTree();

		// Building the MerkleTree
		System.out.println("Testing the constructMerkleTree method");
		root = merkleTree.constructMerkleTree(files);

		System.out.println("The root value after constructing the MerkleTree is " + root);
		System.out.println("The root value expected after constructing the MerkleTree is F118EFF0E779DE1594122DE1D9A1B68F24F8C93D793EC5068EE74E5F84CCE942A72839BDF61054D91F7665DF9F22F0115088C5DB17121ECD801A91B032372378");
		
		System.out.println();
		System.out.println("****************************************************************");
		
		System.out.println("Testing the sendAppr method");
        ArrayList<SiblingPair<String>> aprr = merkleTree.sendAppr(2);

		try {
			int size = aprr.size();
			for(int i = 0; i < size; i++){
				SiblingPair<String> siblingPair = aprr.get(i);
				String first = siblingPair.leftSibling;
				String second = siblingPair.rightSibling;
				System.out.println("The pair at position " + (i) + " is " + first + " and " + second);
			}

			System.out.println("The Expected APRR is:");
			System.out.println("The pair at position 0 is CS 310 and Data Structures Project 3");
			System.out.println("The pair at position 1 is A6FF2D3798C0E3341C88121EF513EB2C1AEB11949D8F19946E9C2840316A4CA98D4939D1939E53421BBF23A4B0966E9344D0DC2C95168F7AAA3E88216A1BA94A and 552F524F5B3AD72C7379679448E8B9C25066C7457EBD45C2C8F7F33ACFE75F52ED8813727D613E3311D7888D43E7011B82D36A190E82CB481290470277A055D6");
			System.out.println("The pair at position 2 is F118EFF0E779DE1594122DE1D9A1B68F24F8C93D793EC5068EE74E5F84CCE942A72839BDF61054D91F7665DF9F22F0115088C5DB17121ECD801A91B032372378 and null");
		}
		catch(Exception e) {
			System.out.println("Error finding the APRR");
		}

		System.out.println();
		System.out.println("*********************************************************");

		//Authenticate List function
		System.out.println("Testing the verifyIntegrity method");
		System.out.println("The result is "+ MerkleTree.verifyIntegrity(aprr,root));
		System.out.println();
		System.out.println("*********************************************************");

		// Updating a document
		System.out.println("Testing the replaceFile method");
		root = merkleTree.replaceFile(1, "CS 310 Sections 3 and 5 Project");

		System.out.println("The root value after replacing the file in the MerkleTree is " + root);
		System.out.println("The expected root value after replacing the file in the MerkleTree is 039485C914CF30AD18DC3866F39E7D8AC28536182C17F24F9A11E2937EB77110DDB6BF74494483258A7A3EDC7409AA088624ACD21471E987E1C820204BE58033");
	}
}
