package sync;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.*;

import net.ConnectionManager;

public class SyncManager extends TimerTask {
	private ConnectionManager manager;

	public SyncManager(ConnectionManager m) {
		manager = m;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		ArrayList<FileOperation> operations = new ArrayList<FileOperation>();
		ArrayList<String> result = new ArrayList<String>();
		
		getRemoteTree();

		operations.add(new FileDownload(manager, "test_file/NextcloudUserManual.pdf"));
		operations.add(new FileDownload(manager, "test_file/testpic.png"));
		ExecutorService exec = Executors.newFixedThreadPool(operations.size());

		for (FileOperation o : operations) {
			try {
				result.add(exec.submit((Callable<String>) o).get());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		manager.revokeKey("lol");

	}

	private void sync() {
		/*
		 * 1) getRemoteTree() - Returns a JSON representation of the user's remote storage 
		 * 2) Parse JSON file - determine which files to upload and which to download; 
		 * 3) Add file requests to queue
		 */
	}

	private String[][] getRemoteTree() {
		URL u = manager.getURL("/remoteFileTree.php");
		URLConnection conn;
		BufferedInputStream in;
		JSONTokener tokener;
		JSONObject tree;

		try {
			conn = u.openConnection();
			in = new BufferedInputStream(conn.getInputStream());
			tokener = new JSONTokener(in);
			tree = new JSONObject(tokener);
			System.out.println(tree.toString());
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		return null;
	}

}
