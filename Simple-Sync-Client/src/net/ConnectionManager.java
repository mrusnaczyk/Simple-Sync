package net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.*;

/**
 * @author Mateusz Rusnaczyk
 *
 */
public class ConnectionManager {
	private LoginDialog auth;
	private PasswordAuthentication a;
	private String domain;
	
	private TrustManager[] trustAllCerts;
	private SSLContext sc;
	private HostnameVerifier allHostsValid;

	public ConnectionManager(String domain) {
		System.setProperty("http.agent", "SimpleSync/0.0.1 (Windows)");
		
		try{
			this.prepareSSL();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		this.domain = domain;
		auth = new LoginDialog();
		Authenticator.setDefault(auth);
		a = auth.getPasswordAuthentication();
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
			url = new URL("https", domain, "/SimpleSync/genKey.php");
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

	public URLConnection getURLConnection(String path) {
		/**
		 * TODO - Check connect - Check authentication
		 */		
		try {        
			return (HttpURLConnection) (new URL("https", domain, "/SimpleSync" + path).openConnection());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public boolean isAuthenticated(){
		return false;
	}
	
	private void checkIn(){
		//Client sends key to server to confirm 
	}
	
	private void prepareSSL() throws NoSuchAlgorithmException, KeyManagementException{
	       trustAllCerts = new TrustManager[] { new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) { }

	        } };

	        sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        // Create all-trusting host name verifier
	        allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) { return true; }
	        };
	        
	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}
}
