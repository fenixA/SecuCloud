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
				selectedFile.getAbsolutePath().replaceAll(selectedFile.getName(), "") + encryptedName,
				encryptedName,
				selectedFile.getName(),
				null,
				tempKey,
				encryptionIdent.AES_CTR);
		System.out.println("encryptedName: " + temp.getEncryptedName());
		System.out.println("File selected: " + selectedFile.getAbsolutePath());
		System.out.println(tempKey);

		Crypt.encryptFileSymAesCTR(selectedFile,
				temp.getLocalEncryptedFileLocation(), tempKey);
		return temp;		
	}

}
