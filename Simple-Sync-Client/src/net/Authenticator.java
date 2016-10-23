package net;

/**
 * 
 * @author Mateusz Rusnaczyk
 * @version 0.0.1
 * @since 0.0.1
 */

public class Authenticator {

	/**
	 * Authenticates the user's credentials with the server
	 * <p>
	 * This method accepts hashed versions of a user's credentials and sends
	 * them to the server. If the server accepts the credentials, it will send
	 * back a session ID and key which this method will then pass to the
	 * ConnectionManager to be stored as a cookie. If the server does not accept
	 * the credentials provided, it will send back an error, and in response,
	 * this method will return null.
	 * <p>
	 * 
	 * @param userHash
	 *            A hashed version of the username provided
	 * @param passwordHash
	 *            A hashed version of the user's password
	 * @return A ConnectionManager that the client will direct all
	 *         communications with the server through
	 */
	public static ConnectionManager logIn(String userHash, String passwordHash) {
		return null;
	}

	/**
	 * Logs a user out
	 * 
	 * <p>
	 * Requests the user's 
	 * </p>
	 * @param connectionManager
	 *            ConnectionManager to be killed
	 * 
	 * @return True if this method successfully logs the user out and kills the
	 *         ConnectionManager. <br>
	 *         False if one of the aforementioned criteria isn't met, or if some
	 *         error occurs.
	 */

	public static boolean logOut(ConnectionManager connectionManager) {
		return false;
	}
}
