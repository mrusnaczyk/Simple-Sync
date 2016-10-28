package net;

import java.net.*;

public class ConnectionManager {

	private CookieManager sessionCookies;

	public ConnectionManager(CookieManager cm) {
		sessionCookies = cm;
		CookieHandler.setDefault(sessionCookies); 

		System.out.println(sessionCookies.getCookieStore().getCookies().toString());
		
	}
	
	
	public void getRemoteDirectoryTree(){
		
	}
	
	
	

}
