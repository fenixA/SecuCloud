package model;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import control.FileListHandler;
import control.Main;

public class InformationContainerStorer {

	public boolean storeFileList() throws IOException {
		Vector<InformationContainer> fileList = FileListHandler.getInstance()
				.getFileList();
		for (InformationContainer ic : fileList) {
			if (!storeInformationContainer(ic)) {
				return false;
			}
		}
		return true;
	}

	private boolean storeInformationContainer(InformationContainer input) throws IOException {
		File file =  new File(Main.getInstance().getUSER_DATA_DIR() + "/freeze.conf");
		if(!file.exists()){
			
			file.createNewFile();
		}
		return true;
	}
}
