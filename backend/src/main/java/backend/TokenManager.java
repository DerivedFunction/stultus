package backend;

import java.util.HashMap;
import java.util.Map;

public class TokenManager {
    private static final Map<String, String> emailMap = new HashMap<>();

    // Add email to the map
    public static void addEmail(String userId, String email) {
        emailMap.put(userId, email);
    }

    // Get email from the map
    public static String getEmail(String userId) {
        return emailMap.get(userId);
    }

    // Remove email from the map
    public static void removeEmail(String userId) {
        emailMap.remove(userId);
    }
}