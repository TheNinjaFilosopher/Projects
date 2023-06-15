//TODO: Complete java docs and code in missing spots.
//This code is already made for you, you just need to call Hashing.cryptHash whenever you want to generate the hash of a particular String. The output is formatted in HexaDecimal  
/**
 * Class that exists to generate a hash of a particular String.
 * @author Maha Shamseddine
 *
 */
public class Hashing extends SHA512 {
	/**
	 * Converts the given string to a hash.
	 * @param s The string being converted.
	 * @return The converted String.
	 */
	public static String cryptHash(String s) {
        String digest = hashSHA512(s);
        return digest.substring(0,128);
    }
}
