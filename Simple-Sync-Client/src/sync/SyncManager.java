package sync;

import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.TimerTask;

import net.ConnectionManager;

public class SyncManager extends TimerTask{
	private static final Path homeDir = Paths.get(System.getProperty("user.home") + "\\SimpleSync");
	private ConnectionManager manager;

	public SyncManager(ConnectionManager m) {
		manager = m;
		
	}

	public void downloadFile(String path) {
		URLConnection conn;
		InputStream in;
	
		try {
			String query = "/download.php?f=" + URLEncoder.encode(path,"UTF-8");
			conn = manager.getURL(query).openConnection();
			in = conn.getInputStream();

			Files.copy(in, Paths.get(homeDir.toString() + "/" + path), StandardCopyOption.REPLACE_EXISTING);
			in.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	

}