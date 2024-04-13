package backend;

import java.util.HashMap;
import java.util.Map;

import java.security.SecureRandom;

/**
 * The SessionManger class manages user tokens and their associated sessions.
 */
public class SessionManager {

    /**
     * Our chars we want to use for our session
     */
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    /**
     * Constructs a secure random number generator (RNG) implementing the default
     * random number algorithm
     */
    static SecureRandom rnd = new SecureRandom();

    /**
     * Sets the session token length
     */
    static final int LEN = Integer.parseInt(System.getenv("SESSION_LENGTH"));

    /**
     * From https://stackoverflow.com/a/157202
     * Generate a new random token
     * 
     * @return a randomized session token
     */
    private static String randomString() {
        StringBuilder sb = new StringBuilder(LEN);
        for (int i = 0; i < LEN; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    /**
     * HashMap to store the session as the key and the associated token as the
     * value.
     */
    private static final Map<Session, String> sessiontoToken = new HashMap<>();

    /**
     * Reverse lookup HashMap to store the token as the key and the associated user
     * ID as the value.
     */
    private static final Map<String, Session> tokentoSession = new HashMap<>();

    /**
     * Adds a token and its associated session to the manager.
     *
     * @param session The session to associate with the token.
     * @return the user session token
     */
    public static String addToken(Session session) {
        String token = randomString();
        sessiontoToken.put(session, token);
        tokentoSession.put(token, session); // Update the reverse lookup map
        return token; // Returns user session token
    }

    /**
     * Retrieves the session token associated with the given session
     *
     * @param session The session for which to retrieve the token.
     * @return The token associated with the session, or null if no token is found.
     */
    public static String getToken(Session session) {
        return sessiontoToken.get(session);
    }

    /**
     * Retrieves the token associated with the given session string
     *
     * @param token The session string for which to retrieve the token.
     * @return The session associated with the string, or null if no token is found.
     */
    public static Session getSession(String token) {
        return tokentoSession.get(token);
    }

    /**
     * Retrieves the session associated with the given token.
     *
     * @param token The token to retrieve the ID.
     * @return The userID associated with the token, or 0 if the token is not
     *         found.
     */
    public static int getUserID(String token) {
        Session session = tokentoSession.get(token);
        return session != null ? session.userID : 0; // Return 0 if token is not found
    }

    /**
     * Checks if the SessionManager contains the given token.
     *
     * @param session The session to check for.
     * @return True if the token is found in the SessionManager, otherwise false.
     */
    public static boolean containsToken(Session session) {
        return sessiontoToken.containsKey(session);
    }

    /**
     * Checks if the SessionManager contains the given token.
     *
     * @param token The token to check for.
     * @return True if the token is found in the SessionManager, otherwise false.
     */
    public static boolean containsSession(String token) {
        return tokentoSession.containsKey(token);
    }

    /**
     * Removes all tokens associated with the given session from the manager.
     *
     * @param session The session for which to remove all tokens.
     */
    public static void removeToken(Session session) {
        String token = sessiontoToken.remove(session);
        if (token != null) {
            tokentoSession.remove(token); // Remove the token from the reverse lookup map
        }
    }
}