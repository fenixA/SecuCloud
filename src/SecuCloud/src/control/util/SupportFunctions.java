package control.util;

public class SupportFunctions {
	public static final int INT_BYTE_SIZE = 4;

	public static int byteArrayToInt(byte[] value) {
		int intValue = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			intValue += (value[i] & 0x000000FF) << shift;
		}
		return intValue;
	}

	public static byte[] intToByteArray(int value) {
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16),
				(byte) (value >>> 8), (byte) value };
	}
}
