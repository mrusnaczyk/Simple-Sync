package sync;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.nio.file.Files;
import java.nio.file.Path;
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
	private Authenticator auth;

	public SyncManager(ConnectionManager m) {
		manager = m;
		auth = manager.auth;
		Authenticator.setDefault(auth);
	}

	public void run() {
		operations = new ArrayList<FileOperation>();
		result = new ArrayList<String>();
		getSettings();
		if(Settings.isWiping){
			wipe(Settings.homeDir);
			Settings.isWiping = false;
		}if(Settings.AutoSync){
			getRemoteDirectory();
			sync();
		}
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
	
	//Wipes a user's local SimpleSync folder at the command of the server
	private void wipe(Path p){
		File[] files = p.toFile().listFiles();
		for(File f : files){
			if(f.isDirectory())
				wipe(Paths.get(p.toString()+"/"+f.getName()));
			f.delete();
		}
		
		
	}
	
	private void getSettings(){
		HttpsURLConnection conn;

		BufferedInputStream in;
		JSONTokener tokener;
		JSONObject tree;

		try {
			conn = (HttpsURLConnection) manager.getURLConnection("/sync/getSettings.php");
			conn.connect();

			in = new BufferedInputStream(conn.getInputStream());
			tokener = new JSONTokener(in);
			tree = new JSONObject(tokener);

			int SyncInterval = Integer.parseInt(tree.getString("SyncInterval"));
			int doWipe = Integer.parseInt(tree.getString("WipeLocal"));
			int AutoSync = Integer.parseInt(tree.getString("AutoSync"));
			
			System.out.println("Sync Interval: " + SyncInterval + " :: Wipe? " + doWipe);
			
			Settings.syncInterval = SyncInterval;
			if(doWipe == 1)
				Settings.isWiping = true;
			if(AutoSync == 1)
				Settings.AutoSync = true;
			else if(AutoSync == 0)
				Settings.AutoSync = false;
			

		} catch (Exception e) {
			//e.printStackTrace();
			System.exit(0);
		}
	}

	private void getRemoteDirectory() {
		HttpsURLConnection conn;

		BufferedInputStream in;
		JSONTokener tokener;
		JSONObject tree;

		try {
			conn = (HttpsURLConnection) manager.getURLConnection("/sync/remoteFileTree.php");
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
			
			//If the object is a file and doesn't already exist in the local folder, download it
			if (type.equals("FILE") && Files.notExists(Paths.get(Settings.homeDir.toString() + "/" + folder + "/" + fileName))){
				operations.add(new FileDownload(manager, folder + "/" + fileName));
				System.out.println("Downloading: " + folder + "/" + fileName);
			} else if (type.equals("DIR")) {
				//If the folder doesn't already exist, create it
				if (!Files.isDirectory(Paths.get(Settings.homeDir.toString() + "/" + folder + "/" + fileName))) {
					Files.createDirectory(Paths.get(Settings.homeDir.toString() + "/" + folder + "/" + fileName));
				}
				parseJSON(a.getJSONObject("Files"), folder + "/" + fileName);
			}

		}

	}


}
