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
     * A user constructor.
     * 
     * @param cID       The id(userID)
     * @param uUsername The username
     * @param uEmail    The email
     */
    public UserData(int cID, String uUsername, String uEmail) {
        super(cID, uUsername, uEmail);
    }

    /**
     * A user constructor
     * 
     * @param uID       The id(userID)
     * @param uUsername The username
     * @param uEmail    The email
     * @param uGender   The gender
     * @param uSO       The SO
     */
    public UserData(int uID, String uUsername, String uEmail, int uGender, String uSO) {
        super(uID, uUsername, uEmail);
        this.uGender = uGender;
        this.uSO = uSO;
    }

    @Override
    public boolean equals(Object other) {
        UserData obj = (UserData) other;
        if (!this.uUsername.equals(obj.uUsername))
            return false;
        if (!this.uEmail.equals(obj.uEmail))
            return false;
        if (this.uGender != obj.uGender)
            return false;
        if (!this.uSO.equals(obj.uSO))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("mGender: %d%nmSO: %s%n", this.uGender, this.uSO.trim());
    }
}