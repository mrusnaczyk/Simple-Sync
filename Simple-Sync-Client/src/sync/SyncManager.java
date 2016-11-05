package sync;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.ConnectionManager;

public class SyncManager {
	private static final Path homeDir = Paths.get(System.getProperty("user.home") + "\\SimpleSync");
	private ConnectionManager manager;

	public SyncManager(ConnectionManager m) {
		manager = m;
	}

	public void downloadFile(String path) {
		URLConnection conn;
		BufferedInputStream in;
		FileWriter writer;

		try {
			String query = "/sync/download.php?f=" + URLEncoder.encode(path, "UTF-8");
			conn = manager.getURL(query).openConnection();
			in = new BufferedInputStream(conn.getInputStream());
			writer = new FileWriter(new File(Paths.get(homeDir.toString()) + "/" + path));

			int incoming = 0;
			while ((incoming = in.read()) != -1) {
				writer.write(incoming);
			}
			writer.close();

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

	}

}
