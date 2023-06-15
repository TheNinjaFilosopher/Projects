/**
 * This program accepts multiple numbers as command line arguments and prints those numbers out again space separated, 
 * but for multiples of 3 prints "banana" instead of the number, for multiples of 7 prints "coconut" instead of the number, 
 * and for the multiples of both 3 and 7 prints "banana-coconut" instead of the number. For numbers smaller than 1, it prints "puttputt".
 * @author Logan France
 *
 */
public class BananaCoconut {
	/**
	 * The main method of the BananaCoconut class.
	 * @param args The arguments being passed in by the user, must be an integer or an error will be thrown.
	 */
	public static void main(String[] args) {
		//Initializes num to hold the integers for checking
		int num = 0;
		//Initializes ans for the final output
		String ans = "";
		//Checks if the command line input is correct, catching any errors that occur
		try 
		{
			if(args.length<1) 
			{
				throw new Exception();
			}
			for(String s : args) {
				num = Integer.parseInt(s);
			}
		}
		catch(Exception e) 
		{
			System.err.println("One or more numbers required as a command line argument.\r\n"
					+ "Example Usage: java BananaCoconut [number] [number] [...]");
			return;
		}
		// Prints
		for(int i =0;i< args.length;i++){
			num = Integer.parseInt(args[i]);
			
			if(num<1)
				ans+="puttputt";//System.out.print("puttputt");
			else if(num%3==0 && num%7==0) 
				ans+="banana-coconut";//System.out.print("banana-coconut");
			else if(num%3==0) 
				ans+="banana";//System.out.print("banana");
			else if(num%7==0) 
				ans+="coconut";//System.out.print("coconut");
			else
				ans+=num;//System.out.print(num);
			
			if(i!=args.length-1)
				ans+=" ";//System.out.print(" ");
		}
		
		System.out.print(ans);
	}

}
