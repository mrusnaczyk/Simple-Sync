package net;

import java.io.BufferedInputStream;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;

import util.Settings;

/**
 * @author Mateusz Rusnaczyk
 *
 */
public class ConnectionManager {
	public LoginDialog auth;
	private CookieManager chocolateChip;
	private String domain;
	
	//private TrustManager[] trustAllCerts;
	private TrustManager[] trustManagers;
	private SSLContext sc;
	private HostnameVerifier hostVerify;

	
	public ConnectionManager(String domain, LoginDialog d) {
		System.setProperty("http.agent", "SimpleSync/0.0.1 (Windows)");
		System.setProperty("javax.net.ssl.keyStore", "keyStore");
		System.setProperty("javax.net.ssl.keyStorePassword", "keyStorePassword");
		
		try{
			this.prepareSSL();		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		this.domain = domain;
		auth = d;
		Authenticator.setDefault(auth);
		chocolateChip = new CookieManager();
		CookieHandler.setDefault(chocolateChip);
		verifyClient();
		System.out.println(chocolateChip.getCookieStore().getCookies().toString());
		
	}

	private void verifyClient(){
		try{
			String secrets = URLEncoder.encode(Settings.secretSecret, "UTF-8");
			String client = URLEncoder.encode(Settings.compName, "UTF-8");
			CookieHandler.setDefault(chocolateChip);
			HttpsURLConnection conn = (HttpsURLConnection) getURLConnection("/auth/clientVerify.php?key=" + secrets + "&clientName=" + client);
			BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
			
			String result = "";
			int c;
			while((c= in.read()) != -1){
				result += (char) c;
			}
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Uh oh! Something went wrong! Restart the program and try again.","Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	public URLConnection getURLConnection(String path) {	
		try {        
			return (HttpURLConnection) (new URL("https", domain, "/SimpleSync" + path).openConnection());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	private void prepareSSL() throws NoSuchAlgorithmException, KeyManagementException{	
		try{

			trustManagers = new TrustManager[] { new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) { }

	        } };

	        sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustManagers, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        hostVerify = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) { return true; }
	        };
	        
	        HttpsURLConnection.setDefaultHostnameVerifier(hostVerify);
	        
		}
		catch(Exception e)
		{
			e.printStackTrace(System.out);
		}
	}
}
