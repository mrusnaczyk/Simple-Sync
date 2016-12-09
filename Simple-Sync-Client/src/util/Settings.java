package util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Settings {
	// Location of the user's SimpleSync Folder
	public static final Path homeDir = Paths.get(System.getProperty("user.home") + "\\SimpleSync");
	// IP or FQDN of the server
	public static final String domain = "cs.rusnaczyk.tk";
	// How often the server will sync in seconds
	public static final int syncInterval = 5;
	// Nothing important down here
	public static final String secretSecret = "frUEEUzL4za4fJVSWqWz5v=sUjXe%b!V+Sv=fVx!b?GnRUHGzn"
			+ "&_-ff%bxy?Dfb6WLJj?f@G!JjrNgJSz=+J=^WasgdcYPkDdtZGXN-U3JsZD9@p_$@4FcJRC5aJ_QyJqu4v"
			+ "P-Vn?Q6HZ6^B8!VKj%Vc?z+FuG$uBCGE=+snR=FRt+4R$M&*E7kPffMR4hV+FKX3y-4EtR%kq@%S%sEsCv"
			+ "2zUsGnqqsrhCq=t@uq5EMcuYN&Q=UQ!P#pedhqRzLG";

}
