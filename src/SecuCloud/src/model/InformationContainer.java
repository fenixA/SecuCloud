package model;

import java.sql.Timestamp;
import java.util.Date;

import control.Main;

public class InformationContainer {
	public enum encryptionIdent {
		AES_CTR, AES_ECB, RSA
	}

	private String localPlainFileLocation;
	private String plainName;
	private String encryptedName;
	private String localEncryptedFileLocation;
	private String cloudFileLocation;
	private Timestamp time;
	private byte[] symKey;
	private encryptionIdent encryption;

	public String getLocalPlainFileLocation() {
		return localPlainFileLocation;
	}
	public String getPlainName() {
		return plainName;
	}
	public String getEncryptedName() {
		return encryptedName;
	}
	public String getLocalEncryptedFileLocation() {
		return localEncryptedFileLocation;
	}
	public String getCloudFileLocation() {
		return cloudFileLocation;
	}
	public void setCloudFileLocation(String cloudFileLocation) {
		this.cloudFileLocation = cloudFileLocation;
	}
	public String getTimestamp() {
		return time.toString();
	}
	public byte[] getSymKey() {
		return symKey;
	}
	public encryptionIdent getEncryption() {
		return encryption;
	}

	public InformationContainer(String localPlainFileLocation,
			String localEncryptedFileLocation, String encryptedName,
			String plainName, String cloudFileLocation, byte[] symKey,
			encryptionIdent encryption) {
		this.time = new Timestamp(new Date().getTime());
		this.localPlainFileLocation = localPlainFileLocation;
		this.localEncryptedFileLocation = localEncryptedFileLocation;
		this.encryptedName = encryptedName;
		this.plainName = plainName;
		this.cloudFileLocation = cloudFileLocation;
		this.symKey = symKey;
		this.encryption = encryption;
		this.cloudFileLocation = Main.getInstance().getBucket() + "/" + encryptedName;
	}
}
