package control;

import java.io.File;

import control.crypt.Crypt;
import model.container.InformationContainer;
import model.container.InformationContainer.encryptionIdent;

public class FileComputer {
	public InformationContainer encryptFile(File selectedFile) {
		byte[] tempKey = Crypt.generateRandomKey(16);
		String encryptedName = Crypt.generateLocationString();
		InformationContainer temp = new InformationContainer(
				selectedFile.getAbsolutePath(),
				Main.ENCRYPTED_DATA_LOCATION + encryptedName + ".enc",
				encryptedName,
				selectedFile.getName(),
				null,
				tempKey,
				encryptionIdent.AES_CTR);
		System.out.println("encryptedName: " + Main.ENCRYPTED_DATA_LOCATION + temp.getEncryptedName());
		System.out.println("File selected: " + selectedFile.getAbsolutePath());
		System.out.println(tempKey);

		Crypt.encryptFileSymAesCTR(selectedFile,
				temp.getLocalEncryptedFileLocation(), tempKey);
		return temp;		
	}

}
