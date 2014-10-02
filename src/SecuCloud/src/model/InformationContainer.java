package model;

import java.sql.Timestamp;
import java.util.Date;

import control.Main;

public class InformationContainer {
	public static final int ATTRIBUTE_LEN = 256;
	public static final int ATTRIBUTES = 8;
	public enum Encryption {
		AES_CTR(0), AES_ECB(1), RSA(2);
		private int value;

		private Encryption(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
		
		public byte[] getByteArray() {
			return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16),
					(byte) (value >>> 8), (byte) value };
		}
	}

	private String localPlainLocation;
	private String name;
	private String encryptedName;
	private String localEncryptedLocation;
	private String cloudLocation;
	private String time;
	private byte[] key;
	private Encryption encryption;

	public String getLocalPLainLocation() {
		return localPlainLocation;
	}

	public String getName() {
		return name;
	}

	public String getEncryptedName() {
		return encryptedName;
	}

	public String getLocalEncryptedLocation() {
		return localEncryptedLocation;
	}

	public String getCloudLocation() {
		return cloudLocation;
	}

	public void setCloudLocation(String cloudLocation) {
		this.cloudLocation = cloudLocation;
	}

	public String getTimestamp() {
		return time;
	}
	
	public void setTimestamp(String time) {
		this.time = time;
	}

	public byte[] getKey() {
		return key;
	}

	public Encryption getEncryption() {
		return encryption;
	}

	public InformationContainer(String localPlainLocation,
			String localEncryptedLocation, String encryptedName,
			String name, byte[] key,
			Encryption encryption) {
		this.time = (new Timestamp(new Date().getTime())).toString();
		this.localPlainLocation = localPlainLocation;
		this.localEncryptedLocation = localEncryptedLocation;
		this.encryptedName = encryptedName;
		this.name = name;
		this.key = key;
		this.encryption = encryption;
		this.cloudLocation = Main.getInstance().getBucket() + "/"
				+ encryptedName;
	}
}
