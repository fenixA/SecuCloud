package control.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * The Class SupportFunctions provides supporting functions.
 */
public class SupportFunctions {

	/** The Constant INT_BYTE_SIZE. Length of an int in java. */
	public static final int INT_BYTE_SIZE = 4;

	/**
	 * Converts a Byte array to an integer.
	 * 
	 * @param value
	 *            the given byte array
	 * @return the calculated int
	 */
	public static int byteArrayToInt(byte[] value) {
		int intValue = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			intValue += (value[i] & 0x000000FF) << shift;
		}
		return intValue;
	}

	/**
	 * Converts an integer to a byte array.
	 * 
	 * @param value
	 *            given integer
	 * @return the calculated byte[]
	 */
	public static byte[] intToByteArray(int value) {
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16),
				(byte) (value >>> 8), (byte) value };
	}

	/**
	 * Prints a byte array in a human readable version. For debugging reasons.
	 * 
	 * @param ident
	 *            the name of the given array. Printed as prefix to the console
	 * @param input
	 *            the byte array to print
	 */
	public static void printHRByteArray(String ident, byte[] input) {
		System.out.print(ident + ": ");
		int i = 0;
		while (i < input.length) {
			System.out.print((int) input[i]);
			i++;
		}
		System.out.println();
	}

	public static File copyFile(File sourceFile, String destinationString,
			StandardCopyOption option) throws IOException {
		File destinationFile = new File(destinationString);
		Files.copy(sourceFile.toPath(), destinationFile.toPath(), option);
		return destinationFile;
	}
}
