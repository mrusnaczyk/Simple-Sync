package net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

/**
 * @author Mateusz Rusnaczyk
 *
 */
public class ConnectionManager {
	private LoginDialog auth;
	private PasswordAuthentication a;
	private String domain;

	public ConnectionManager(String domain) {
		System.setProperty("http.agent", "SimpleSync/0.0.1 (Windows)");
		this.domain = domain;
	}

	/**
	 * Returns an authentication token for the client to use to sync files. This
	 * method requires that the user has supplied valid credentials.
	 * 
	 * @return An authentication token as a char array
	 */
	public char[] getKey() {
		// Add throws exception
		// a = auth.getPasswordAuthentication();
		URL url;
		URLConnection conn;
		char[] key = null;
		String k = "";
		try {
			url = new URL("http", "cs.rusnaczyk.tk", "/SimpleSync/genKey.php");
			conn = url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			int c;
			while ((c = in.read()) != -1) {
				k += (char) c;
			}
			key = k.toCharArray();
			k = null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return key;

	}

	/**
	 * Revokes a key generated by {@link getKey()}
	 * 
	 * <p>
	 * This method should be called after an object no longer needs the
	 * authentication key it received from {@link getKey()}
	 * </p>
	 * 
	 * @param key
	 *            The authentication key to be revoked
	 */
	public void revokeKey(String key) {
		// Add throws exception
	}

	public URL getURL(String path) throws UnknownHostException, IOException {
		/**
		 * TODO - Check connect - Check authentication
		 */
		return new URL("http", domain, "/SimpleSync" + path);
	}
}
