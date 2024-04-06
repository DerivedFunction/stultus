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
     * A note of the user
     */
    String uNote;

    /**
     * A user constructor.
     * 
     * @param uID       The id(userID)
     * @param uUsername The username
     * @param uEmail    The email
     * @param uNote     The note
     */
    public UserDataLite(int uID, String uUsername, String uEmail, String uNote) {
        this.uID = uID;
        this.uUsername = uUsername.trim();
        this.uEmail = uEmail.trim();
        this.uNote = (uNote != null) ? uNote.trim() : null;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass())
            return false;
        UserDataLite obj = (UserDataLite) other;
        if (!this.uUsername.equals(obj.uUsername))
            return false;
        return this.uEmail.equals(obj.uEmail);
    }

    @Override
    public int hashCode() {
        int result = 17; // Initial value, typically a prime number
        result = 31 * result + uUsername.hashCode();
        result = 31 * result + uEmail.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("mId: %d%nmUsername: %s%nmEmail: %s%nNote: %s%n",
                this.uID, this.uUsername, this.uEmail, this.uNote);
    }
}