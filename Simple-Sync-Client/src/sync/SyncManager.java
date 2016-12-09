package sync;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.*;
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
	}

	private ArrayList<FileOperation> sync() {
		/*
		 * 1) getRemoteTree() - Returns a JSON representation of the user's remote storage 
		 * 2) Parse JSON file - determine which files to upload and which to download; 
		 * 3) Add file requests to queue
		 */
		JSONObject remoteTree = getRemoteTree();
		
		return null;
	}
	


	private JSONObject getRemoteTree() {
		//URL u = manager.getURL("/remoteFileTree.php");
		HttpsURLConnection conn;		
		
		BufferedInputStream in;
		JSONTokener tokener;
		JSONObject tree;
		try{
			conn = (HttpsURLConnection) manager.getURLConnection("/remoteFileTree.php");
			conn.connect();
			
			
			in = new BufferedInputStream(conn.getInputStream());
			tokener = new JSONTokener(in);
			tree = new JSONObject(tokener);
			//return new JSONObject(tokener);
			System.out.println(tree.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//Get the tree from the server
//		try {
//			//conn = u.openConnection();
//			in = new BufferedInputStream(conn.getInputStream());
//			tokener = new JSONTokener(in);
//			tree = new JSONObject(tokener);
//			//return new JSONObject(tokener);
//			System.out.println(tree.toString());
//		} catch (Exception e) {
//			e.printStackTrace(System.out);
//		}
		
		//Parse the tree and convert it into an array
		
		return null;
	}
	

}
