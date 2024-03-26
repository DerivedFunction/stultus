package backend;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * SQL Database for our App
 */
public class Database {
  /**
   * The connection to the database. When there is no connection, it
   * should be null. Otherwise, there is a valid open connection
   */
  private Connection mConnection;

  /**
   * A prepared statement for getting all the data in the Database
   */
  private PreparedStatement mSelectAll;

  /**
   * A Prepared Statement for getting one row from the Database
   */
  private PreparedStatement mSelectOne;

  /**
   * A Prepared Statement for deleting one row from the Database
   */
  private PreparedStatement mDeleteOne;

  /**
   * A Prepared Statement for inserting one row from the Database
   */
  private PreparedStatement mInsertOne;

  /**
   * A Prepared Statement for updating one row from the Database
   */
  private PreparedStatement mUpdateOne;

  /**
   * A prepared statement for adding a like to a message
   */
  private PreparedStatement mAddLike_deprecated;

  /**
   * A prepared statement for removing a like to a message
   */
  private PreparedStatement mRemoveLike_deprecated;
  /**
   * A prepared statement for find if a user has already voted for a message
   */
  private PreparedStatement mfindVoteforUser;
  /**
   * A prepared statement for find total votes for a message
   */
  private PreparedStatement mfindTotalVotes;
  /**
   * A prepared statement for voting to a message
   */
  private PreparedStatement mVote;
  /**
   * A prepared statement for deleting votes to a message
   */
  private PreparedStatement mDeleteVote;

  /**
   * In the context of the database, RowData represents the data
   * we'd see in a row.
   *
   * We make RowData a static class of Database because it is only
   * an abstract representation of a row of the database. RowData
   * and Database are connected: Both gets updated if one changes.
   */
  public static class RowData {
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
     * Construct a RowData object by providing values for its fields
     * 
     * @param id      The ID of post
     * @param subject The subject of post
     * @param message The message of post
     * @param likes   (Deprecated method)
     */
    public RowData(int id, String subject, String message, int likes) {
      mId = id;
      mSubject = subject;
      mMessage = message;
      numLikes = likes;
    }

    @Override
    public boolean equals(Object other) {
      RowData obj = (RowData) other;
      if (!this.mSubject.equals(obj.mSubject))
        return false;
      return this.mMessage.equals(obj.mMessage);
    }
  }

  /**
   * Database constructor is private
   */
  private Database() {
  }

  /**
   * Get a fully-configured connection to the database
   * 
   * @param db_url       The url to the database
   * @param port_default port to use if absent in db_url
   * @param dbTable      ArrayList of all SQL tables to use
   * 
   * @return A Database object, or null if we cannot connect properly
   */
  static Database getDatabase(String db_url, String port_default, ArrayList<String> dbTable) {
    try {
      URI dbUri = new URI(db_url);
      String username = dbUri.getUserInfo().split(":")[0];
      String password = dbUri.getUserInfo().split(":")[1];
      String host = dbUri.getHost();
      String path = dbUri.getPath();
      String port = dbUri.getPort() == -1 ? port_default : Integer.toString(dbUri.getPort());

      return getDatabase(host, port, path, username, password, dbTable);
    } catch (URISyntaxException s) {
      System.out.println("URI Syntax Error");
      return null;
    }
  }

  /**
   * Get a fully configured connected to the database
   * 
   * @param ip    The IP address of server
   * @param port  The port on the server
   * @param path  The path
   * @param user  The user ID to use
   * @param pass  The password to use
   * @param table The ArrayList of all SQL tables
   */
  static Database getDatabase(String ip, String port, String path, String user, String pass, ArrayList<String> table) {
    if (path == null || "".equals(path)) {
      path = "/";
    }
    // Create an unconfigured Database obj
    Database db = new Database();

    // Give the Database obj a connection, or else fail
    try {
      Connection conn = DriverManager.getConnection(
          "jdbc:postgresql://" + ip + ":" + port + path, user, pass);
      if (conn == null) {
        System.err.println("Error: DriverManager.getConnection() returns null");
        return null;
      }
      db.mConnection = conn;
    } catch (SQLException e) {
      System.err.println(
          "Error: DriverManager.getConnection() threw a SQLException");
      e.printStackTrace();
      return null;
    }
    return createPreparedStatements(db, table);
  }

  /**
   * creates prepared SQL statments
   */
  private static Database createPreparedStatements(Database db, ArrayList<String> dbTable) {
    String tableName = dbTable.get(0);
    String likeTable = dbTable.get(1);
    try {
      // Standard CRUD operations
      db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM " + tableName + " WHERE id=?");
      db.mInsertOne = db.mConnection.prepareStatement(
          "INSERT INTO  " + tableName + "  VALUES (default, ?, ?,default)");
      db.mSelectAll = db.mConnection.prepareStatement(
          "SELECT id, subject, message, likes FROM  " + tableName + "  ORDER BY id DESC");
      db.mSelectOne = db.mConnection.prepareStatement("SELECT * from  " + tableName + "  WHERE id=?");
      db.mUpdateOne = db.mConnection.prepareStatement(
          "UPDATE  " + tableName + "  SET subject=?, message=? WHERE id=?");
      db.mVote = db.mConnection.prepareStatement(
          "INSERT INTO  " + likeTable + " (post_id, vote, userID) VALUES (?,?,?)");
      db.mfindVoteforUser = db.mConnection.prepareStatement(
          "SELECT COUNT(*) AS hasVoted" +
              " FROM " + likeTable +
              " WHERE post_id=? AND userID=?");
      db.mfindTotalVotes = db.mConnection.prepareStatement(
          "SELECT " + tableName + ".id, " +
              tableName + ".subject, " +
              tableName + ".message, " +
              "COALESCE(SUM(" + likeTable + ".vote), 0) AS NetVotes " +
              "FROM " + tableName + " " +
              "LEFT JOIN " + likeTable + " ON " + tableName + ".id = " + likeTable + ".post_id " +
              "WHERE " + tableName + ".id = ? " +
              "GROUP BY " + tableName + ".id, " + tableName + ".subject, " + tableName + ".message");
      db.mDeleteVote = db.mConnection.prepareStatement(
          "DELETE FROM " + likeTable +
              " WHERE post_id=? AND userID=?");
      // deprecated statements
      db.mAddLike_deprecated = db.mConnection
          .prepareStatement("UPDATE  " + tableName + "  SET likes=likes+1 WHERE id=? AND likes=0");
      db.mRemoveLike_deprecated = db.mConnection
          .prepareStatement("UPDATE  " + tableName + "  SET likes=likes-1 WHERE id=? AND likes=1");

    } catch (SQLException e) {
      System.err.println("Error creating prepared statement");
      e.printStackTrace();
      db.disconnect();
      return null;
    }
    return db;
  }

  /**
   * Close the current connection to data database, if it exist
   * 
   * @return true if connection closes as expected
   */
  boolean disconnect() {
    if (mConnection == null) {
      System.err.println("Unable to close connection: Connection was null");
      return false;
    }
    try {
      mConnection.close();
    } catch (SQLException e) {
      System.err.println("Error: Connection.close() threw a SQLException");
      e.printStackTrace();
      mConnection = null;
      return false;
    }
    mConnection = null;
    return true;
  }

  /**
   * Insert a row into the database
   *
   * @param subject The subject for this new row
   * @param message The message body for this new row
   *
   * @return The number of rows that were inserted
   */
  int insertRow(String subject, String message) {
    int count = 0;
    try {
      mInsertOne.setString(1, subject);
      mInsertOne.setString(2, message);
      count += mInsertOne.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  /**
   * Query the database for a list of all subjects and their IDs
   * 
   * @return All rows, as an ArrayList
   */
  ArrayList<RowData> selectAll() {
    ArrayList<RowData> res = new ArrayList<>();
    try {
      ResultSet rs = mSelectAll.executeQuery();
      while (rs.next()) {
        int id = rs.getInt("id");
        res.add(new RowData(id, rs.getString("subject"),
            rs.getString("message"), totalVotes(id)));
      }
      rs.close();
      return res;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Get all the data for a specific row, by ID
   *
   * @param id The id being requested
   * @return the data for the requested row, null otherwise
   */
  RowData selectOne(int id) {
    RowData res = null;
    try {
      mSelectOne.setInt(1, id);
      ResultSet rs = mSelectOne.executeQuery();
      if (rs.next()) {
        res = new RowData(rs.getInt("id"), rs.getString("subject"),
            rs.getString("message"), rs.getInt("likes"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Delete a row by ID
   * 
   * @param id The id of the row to delete
   * @return the number of rows deleted, -1 if error
   */
  int deleteRow(int id) {
    int res = -1;
    try {
      mDeleteOne.setInt(1, id);
      res = mDeleteOne.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Update the message for a row in the database
   *
   * @param id      The id of the row to update
   * @param title   The title of the message
   * @param message The new msg contents
   *
   * @return the number of rows updated, -1 on error
   */
  int updateOne(int id, String title, String message) {
    int res = -1;
    try {
      mUpdateOne.setString(1, title);
      mUpdateOne.setString(2, message);
      mUpdateOne.setInt(3, id);
      res = mUpdateOne.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Add a like to a row in the database
   * 
   * @param id The id of the row to add the like
   * @return the number of rows updated
   */
  int toggleLike(int id) {
    int res = -1;
    try {
      mAddLike_deprecated.setInt(1, id);
      res = mAddLike_deprecated.executeUpdate();
      if (res == 0) {
        mRemoveLike_deprecated.setInt(1, id);
        res = mRemoveLike_deprecated.executeUpdate();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Checks to see if user upvotes or downvotes a post.
   * If it already exists in the LikeData, then it will remove it
   * 
   * @param id     ID of the post
   * @param vote   value of the vote
   * @param userID ID of user
   * @return number of rows updated (1 on success)
   */
  int toggleVote(int id, int vote, int userID) {
    int res = -1;
    int oldVote = findVotes(id, userID);
    try {
      // If it is the same value (upvoted already and
      // upvote button clicked again)
      // Delete the vote
      if (oldVote == vote) {
        return deleteVote(id, userID);
      } else
      // If it is different value ex (already downvoted and
      // now an upvote) delete it and replace it.
      if (oldVote != 0) {
        deleteVote(id, userID);
      }
      mVote.setInt(1, id);
      mVote.setInt(2, vote);
      mVote.setInt(3, userID);
      res = mVote.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Finds if a user already voted for a post
   * 
   * @param postID post's ID to check
   * @param userID user's ID that voted
   * @return vote value (1 or -1 if voted, 0 if not)
   */
  int findVotes(int postID, int userID) {
    int res = 0; // default vote count
    try {
      mfindVoteforUser.setInt(1, postID);
      mfindVoteforUser.setInt(2, userID);
      ResultSet resultSet = mfindVoteforUser.executeQuery();
      if (resultSet.next()) {
        res = resultSet.getInt("hasVoted");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Deletes the user's vote from the likeTable
   * 
   * @param postID post's ID to delete the vote
   * @param userID user that is removing the vote
   * @return 1 on success, -1 on error
   */
  int deleteVote(int postID, int userID) {
    int res = -1;
    try {
      mDeleteVote.setInt(1, postID);
      mDeleteVote.setInt(2, userID);
      res = mDeleteVote.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Finds the net total votes of a post
   * 
   * @param postID The id of post to check
   * @return The net vote count
   */
  int totalVotes(int postID) {
    int res = 0;
    try {
      mfindTotalVotes.setInt(1, postID);
      ResultSet resultSet = mfindTotalVotes.executeQuery();
      if (resultSet.next()) {
        res = resultSet.getInt("netVotes");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }
}
