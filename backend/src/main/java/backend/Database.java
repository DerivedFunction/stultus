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
   * A prepared statement for adding a user
   */
  private PreparedStatement mAddUser;
  /**
   * A prepared statement to find the userID based on email
   */
  private PreparedStatement mFindUser;
  /**
   * A prepared statement to get simple information of a user
   */
  private PreparedStatement mGetUserSimple;
  /**
   * A prepared statement to get the full information of a user
   */
  private PreparedStatement mGetUserFull;

  /**
   * A prepared statement to get update user information
   */
  private PreparedStatement mUpdateUser;

  /**
   * A prepared statement to get delete a user
   */
  private PreparedStatement mDeleteUser;

  /**
   * A prepared statement to select all comments
   */
  private PreparedStatement mSelectAllCommentsForPost;
  /**
   * A prepared statement to select all comments
   */
  private PreparedStatement mSelectAllCommentsByUser;
  /**
   * A prepared statement to select all comments
   */
  private PreparedStatement mSelectAllCommentsByUserAndPost;
  /**
   * A prepared statement to select one comment
   */
  private PreparedStatement mSelectOneComment;
  /**
   * A prepared statement to delete a comment
   */
  private PreparedStatement mDeleteComment;
  /**
   * A prepared statement to edit a comment
   */
  private PreparedStatement mUpdateComment;
  /**
   * A prepared statement to add a comment
   */
  private PreparedStatement mInsertComment;

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
      Log.error("URI Syntax Error");
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
   * @return Connected Database
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
        Log.error("Error: DriverManager.getConnection() returns null");
        return null;
      }
      db.mConnection = conn;
    } catch (SQLException e) {
      Log.error(
          "Error: DriverManager.getConnection() threw a SQLException");
      e.printStackTrace();
      return null;
    }
    return createPreparedStatements(db, table);
  }

  /**
   * creates prepared SQL statments
   * 
   * @param db      The connected database
   * @param dbTable The list of db tables we want to connect
   * @return The database with SQL statements
   */
  private static Database createPreparedStatements(Database db, ArrayList<String> dbTable) {
    String tableName = dbTable.get(0);
    String likeTable = dbTable.get(1);
    String userTable = dbTable.get(2);
    String commentTable = dbTable.get(3);
    try {
      // Standard CRUD operations
      db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM " + tableName + " WHERE id=?");
      db.mInsertOne = db.mConnection.prepareStatement(
          "INSERT INTO  " + tableName + "  VALUES (default, ?, ?,default,?)");
      db.mSelectAll = db.mConnection.prepareStatement(
          "SELECT * FROM  " + tableName + "  ORDER BY id DESC");
      db.mSelectOne = db.mConnection.prepareStatement("SELECT * from  " + tableName + "  WHERE id=?");
      db.mUpdateOne = db.mConnection.prepareStatement(
          "UPDATE  " + tableName + "  SET subject=?, message=? WHERE id=?");
      db.mVote = db.mConnection.prepareStatement(
          "INSERT INTO  " + likeTable + " (post_id, vote, userID) VALUES (?,?,?)");
      db.mfindVoteforUser = db.mConnection.prepareStatement(
          "SELECT vote FROM " +
              likeTable +
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
      db.mAddUser = db.mConnection.prepareStatement(
          "INSERT INTO " + userTable +
              " (username,email) VALUES" +
              " (?,?)");
      db.mFindUser = db.mConnection.prepareStatement(
          "SELECT id FROM " + userTable + " WHERE email=?");
      db.mGetUserSimple = db.mConnection.prepareStatement(
          "SELECT id, username, email FROM " + userTable + " WHERE id=?");
      db.mGetUserFull = db.mConnection.prepareStatement(
          "SELECT * FROM " + userTable + " WHERE id=?");
      db.mUpdateUser = db.mConnection.prepareStatement(
          "UPDATE " + userTable + " SET username=?, gender=?, so=? where id=?");
      db.mDeleteUser = db.mConnection.prepareStatement(
          "DELETE FROM " + userTable + " WHERE id=?");
      db.mSelectAllCommentsForPost = db.mConnection.prepareStatement(
          "SELECT * FROM " + commentTable + " WHERE post_id=? ORDER BY ID DESC");
      db.mSelectAllCommentsByUser = db.mConnection.prepareStatement(
          "SELECT * FROM " + commentTable + " WHERE userid=? ORDER BY ID DESC");
      db.mSelectOneComment = db.mConnection.prepareStatement(
          "SELECT * FROM " + commentTable + " WHERE id=?");
      db.mDeleteComment = db.mConnection.prepareStatement(
          "DELETE FROM " + commentTable + " WHERE id=? AND userid=?");
      db.mUpdateComment = db.mConnection.prepareStatement(
          "UPDATE " + commentTable + " SET message=? WHERE id=? AND userid=?");
      db.mInsertComment = db.mConnection.prepareStatement(
          "INSERT INTO " + commentTable + " (mesage, post_id, userid) VALUES (?,?,?)");
      // deprecated statements
      db.mAddLike_deprecated = db.mConnection
          .prepareStatement("UPDATE  " + tableName + " SET likes=likes+1 WHERE id=? AND likes=0");
      db.mRemoveLike_deprecated = db.mConnection
          .prepareStatement("UPDATE  " + tableName + " SET likes=likes-1 WHERE id=? AND likes=1");

    } catch (SQLException e) {
      Log.error("Error creating prepared statement");
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
      Log.error("Unable to close connection: Connection was null");
      return false;
    }
    try {
      mConnection.close();
    } catch (SQLException e) {
      Log.error("Error: Connection.close() threw a SQLException");
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
   * @param userid  The userID of author
   * @return The number of rows that were inserted
   */
  int insertRow(String subject, String message, int userid) {
    int count = 0;
    try {
      mInsertOne.setString(1, subject);
      mInsertOne.setString(2, message);
      mInsertOne.setInt(3, userid);
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
  ArrayList<PostData> selectAll() {
    ArrayList<PostData> res = new ArrayList<>();
    try {
      ResultSet rs = mSelectAll.executeQuery();
      while (rs.next()) {
        int id = rs.getInt("id");
        res.add(new PostData(id, rs.getString("subject"),
            rs.getString("message"), totalVotes(id) + rs.getInt("likes"), rs.getInt("userid")));
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
  PostData selectOne(int id) {
    PostData res = null;
    try {
      mSelectOne.setInt(1, id);
      ResultSet rs = mSelectOne.executeQuery();
      if (rs.next()) {
        int postID = rs.getInt("id");
        res = new PostData(postID, rs.getString("subject"),
            rs.getString("message"), totalVotes(postID) + rs.getInt("likes"), rs.getInt("userid"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Delete a row by ID
   * 
   * @param id     The id of the row to delete
   * @param userID The id of user wanting to delete it
   * @return the number of rows deleted, -1 if error
   */
  int deleteRow(int id, int userID) {
    int res = -1;
    PostData data = selectOne(id);
    // Wrong user cannot update post
    if (data.mUserID != userID)
      return res;
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
   * @param userID  The id of user wanting to update it
   * @return the number of rows updated, -1 on error
   */
  int updateOne(int id, String title, String message, int userID) {
    int res = -1;
    PostData data = selectOne(id);
    // Wrong user cannot update post
    if (data.mUserID != userID)
      return res;
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
   * @deprecated As of sprint 8, this feature has been deprecated in favor of
   *             {@link #toggleVote(int, int, int)}
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
      ResultSet rs = mfindVoteforUser.executeQuery();
      if (rs.next()) {
        res = rs.getInt("vote");
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
      ResultSet rs = mfindTotalVotes.executeQuery();
      if (rs.next()) {
        res = rs.getInt("netVotes");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * find the userid based on email
   * 
   * @param email to find
   * @return id of user, 0 on nothing
   */
  int findUserID(String email) {
    int res = 0;
    try {
      mFindUser.setString(1, email);
      ResultSet rs = mFindUser.executeQuery();
      // Get the count
      if (rs.next()) {
        res = rs.getInt("id");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Gets simple user information from id
   * 
   * @param userID id of user
   * @return UserData of id, username, and email
   */
  UserDataLite getUserSimple(int userID) {
    UserDataLite res = null;
    try {
      mGetUserSimple.setInt(1, userID);
      ResultSet rs = mGetUserSimple.executeQuery();
      if (rs.next()) {
        res = new UserDataLite(rs.getInt("id"), rs.getString("username"), rs.getString("email"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Gets full user information from email
   * 
   * @param userID Gets email
   * @return UserData of id, username, email, gender, and SO)
   */
  UserData getUserFull(int userID) {
    UserData res = null;
    try {
      mGetUserFull.setInt(1, userID);
      ResultSet rs = mGetUserFull.executeQuery();
      if (rs.next()) {
        res = new UserData(rs.getInt("id"), rs.getString("username"),
            rs.getString("email"), rs.getInt("gender"),
            rs.getString("so"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Adds a new user
   * 
   * @param username The username to add
   * @param email    The email to add
   * @return 1 on success, 0 if fail or already exists
   */
  int insertUser(String username, String email) {
    int count = 0;
    // User already existrs
    if (findUserID(email) > 0) {
      return 0;
    }
    try {
      mAddUser.setString(1, username);
      mAddUser.setString(2, email);
      count += mAddUser.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  /**
   * Updates user profile
   * 
   * @param userID   The userID associated with user
   * @param username The new username
   * @param gender   The new gender
   * @param SO       The new SO
   * @return 1 on sucess, 0 on fail
   */
  int updateUser(int userID, String username, int gender, String SO) {
    int count = 0;

    try {
      mUpdateUser.setString(1, username);
      mUpdateUser.setInt(2, gender);
      mUpdateUser.setString(3, SO);
      mUpdateUser.setInt(4, userID);
      count += mUpdateUser.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  /**
   * Deletes a user from database
   * 
   * @param userID to delete
   * @return 1 on success, 0 on failure
   */
  int deleteUser(int userID) {
    int count = 0;
    try {
      mDeleteUser.setInt(1, userID);
      count += mDeleteUser.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  /**
   * Inserts a comment to a post
   * 
   * @param message Comment message
   * @param postID  which post is commented
   * @param userID  author of comment
   * @return
   */
  int insertComment(String message, int postID, int userID) {
    int count = 0;
    try {
      mInsertComment.setString(1, message);
      mInsertComment.setInt(2, postID);
      mInsertComment.setInt(3, userID);
      count += mInsertComment.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  /**
   * Updates a comment for user
   * 
   * @param message   Comment message
   * @param commentID Which comment to update
   * @param userID    Author of edited comment
   * @return
   */
  int updateComment(String message, int commentID, int userID) {
    int count = 0;
    try {
      mUpdateComment.setString(1, message);
      mUpdateComment.setInt(2, commentID);
      mUpdateComment.setInt(3, userID);
      count += mUpdateComment.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  /**
   * Deletes a comment
   * 
   * @param commentID id of comment
   * @param userID    id of user
   * @return
   */
  int deleteComment(int commentID, int userID) {
    int count = 0;
    try {
      mDeleteComment.setInt(1, commentID);
      mDeleteComment.setInt(2, userID);
      count += mDeleteComment.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  /**
   * Select a single comment
   * 
   * @param commentID comment's ID to select
   * @return CommentData
   */
  CommentData selectComment(int commentID) {
    CommentData res = null;
    try {
      mSelectOneComment.setInt(1, commentID);
      ResultSet rs = mSelectOneComment.executeQuery();
      if (rs.next()) {
        res = new CommentData(rs.getInt("id"), rs.getString("message"),
            rs.getInt("post_id"), rs.getInt("userid"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Selects all comments for specific post
   * 
   * @param postID id of post
   * @return ArrayList of CommentData
   */
  ArrayList<CommentData> selectAllCommentByPost(int postID) {
    ArrayList<CommentData> res = new ArrayList<>();
    try {
      mSelectAllCommentsForPost.setInt(1, postID);
      ResultSet rs = mSelectAllCommentsForPost.executeQuery();
      while (rs.next()) {
        res.add(new CommentData(rs.getInt("id"), rs.getString("message"),
            rs.getInt("post_id"), rs.getInt("userid")));
      }
      rs.close();
      return res;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
