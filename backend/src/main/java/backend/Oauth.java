package backend;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

/**
 * The Oauth 2.0 implementation from sample code
 */
public class Oauth {

    /**
     * The app's client ID
     */
    private static final String CLIENT_ID = System.getenv("CLIENT_ID");
    /**
     * The HttpTransport for Oauth
     */
    private static final NetHttpTransport transport = new NetHttpTransport();
    /**
     * The JSON factory for Oauth
     */
    private static final JsonFactory jsonFactory = new GsonFactory();

    /**
     * Gets the payload's email
     * 
     * @param idTokenString The token to get the payload
     * @return email as a string
     */
    public static String getEmail(String idTokenString) {
        Payload payload = getPayload(idTokenString);
        return payload != null ? payload.getEmail() : null;
    }

    /**
     * Gets the payload's name
     * 
     * @param idTokenString The token to get the payload
     * @return name as a string
     */
    public static String getName(String idTokenString) {
        Payload payload = getPayload(idTokenString);
        return payload != null ? payload.get("name").toString() : null;
    }

    /**
     * Verifies if the token is valid
     * 
     * @param idTokenString The token to verify
     * @return True if valid
     */
    public static Boolean verifyToken(String idTokenString) {
        return getPayload(idTokenString) != null;
    }

    /**
     * Gets the sub of the token
     * 
     * @param idTokenString The token to verify
     * @return unique value
     */
    public static String getSub(String idTokenString) {
        Payload payload = getPayload(idTokenString);
        return payload != null ? payload.get("sub").toString() : null;
    }

    /**
     * Gets the payload of the token
     * 
     * @param idTokenString The token
     * @return The response
     */
    private static Payload getPayload(String idTokenString) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                return idToken.getPayload();
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
