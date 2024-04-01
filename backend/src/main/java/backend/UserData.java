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
    int mGender;

    /**
     * The SO of the user
     */
    String mSO;

    /**
     * A user constructor.
     * 
     * @param mId       The id(userID)
     * @param mUsername The username
     * @param mEmail    The email
     */
    public UserData(int mId, String mUsername, String mEmail) {
        super(mId, mUsername, mEmail);
    }

    /**
     * A user constructor
     * 
     * @param mId       The id(userID)
     * @param mUsername The username
     * @param mEmail    The email
     * @param mGender   The gender
     * @param mSO       The SO
     */
    public UserData(int mId, String mUsername, String mEmail, int mGender, String mSO) {
        super(mId, mUsername, mEmail);
        this.mGender = mGender;
        this.mSO = mSO;
    }

    @Override
    public boolean equals(Object other) {
        UserData obj = (UserData) other;
        if (!this.mUsername.equals(obj.mUsername))
            return false;
        if (!this.mEmail.equals(obj.mEmail))
            return false;
        if (this.mGender != obj.mGender)
            return false;
        if (!this.mSO.equals(obj.mSO))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("mGender: %d%nmSO: %s%n", this.mGender, this.mSO);
    }
}