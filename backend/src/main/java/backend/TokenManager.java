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
    private static final Map<Integer, String> hashMap = new HashMap<>();

    /**
     * Adds a token and its associated user ID to the manager.
     *
     * @param userId The user ID to associate with the token.
     * @param token  The token to be added.
     */
    public static void addToken(int userId, String token) {
        hashMap.put(userId, token);
    }

    /**
     * Retrieves the token associated with the given user ID.
     *
     * @param userId The user ID for which to retrieve the token.
     * @return The token associated with the user ID, or null if no token is found.
     */
    public static String getToken(int userId) {
        return hashMap.get(userId);
    }

    /**
     * Checks if the TokenManager contains the given token.
     *
     * @param token The token to check for.
     * @return True if the token is found in the TokenManager, otherwise false.
     */
    public static boolean containsToken(String token) {
        return hashMap.containsValue(token);
    }

    /**
     * Removes all tokens associated with the given user ID from the manager.
     *
     * @param userId The user ID for which to remove all tokens.
     */
    public static void removeToken(int userId) {
        hashMap.entrySet().removeIf(entry -> entry.getKey() == userId);
    }
}