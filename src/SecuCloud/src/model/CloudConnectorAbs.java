package model;

public abstract class CloudConnectorAbs {
	abstract Boolean upload(String path);

	abstract String download(String path);

	abstract String listDir(String path);
}
