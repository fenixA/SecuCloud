package secucloud;

public class Main {
	public static void main(String[] args) {
		CloudConnectorGoogleGsutilTEMP cloudconnector = new CloudConnectorGoogleGsutilTEMP();
		cloudconnector.upload("TestStringUpload");
		System.out.println(cloudconnector.download("TestStringDownload"));
		System.out.println(cloudconnector.listDir("TestStringListDir"));
	}
}
