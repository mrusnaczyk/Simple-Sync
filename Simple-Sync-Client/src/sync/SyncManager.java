package sync;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.*;
import org.json.*;

import net.ConnectionManager;
import sync.file.FileDownload;
import sync.file.FileOperation;
import util.Settings;

public class SyncManager extends TimerTask {
	private ConnectionManager manager;
	private ArrayList<FileOperation> operations;
	private ArrayList<String> result;

	public SyncManager(ConnectionManager m) {
		manager = m;
	}

	public void run() {
		operations = new ArrayList<FileOperation>();
		result = new ArrayList<String>();

		getRemoteDirectory();
		sync();
	}

	@SuppressWarnings("unchecked")
	private void sync() {
		if (operations.size() > 0) {
			ExecutorService exec = Executors.newFixedThreadPool(operations.size());

			for (FileOperation o : operations) {
				try {
					result.add(exec.submit((Callable<String>) o).get());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void getRemoteDirectory() {
		HttpsURLConnection conn;

		BufferedInputStream in;
		JSONTokener tokener;
		JSONObject tree;

		try {
			conn = (HttpsURLConnection) manager.getURLConnection("/remoteFileTree.php");
			conn.connect();

			in = new BufferedInputStream(conn.getInputStream());
			tokener = new JSONTokener(in);
			tree = new JSONObject(tokener);

			parseJSON(tree, "");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void parseJSON(JSONObject tree, String folder) throws IOException {
		for (int i = 0; i < tree.length(); i++) {
			JSONObject a = tree.getJSONObject(String.valueOf(i));
			String type = a.getString("Type");
			String fileName = a.getString("FileName");
			//System.out.println(folder + "/" + fileName);
			if (type.equals("FILE")) {
				//File f = new File(Settings.homeDir.toString() + "/"+folder + "/" + fileName);
				//long lastModifiedRemote = Long.valueOf(a.getString("LastModified"));
				//if (!f.exists()) {
				operations.add(new FileDownload(manager, folder + "/" + fileName));
				//System.out.println("Downloading: " + folder + "/" + fileName);
				//}
			} else if (type.equals("DIR")) {
				if (!Files.isDirectory(Paths.get(Settings.homeDir.toString() + "/" + folder + "/" + fileName))) {
					Files.createDirectory(Paths.get(Settings.homeDir.toString() + "/" + folder + "/" + fileName));
				}
				parseJSON(a.getJSONObject("Files"), folder + "/" + fileName);
			}

		}

	}


}
