import java.io.IOException;
import java.net.Authenticator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.ConnectionManager;

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

		// Authenticator.setDefault(new LoginAuthenticator());
		ConnectionManager c = new ConnectionManager();
		System.out.println("Temporary Key: " + String.valueOf(c.getKey()));
		/*String test = c.getKey();
		System.out.println(test);
		System.exit(0);*/
		
	}

}
