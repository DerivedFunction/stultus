package backend;

/**
 * In the context of the database, RowData represents the data
 * we'd see in a row.
 *
 * We make RowData a static class of Database because it is only
 * an abstract representation of a row of the database. RowData
 * and Database are connected: Both gets updated if one changes.
 */
public class UserDataLite {
    /**
     * The ID of this row of the database
     */
    int uID;

    /**
     * The username of the user
     */
    String uUsername;

    /**
     * The email of the user
     */
    String uEmail;

    /**
     * A user constructor.
     * 
     * @param uID       The id(userID)
     * @param uUsername The username
     * @param uEmail    The email
     */
    public UserDataLite(int uID, String uUsername, String uEmail) {
        this.uID = uID;
        this.uUsername = uUsername.trim();
        this.uEmail = uEmail.trim();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        UserDataLite obj = (UserDataLite) other;
        if (!this.uUsername.equals(obj.uUsername))
            return false;
        if (!this.uEmail.equals(obj.uEmail))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("mId: %d%nmUsername: %s%nmEmail: %s%n",
                this.uID, this.uUsername,
                this.uEmail);
    }
}