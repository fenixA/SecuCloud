package secucloud;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class CloudConnectorGoogleGSCClientLibrary extends CloudConnectorAbs {

	public Boolean upload(String path) {
		System.out.print("Upload: ");
		System.out.println(path);
		return true;
	}

	public String download(String path) {
		return "Download: " + path;

	}

	public String listDir(String path) {
		return "listDir: " + path;
	}
}
