package net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class ConnectionManager {
	LoginDialog auth;
	PasswordAuthentication a;

	public ConnectionManager(){
		System.setProperty("http.agent", "SimpleSync/0.0.1 (Windows)");
		// auth = new LoginDialog();
		// Authenticator.setDefault(auth);
		// Authenticator.requestPasswordAuthentication("",
		// InetAddress.getByName("cs.rusnaczyk.tk"), 80, "", "", "");

	}

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

	public void revokeKey(String key) {
		// Add throws exception
	}
}
