package backend;

/**
 * In the context of the database, RowData represents the data
 * we'd see in a row.
 *
 * We make RowData a static class of Database because it is only
 * an abstract representation of a row of the database. RowData
 * and Database are connected: Both gets updated if one changes.
 */
public class PostData {
    /**
     * The ID of this row of the database
     */
    int mId;

    /**
     * The subject stored in this row
     */
    String mSubject;

    /**
     * The message stored in this row
     */
    String mMessage;

    /**
     * The number of likes on the object
     */
    int numLikes;

    /**
     * The userID on the object
     */
    int mUserID;

    /**
     * Construct a RowData object by providing values for its fields
     * 
     * @param id      The ID of post
     * @param subject The subject of post
     * @param message The message of post
     * @param likes   (Deprecated method)
     */
    public PostData(int id, String subject, String message, int likes) {
        mId = id;
        mSubject = subject.trim();
        mMessage = message.trim();
        numLikes = likes;
        mUserID = 1;
    }

    /**
     * Construct a RowData object by providing values for its fields
     * 
     * @param id      The ID of post
     * @param subject The subject of post
     * @param message The message of post
     * @param likes   (Deprecated method)
     * @param userID  The author's id
     */
    public PostData(int id, String subject, String message, int likes, int userID) {
        mId = id;
        mSubject = subject.trim();
        mMessage = message.trim();
        numLikes = likes;
        mUserID = userID;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass())
            return false;
        PostData obj = (PostData) other;
        if (!this.mSubject.equals(obj.mSubject))
            return false;
        if (!this.mMessage.equals(obj.mMessage))
            return false;
        return (this.mUserID == obj.mUserID);
    }

    @Override
    public int hashCode() {
        int result = 17; // Initial value, typically a prime number
        result = 31 * result + mSubject.hashCode();
        result = 31 * result + mMessage.hashCode();
        result = 31 * result + mUserID;
        return result;
    }

    @Override
    public String toString() {
        return String.format("Subject: %s%nMessage: %s%nuserID: %d%nmId: %d%n",
                this.mSubject, this.mMessage,
                this.mUserID, this.mId);
    }
}