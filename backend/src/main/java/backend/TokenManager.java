package backend;

import java.util.HashMap;
import java.util.Map;

/**
 * The TokenManager class manages user tokens and their associated user IDs.
 */
public class TokenManager {

    /**
     * HashMap to store the user ID as the key and the associated token as the
     * value.
     */
    private static final Map<Integer, String> userIDtoToken = new HashMap<>();

    /**
     * Reverse lookup HashMap to store the token as the key and the associated user
     * ID as the value.
     */
    private static final Map<String, Integer> TokentoUserID = new HashMap<>();

    /**
     * Adds a token and its associated user ID to the manager.
     *
     * @param userID The user ID to associate with the token.
     * @param token  The token to be added.
     */
    public static void addToken(int userID, String token) {
        userIDtoToken.put(userID, token);
        TokentoUserID.put(token, userID); // Update the reverse lookup map
    }

    /**
     * Retrieves the token associated with the given user ID.
     *
     * @param userID The user ID for which to retrieve the token.
     * @return The token associated with the user ID, or null if no token is found.
     */
    public static String getToken(int userID) {
        return userIDtoToken.get(userID);
    }

    /**
     * Retrieves the user ID associated with the given token.
     *
     * @param token The token to retrieve the ID.
     * @return The user ID associated with the token, or 0 if the token is not
     *         found.
     */
    public static int getUserID(String token) {
        Integer userID = TokentoUserID.get(token);
        return userID != null ? userID : 0; // Return 0 if token is not found
    }

    /**
     * Checks if the TokenManager contains the given token.
     *
     * @param token The token to check for.
     * @return True if the token is found in the TokenManager, otherwise false.
     */
    public static boolean containsToken(String token) {
        return TokentoUserID.containsKey(token);
    }

    /**
     * Removes all tokens associated with the given user ID from the manager.
     *
     * @param userId The user ID for which to remove all tokens.
     */
    public static void removeToken(int userId) {
        String token = userIDtoToken.remove(userId);
        if (token != null) {
            TokentoUserID.remove(token); // Remove the token from the reverse lookup map
        }
    }
}