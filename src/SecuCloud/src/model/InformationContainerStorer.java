package model;

import java.util.ArrayList;

import control.Main;

public class InformationContainerStorer {

	public boolean storeFileList() {
		ArrayList<InformationContainer> fileList = Main.getInstance()
				.getFileList();
		for (InformationContainer ic : fileList) {
			if (!storeInformationContainer(ic)) {
				return false;
			}
		}
		return true;
	}

	private boolean storeInformationContainer(InformationContainer input) {
		return true;
	}
}
