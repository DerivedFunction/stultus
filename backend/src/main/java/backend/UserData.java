package backend;

/**
 * In the context of the database, RowData represents the data
 * we'd see in a row.
 *
 * We make RowData a static class of Database because it is only
 * an abstract representation of a row of the database. RowData
 * and Database are connected: Both gets updated if one changes.
 */
public class UserData extends UserDataLite {
    /**
     * The gender of the user
     */
    int uGender;

    /**
     * The SO of the user
     */
    String uSO;

    /**
     * The sub value of the user
     */
    String uSub;

    /**
     * Full constructor for Google Account associated with user
     * 
     * @param uID       The userID in db
     * @param uUsername The user's username
     * @param uEmail    The user's Google account
     * @param uGender   The user's gender
     * @param uSO       The user's SO
     * @param uSub      The user's sub value
     * @param uNote     The user's note
     */
    public UserData(int uID, String uUsername, String uEmail, int uGender, String uSO, String uSub, String uNote) {
        super(uID, uUsername, uEmail, uNote);
        this.uGender = uGender;
        this.uSO = uSO.trim();
        this.uSub = uSub.trim();
        this.uNote = (uNote != null) ? uNote.trim() : null;
    }

    /**
     * A user constructor.
     * 
     * @param uID       The id(userID)
     * @param uUsername The username
     * @param uEmail    The email
     * @param uNote     The note
     */
    public UserData(int uID, String uUsername, String uEmail, String uNote) {
        super(uID, uUsername, uEmail, uNote);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass())
            return false;
        UserData obj = (UserData) other;
        if (!this.uUsername.equals(obj.uUsername))
            return false;
        if (!this.uEmail.equals(obj.uEmail))
            return false;
        if (this.uGender != obj.uGender)
            return false;
        if (!this.uSO.equals(obj.uSO))
            return false;
        return this.uSub.equals(obj.uSub);
    }

    @Override
    public int hashCode() {
        int result = 17; // Initial value, typically a prime number
        result = 31 * result + uUsername.hashCode();
        result = 31 * result + uEmail.hashCode();
        result = 31 * result + uGender;
        result = 31 * result + uSO.hashCode();
        result = 31 * result + uSub.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("mGender: %d%nmSO: %s%n", this.uGender, this.uSO);
    }
}