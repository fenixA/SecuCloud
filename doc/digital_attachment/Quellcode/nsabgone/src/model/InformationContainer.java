package model;

import java.sql.Timestamp;
import java.util.Date;

import control.Main;

/**
 * The Class InformationContainer defines the way in which the information of an
 * encrypted and uploaded file are structured.
 */
public class InformationContainer {

	/** The Constant ATTRIBUTE_LEN defines the length of a single attribute. */
	public static final int ATTRIBUTE_LEN = 256;

	/** The Constant ATTRIBUTES defines the number of attributes. */
	public static final int ATTRIBUTES = 8;
	

	/**
	 * The Enum Encryption defines the different encryption types.
	 */
	public enum Encryption {

		/** The aes ctr. */
		AES_CTR(0),
		/** The aes ecb. */
		AES_ECB(1),
		/** The rsa. */
		RSA(2);

		/** The value. */
		private int value;

		/**
		 * Instantiates a new encryption.
		 * 
		 * @param value
		 *            the value
		 */
		private Encryption(int value) {
			this.value = value;
		}

		/**
		 * Gets the value.
		 * 
		 * @return the value
		 */
		public int getValue() {
			return value;
		}

		/**
		 * Gets the byte array representing the int value.
		 * 
		 * @return the byte array
		 */
		public byte[] getByteArray() {
			return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16),
					(byte) (value >>> 8), (byte) value };
		}
	}

	/** The local plain location. */
	private String localPlainLocation;

	/** The name. */
	private String name;

	/** The encrypted name. */
	private String encryptedName;

	/** The local encrypted location. */
	private String localEncryptedLocation;

	/** The location in the cloud. */
	private String cloudLocation;

	/** The timestamp when upload started. */
	private String time;

	/** The encryption key. */
	private byte[] key;

	/** The encryption type. */
	private Encryption encryption;

	/**
	 * Gets the local plain location.
	 * 
	 * @return the local plain location
	 */
	public String getLocalPlainLocation() {
		return localPlainLocation;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the encrypted name.
	 * 
	 * @return the encrypted name
	 */
	public String getEncryptedName() {
		return encryptedName;
	}

	/**
	 * Gets the local encrypted location.
	 * 
	 * @return the local encrypted location
	 */
	public String getLocalEncryptedLocation() {
		return localEncryptedLocation;
	}

	/**
	 * Gets the cloud location.
	 * 
	 * @return the cloud location
	 */
	public String getCloudLocation() {
		return cloudLocation;
	}

	/**
	 * Sets the cloud location.
	 * 
	 * @param cloudLocation
	 *            the new cloud location
	 */
	public void setCloudLocation(String cloudLocation) {
		this.cloudLocation = cloudLocation;
	}

	/**
	 * Gets the timestamp.
	 * 
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return time;
	}

	/**
	 * Sets the timestamp.
	 * 
	 * @param time
	 *            the new timestamp
	 */
	public void setTimestamp(String time) {
		this.time = time;
	}

	/**
	 * Gets the key.
	 * 
	 * @return the key
	 */
	public byte[] getKey() {
		return key;
	}

	/**
	 * Gets the encryption.
	 * 
	 * @return the encryption
	 */
	public Encryption getEncryption() {
		return encryption;
	}

	/**
	 * Instantiates a new information container.
	 * 
	 * @param localPlainLocation
	 *            the local plain location
	 * @param localEncryptedLocation
	 *            the local encrypted location
	 * @param encryptedName
	 *            the encrypted name
	 * @param name
	 *            the name
	 * @param key
	 *            the key
	 * @param encryption
	 *            the encryption
	 */
	public InformationContainer(String localPlainLocation,
			String localEncryptedLocation, String encryptedName, String name,
			byte[] key, Encryption encryption) {
		this.time = (new Timestamp(new Date().getTime())).toString();
		this.localPlainLocation = localPlainLocation;
		this.localEncryptedLocation = localEncryptedLocation;
		this.encryptedName = encryptedName;
		this.name = name;
		this.key = key;
		this.encryption = encryption;
		this.cloudLocation = Main.getInstance().getBucket() + Main.CLOUD_SEPERATOR
				+ encryptedName;
	}
}
