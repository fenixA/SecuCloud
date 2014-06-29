package secucloud;

public class Main {
	public static void main(String[] args) {
		System.out.println("test");
		CloudConnectorGoogleGsutil cloudconnector = new CloudConnectorGoogleGsutil();
		cloudconnector.upload("TestStringUpload");
		System.out.println(cloudconnector.download("TestStringDownload"));
		System.out.println(cloudconnector.listDir("TestStringListDir"));
	}
}
