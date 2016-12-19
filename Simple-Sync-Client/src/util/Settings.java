package util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Settings {
	// Location of the user's SimpleSync Folder
	public static final Path homeDir = Paths.get(System.getProperty("user.home") + "\\SimpleSync");
	// IP or FQDN of the server
	public static String domain = "";
	// True when the client is wiping its local directory
	public static boolean isWiping = false;
	// How often the server will sync in seconds
	public static int syncInterval = 10;
	// Name of the computer
	public static final String compName = getComputerName();
	// True when the client is set to auto-sync
	public static boolean AutoSync = false;
	// A key.
	public static final String secretSecret = "WfZ4nLWscKIxxsUFazYQSaJ94FQnsu8AoyPjXNI";

	// http://stackoverflow.com/questions/7883542/getting-the-computer-name-in-java
	private static String getComputerName() {
		Map<String, String> env = System.getenv();
		if (env.containsKey("COMPUTERNAME"))
			return env.get("COMPUTERNAME");
		else if (env.containsKey("HOSTNAME"))
			return env.get("HOSTNAME");
		else
			return "Unknown Computer";
	}

}
