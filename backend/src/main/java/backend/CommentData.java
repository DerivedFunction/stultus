package backend;

/**
 * In the context of the database, RowData represents the data
 * we'd see in a row.
 *
 * We make RowData a static class of Database because it is only
 * an abstract representation of a row of the database. RowData
 * and Database are connected: Both gets updated if one changes.
 */
public class CommentData {
    /**
     * The ID of this row of the database
     */
    int mId;

    /**
     * The message stored in this row
     */
    String mMessage;

    /**
     * The userID on the object
     */
    int mUserID;

    /**
     * The postID on the object
     */
    int mPostID;

    /**
     * Generates a contructor
     * 
     * @param mId      The ID
     * @param mMessage The message
     * @param mUserID  The userID of author of post
     * @param mPostID  The parent of the comment
     */
    public CommentData(int mId, String mMessage, int mUserID, int mPostID) {
        this.mId = mId;
        this.mMessage = mMessage;
        this.mUserID = mUserID;
        this.mPostID = mPostID;
    }

    @Override
    public String toString() {
        return String.format("PostID: %d%nMessage: %s%nuserID: %d%nmId: %d%n",
                this.mPostID, this.mMessage,
                this.mUserID, this.mId);
    }

    @Override
    public boolean equals(Object other) {
        CommentData obj = (CommentData) other;
        if (this.mPostID != obj.mPostID)
            return false;
        if (!this.mMessage.equals(obj.mMessage))
            return false;
        if (this.mUserID != obj.mUserID)
            return false;
        return true;
    }
}