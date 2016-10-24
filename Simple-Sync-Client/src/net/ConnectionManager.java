package net;

import java.net.*;

public class ConnectionManager {

	private CookieManager sessionCookies;

	public ConnectionManager(String sessionID, String authKey, CookieManager cm) {
		sessionCookies = cm;
		CookieHandler.setDefault(sessionCookies); 

	}

}
