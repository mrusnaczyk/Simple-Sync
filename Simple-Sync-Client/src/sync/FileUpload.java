package sync;

import java.io.BufferedOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import net.ConnectionManager;
import util.Settings;

public class FileUpload extends FileOperation implements Callable<String> {

	private URL u;
	private String p;
	private String q;

	public FileUpload(ConnectionManager m, String filePath) {
		p = filePath;

		try {
			q = "/upload.php?f=" + URLEncoder.encode(filePath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		u = m.getURL(q);
	}

	public String call() {
		URLConnection conn;
		BufferedOutputStream out;

		try {
			conn = u.openConnection();
			out = new BufferedOutputStream(conn.getOutputStream());

			Files.copy(Paths.get(Settings.homeDir.toString() + "/" + p), out);
			out.flush();
			out.close();

		} catch (Exception e) {
			return e.getMessage();
		}

		return "OK";
	}

}
