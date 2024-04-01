package backend;

/**
 * In the context of the database, RowData represents the data
 * we'd see in a row.
 *
 * We make RowData a static class of Database because it is only
 * an abstract representation of a row of the database. RowData
 * and Database are connected: Both gets updated if one changes.
 */
public class UserData {
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
        this.mId = mId;
        this.mUsername = mUsername;
        this.mEmail = mEmail;
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
        this.mId = mId;
        this.mUsername = mUsername;
        this.mEmail = mEmail;
        this.mGender = mGender;
        this.mSO = mSO;
    }

}