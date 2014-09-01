package control;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Toolbox {
	public static byte[] generateRandomKey(int length) {
		SecureRandom random = new SecureRandom();
		byte key[] = new byte[length];
		random.nextBytes(key);
		return key;
	}
	
	public static String generateLocationString(){
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(Main.getInstance().FILEIDENT_LEN).replace("[", "").replace("@", "");
	}
}
