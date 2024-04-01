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
    int mId;

    /**
     * The username of the user
     */
    String mUsername;

    /**
     * The email of the user
     */
    String mEmail;

    /**
     * A user constructor.
     * 
     * @param mId       The id(userID)
     * @param mUsername The username
     * @param mEmail    The email
     */
    public UserDataLite(int mId, String mUsername, String mEmail) {
        this.mId = mId;
        this.mUsername = mUsername;
        this.mEmail = mEmail;
    }

    @Override
    public boolean equals(Object other) {
        UserDataLite obj = (UserDataLite) other;
        if (!this.mUsername.equals(obj.mUsername))
            return false;
        if (!this.mEmail.equals(obj.mEmail))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("mId: %d%nmUsername: %s%nmEmail: %s%n",
                this.mId, this.mUsername,
                this.mEmail);
    }
}