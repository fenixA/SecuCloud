package model;

import java.sql.Timestamp;
import java.util.Date;

import control.Main;

public class InformationContainer {
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
	private Timestamp time;
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
		return time.toString();
	}

	public byte[] getKey() {
		return key;
	}

	public Encryption getEncryption() {
		return encryption;
	}

	public InformationContainer(String localPlainFileLocation,
			String localEncryptedFileLocation, String encryptedName,
			String plainName, String cloudFileLocation, byte[] symKey,
			Encryption encryption) {
		this.time = new Timestamp(new Date().getTime());
		this.localPlainLocation = localPlainFileLocation;
		this.localEncryptedLocation = localEncryptedFileLocation;
		this.encryptedName = encryptedName;
		this.name = plainName;
		this.cloudLocation = cloudFileLocation;
		this.key = symKey;
		this.encryption = encryption;
		this.cloudLocation = Main.getInstance().getBucket() + "/"
				+ encryptedName;
	}
}
