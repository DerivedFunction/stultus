package backend;

/**
 * The Sesion class manages user tokens and their associated user IDs.
 */
public class Session {

    /**
     * The ip address of session
     */
    String ip;
    /**
     * The user agent of session
     */
    String userAgent;
    /**
     * The userID of session
     */
    int userID;

    /**
     * Create a new session
     * 
     * @param ip        the ip address
     * @param userAgent the useragent
     * @param userID    the userid
     */
    public Session(String ip, String userAgent, int userID) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ip == null) ? 0 : ip.hashCode());
        result = prime * result + ((userAgent == null) ? 0 : userAgent.hashCode());
        result = prime * result + userID;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Session other = (Session) obj;
        if (ip == null) {
            if (other.ip != null)
                return false;
        } else if (!ip.equals(other.ip))
            return false;
        if (userAgent == null) {
            if (other.userAgent != null)
                return false;
        } else if (!userAgent.equals(other.userAgent))
            return false;
        if (userID != other.userID)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Session [ip=" + ip + ", userAgent=" + userAgent + ", userID=" + userID + "]";
    }

}