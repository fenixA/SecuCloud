package model;

import java.sql.Timestamp;
import java.util.Date;


public class InformationContainer {
	public enum encryptionIdent {
		AES_CTR,
		AES_ECB,
		RSA
	}

	private String localPlainFileLocation;

	public String getLocalPlainFileLocation() {
		return localPlainFileLocation;
	}

	private String plainName;

	public String getPlainName() {
		return plainName;
	}

	private String encryptedName;

	public String getEncryptedName() {
		return encryptedName;
	}

	private String localEncryptedFileLocation;

	public String getLocalEncryptedFileLocation() {
		return localEncryptedFileLocation;
	}

	private String cloudFileLocation;

	public String getCloudFileLocation() {
		return cloudFileLocation;
	}

	public void setCloudFileLocation(String cloudFileLocation) {
		this.cloudFileLocation = cloudFileLocation;
	}

	private Timestamp time;
	
	public String getTimestamp(){
		return time.toString();
	}
	
	private byte[] symKey;

	public byte[] getSymKey() {
		return symKey;
	}

	private encryptionIdent encryption;

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
	}
}
