package util;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Settings {
	//Location of the user's SimpleSync Folder
	public static final Path homeDir = Paths.get(System.getProperty("user.home") + "\\SimpleSync");
	//IP or FQDN of the server
	public static final String domain = "159.203.46.15";
	//How often the server will sync in seconds
	public static final int syncInterval = 5;
	//
}
