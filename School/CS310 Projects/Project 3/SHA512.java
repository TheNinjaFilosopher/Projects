//TODO: Complete java docs and code in missing spots.
import java.security.*;
import java.nio.charset.*;

//This code is already made for you, it is extended by Hashing to generate the SHA-512 hash of a String. No explicit call to any method here is required.
/**
 * Class that is used to generate the SHA-512 hash of a String.
 * @author Maha Shamseddine
 *
 */
public class SHA512 {
	/**
	 * Accepts a given message and returns it in the form of a hex.
	 * @param message The message being converted.
	 * @return The message in the form of a hex.
	 */
	protected static String hashSHA512(String message) {
        String sha512ValueHexa = "";
        try {
            MessageDigest digest512 = MessageDigest.getInstance("SHA-512");
            sha512ValueHexa = byteToHex(digest512.digest(message.getBytes(StandardCharsets.UTF_8)));
        }
        catch(NoSuchAlgorithmException exp) {
            exp.getMessage();
        }
        return sha512ValueHexa;
    }
    /**
     * Converts a byte to a Hex.
     * @param digest An array of bytes.
     * @return A string in the form of a hex value.
     */
    public static String byteToHex(byte[] digest) {
        StringBuilder vector = new StringBuilder();
        for (byte c : digest) {
            vector.append(String.format("%02X", c));
        }
        String output = vector.toString();
        return output;
    }
}
