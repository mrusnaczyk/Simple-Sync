package core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;

import net.LoginAuthenticator;
import ui.LoginUI;

public class Main {
	public static final Path homeDir = Paths.get(System.getProperty("user.home") + "\\SimpleSync");

	public static void main(String[] args) throws MalformedURLException, IOException {
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

		// Open the login GUI
		Thread ui = new LoginUI("cs.rusnaczyk.tk");
		ui.start();

	}

	public static void setLoginInfo(String host, String user, String password) {
		try {
			LoginAuthenticator.logIn(user, password, new URL("http", "cs.rusnaczyk.tk", 80, "/phpTest/submit.php"));
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

	}
}
