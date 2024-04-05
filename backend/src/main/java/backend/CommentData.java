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
    int cId;

    /**
     * The message stored in this row
     */
    String cMessage;

    /**
     * The userID on the object
     */
    int cUserID;

    /**
     * The postID on the object
     */
    int cPostID;

    /**
     * Generates a contructor
     * 
     * @param cId      The ID
     * @param cMessage The message
     * @param cUserID  The userID of author of post
     * @param cPostID  The parent of the comment
     */
    public CommentData(int cId, String cMessage, int cPostID, int cUserID) {
        this.cId = cId;
        this.cMessage = cMessage.trim();
        this.cUserID = cUserID;
        this.cPostID = cPostID;
    }

    @Override
    public String toString() {
        return String.format("PostID: %d%nMessage: %s%nuserID: %d%nmId: %d%n",
                this.cPostID, this.cMessage,
                this.cUserID, this.cId);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass())
            return false;
        CommentData obj = (CommentData) other;
        if (this.cPostID != obj.cPostID)
            return false;
        if (!this.cMessage.equals(obj.cMessage))
            return false;
        if (this.cUserID != obj.cUserID)
            return false;
        return true;
    }
}