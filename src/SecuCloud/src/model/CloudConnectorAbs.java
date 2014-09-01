package model;

import java.io.File;

public abstract class CloudConnectorAbs {
	abstract Boolean upload(File inputFile);

	abstract String download(File inputFile);

	abstract void listDir(String path);
}
