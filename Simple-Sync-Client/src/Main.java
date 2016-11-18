import java.io.IOException;
import java.net.Authenticator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import net.ConnectionManager;
import util.Settings;
import sync.SyncManager;

public class Main {

	public static void main(String[] args) {
		createUserDir();
		
		ConnectionManager c = new ConnectionManager(Settings.domain);
		System.out.println(String.valueOf(c.getKey()));
		//SyncManager sync = new SyncManager(c);
		Timer t = new Timer();
		t.schedule(new SyncManager(c),0, Settings.syncInterval * 1000);

		
	}
	
	private static void createUserDir(){
		// Check if main SimpleSync directory exists, if not, create it
		if (!Files.exists(Settings.homeDir)) {
			System.out.println("No SimpleSync dir... making a new one");
			try {
				Files.createDirectory(Settings.homeDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Check if settings file exists, if not, create it
		if (!Files.exists(Paths.get(Settings.homeDir.toString() + "\\.settings.txt"))) {
			try {
				Files.createFile(Paths.get(Settings.homeDir.toString() + "\\.settings.txt"));
				Files.setAttribute(Paths.get(Settings.homeDir.toString() + "\\.settings.txt"), "dos:hidden", true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
