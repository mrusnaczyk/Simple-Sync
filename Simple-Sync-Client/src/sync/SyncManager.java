package sync;

import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.ConnectionManager;

public class SyncManager extends TimerTask{
	private ConnectionManager manager;

	public SyncManager(ConnectionManager m) {
		manager = m;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		ArrayList<FileOperation> operations = new ArrayList<FileOperation>();
		ArrayList<String> result = new ArrayList<String>();
		
		operations.add(new FileDownload(manager, "test_file/NextcloudUserManual.pdf"));
		operations.add(new FileDownload(manager, "test_file/testpic.png"));
		
		ExecutorService exec = Executors.newFixedThreadPool(operations.size());
		
		for(FileOperation o : operations){
			try {
				result.add(exec.submit((Callable<String>) o).get());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}	
	
}
