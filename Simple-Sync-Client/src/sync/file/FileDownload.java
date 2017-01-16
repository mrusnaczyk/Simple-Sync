/**
 * @author Mateusz Rusnaczyk
 * 
 */

package sync.file;

import java.io.BufferedInputStream;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Callable;

import net.ConnectionManager;
import util.Settings;

public class FileDownload extends FileOperation implements Callable<String> {
	private String q;
	private String p;
	private ConnectionManager manager;
	private Authenticator auth;

	public FileDownload(ConnectionManager m, String filePath) {
		p = filePath;

		try {
			q = "/sync/download.php?f=" + URLEncoder.encode(filePath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		manager = m;
		auth = manager.auth;
		Authenticator.setDefault(auth);
	}

	public String call() {
		URLConnection conn;
		BufferedInputStream in;

		try {
			conn = manager.getURLConnection(q);
			in = new BufferedInputStream(conn.getInputStream());

			Files.copy(in, Paths.get(Settings.homeDir.toString() + "/" + p), StandardCopyOption.REPLACE_EXISTING);
			in.close();

		} catch (Exception e) {
			return e.getMessage();
		}

		return "OK";
	}

}
