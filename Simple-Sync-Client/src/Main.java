import java.io.IOException;
import java.net.Authenticator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.ConnectionManager;
import sync.SyncManager;

public class Main {
	// Path where files are stored
	public static final Path homeDir = Paths.get(System.getProperty("user.home") + "\\SimpleSync");

	public static void main(String[] args) {
		// Check if main SimpleSync directory exists, if not, create it
		if (!Files.exists(homeDir)) {
			System.out.println("No SimpleSync dir... making a new one");
			try {
				Files.createDirectory(homeDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Check if settings file exists, if not, create it
		if (!Files.exists(Paths.get(homeDir.toString() + "\\.settings.txt"))) {
			try {
				Files.createFile(Paths.get(homeDir.toString() + "\\.settings.txt"));
				Files.setAttribute(Paths.get(homeDir.toString() + "\\.settings.txt"), "dos:hidden", true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ConnectionManager c = new ConnectionManager("159.203.46.15");
		System.out.println(String.valueOf(c.getKey()));
		SyncManager sync = new SyncManager(c);
		sync.downloadFile("test_file/test.pdf");
		sync.downloadFile("test_file/testpic.png");
		//sync.downloadFile("test_file/test01.txt");
		//sync.downloadFile("test_file/test02.txt");
		
	}
}
