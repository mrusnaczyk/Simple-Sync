

import java.io.IOException;
import java.nio.file.*;

public class Main {
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

	}
}
