package admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {

  /* MESSAGE DATA *///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * The connection to the database. When there is no connection, it
   * should be null. Otherwise, there is a valid open connection
   */
  private Connection mConnection;

  /**
   * A prepared statement for getting all the messages in the table
   */
  private PreparedStatement mSelectAll;

  /**
   * A Prepared Statement for getting one message from the Database
   */
  private PreparedStatement mSelectOne;

  /**
   * A Prepared Statement for deleting one message from the Database
   */
  private PreparedStatement mDeleteOne;

  /**
   * A Prepared Statement for inserting one message from the Database
   */
  private PreparedStatement mInsertOne;

  /**
   * A Prepared Statement for updating one message from the Database
   */
  private PreparedStatement mUpdateOne;

  /**
   * A Prepared Statement for creating the table of messages
   */
  private PreparedStatement mCreateTable;

  /**
   * A Prepared Statement for deleting the message table from the Database
   */
  private PreparedStatement mDropTable;

  /* USER STATEMENTS *///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * A prepared statement for getting all the Users in the Database
   */
  private PreparedStatement uSelectAll;

  /**
   * A Prepared Statement for deleting one User from the Database
   */
  private PreparedStatement uDeleteOne;

  /**
   * A Prepared Statement for updating one User from the Database
   */
  private PreparedStatement uUpdateOne;
  /**
   * A Prepared Statement for selecting one User from the Database using User ID
   */
  private PreparedStatement uSelectUser;
  /**
   * A Prepared Statement for selecting one User from the Database using Email account
   */
  private PreparedStatement uSelectUserByEmail;

  /**
   * A Prepared Statement for creating the table of Users from the Database
   */
  private PreparedStatement uCreateTable;

  /**
   * A Prepared Statement for deleting the table of Users from the Database
   */
  private PreparedStatement uDropTable;

  /**
   * A Prepared Statement for deleting the table of Users from the Database
   */
  private PreparedStatement uInsertOne;

  /*COMMENT STATEMENTS *///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * A prepared statement for creating the comment table in the Database
   */
  private PreparedStatement cCreateTable;
  /**
   * A Prepared Statement for deleting the comment table from the Database
   */
  private PreparedStatement cDropTable;
  /**
   * A Prepared Statement for selecting all the comments on a single post
   */
  private PreparedStatement cSelectAllCommentsForAPost;
  /**
   * A Prepared Statement for selecting all the comments from a single user
   */
  private PreparedStatement cSelectAllCommentsForAUser;
  /**
   * A Prepared Statement for selecting all the comments from a single user on a single post
   */
  private PreparedStatement cSelectAllCommentsForAUserANDPost;
  /**
   * A Prepared Statement for selecting all the comments by an ID
   */
  private PreparedStatement cSelectComment;
  /**
   * A Prepared Statement for deleting a comment from the Database
   */
  private PreparedStatement cDeleteComment;
  /**
   * A Prepared Statement for updating a comment from the Database
   */
  private PreparedStatement cUpdateComment;
  /**
   * A Prepared Statement for adding a new comment to the table from the Database
   */
  private PreparedStatement cInsertComment;


  /*VOTING STATEMENTS *///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * A prepared statement for creating the voting table
   */
  private PreparedStatement lCreateTable;
  /**
   * A Prepared Statement for deleting the voting table from the Database
   */
  private PreparedStatement lDropTable;
  /**
   * A Prepared Finding How A User Voted on a post
   */
  private PreparedStatement lSelectVoteFromUser;
  /**
   * A Prepared Statement for deleting the vote from the database
   */
  private PreparedStatement lDeleteVote;
  /**
   * A Prepared Statement for seeing how everyone voted on all posts
   */
  private PreparedStatement lSelectAllVotes;

  

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
     * User Idea
     */
    int uID;

    /**
     * Mesage Status
     */
    int status;

    /**
     * Method that constructs a RowData object by providing values for its fields
     * 
     * @param id      the id of the message
     * @param subject the user-inputted subject/title of their message
     * @param message the user-inputted message/content of their post
     * @param uid the number of likes a message has
     */
    public RowData(int id, String subject, String message, int uid, int stat) {
      mId = id;
      mSubject = subject;
      mMessage = message;
      uID = uid;
      status = stat;
    }

    /**
     * Method that checks if the subject or message of one post is identical to
     * another post
     * 
     * @param other the other object(post) that is being compared to the post being
     *              created
     * @return boolean determining whether the two objects are equal or not
     */
    @Override
    public boolean equals(Object other) {
      RowData obj = (RowData) other;
      if (!this.mSubject.equals(obj.mSubject))
        return false;
      if (!this.mMessage.equals(obj.mMessage))
        return false;
      return true;
    }
  }

  public static class UserRowData {
    /**
     * The ID of this row of the database
     */
    int uId;

    /**
     * Name for the user (username if you will)
     */
    String uName;

    /**
     * The email address stored in this row
     */
    String uEmail;

    /**
     * The Gender Identity stored in this row
     */
    int uGender;

    /**
     * The Sexual Orientation stored in this row
     */
    String uSO;
    /**
     * The Google OAuth Code
     */
    String sub;
    /**
     * The User's Bio
     */
    String note;
    /**
     * The User's Bio
     */
    int status;

    /**
     * Method that constructs a RowData object by providing values for its fields
     * 
     * @param id      the id of the user
     * @param name the username
     * @param email the user's email
     * @param gender   the users gender
     * @param SO the users gender orientation
     */
    public UserRowData(int id, String name, String email, int gender, String SO, String su, String not, int stat) {
      uId = id;
      uName = name;
      uEmail = email;
      uGender = gender;
      uSO = SO;
      sub = su;
      note = not; 
      status = stat;
    }

    /**
     * Method that checks if the username or email of one post is identical to another
     * 
     * @param other the other object(post) that is being compared to the post being created
     * @return boolean determining whether the two objects are equal or not
     */
    @Override
    public boolean equals(Object other) {
      UserRowData obj = (UserRowData) other;
      if (!this.uName.equals(obj.uName))
        return false;
      if (!this.uEmail.equals(obj.uEmail))
        return false;
      if (this.uGender != obj.uGender)
        return false;
      if (!this.uSO.equals(obj.uSO))
        return false;
      return true;
    }
  }

  public static class LikeRowData {
    /**
     * The ID of this vote of the database
     */
    int lId;

    /**
     * Message id for the liked post
     */
    int mId;

    /**
     * The user id of the liker stored in this row
     */
    int uId;

    /**
     * Like/Dislike Sign stored in this row
     */
    int lVote;

    /**
     * Method that constructs a RowData object by providing values for its fields
     * 
     * @param lid      the id of the vote
     * @param mid      the id of the message being voted on
     * @param uid      the id of the voter
     * @param lvote    the valuue of the vote (up or down) 
     */
    public LikeRowData(int lid, int mid, int uid, int lvote) {
      lId = lid;
      mId = mid;
      uId = uid;
      lVote = lvote;
    }
    /**
     * Method that checks if the username or id of one post is identical to another
     * post
     * 
     * @Override
     *           public boolean equals(Object other) {
     *           LikeRowData obj = (LikeRowData)other;
     *           if (this.mId != obj.mId)
     *           return false;
     *           if (this.uId != obj.uId)
     *           return false;
     *           return true;
     *           }
     */
  }

  public static class CommentRowData {
    /**
     * The ID of this comment of the database
     */
    int cId;

    /**
     * Comment's string value displayed
     */
    String comment;

    /**
     * The user id of the commenter stored in this row
     */
    int mId;

    /**
     * The user ID of the message sender
     */
    int uId;

    /**
     * Method that constructs a RowData object by providing values for its fields
     * 
     * @param cid      the id of the comment
     * @param com      the value of the comment
     * @param mid      the id of the message being commented on
     * @param uid      the id of the user commenting
     */
    public CommentRowData(int cid, String com, int mid, int uid) {
      cId = cid;
      comment = com;
      mId = mid;
      uId = uid;
    }

    /**
     * Method that checks if the message id, user id, and comment of one post is identical to another
     * post
     * 
     * @param other the other object(post) that is being compared to the post being created
     * @return boolean determining whether the two objects are equal or not
     */
    @Override
    public boolean equals(Object other) {
      CommentRowData obj = (CommentRowData) other;
      if (this.mId != obj.mId)
        return false;
      if (!this.comment.equals(obj.comment))
        return false;
      if (this.uId != obj.uId)
        return false;
      return true;
    }
  }

  // Database constructor is private
  private Database() {
  }

  /**
   * Method that gets a fully configured connection to the database
   * 
   * @param ip   The IP address of server
   * @param port The port on the server
   * @param user The user ID to use
   * @param pass The password to use
   * @return the db object created in which we are connecting
   */

  static Database getDatabase(String ip, String port, String user,
      String pass) {
    // Create an unconfigured Database obj
    Database db = new Database();

    // Give the Database obj a connection, or else fail
    try {
      Connection conn = DriverManager.getConnection(
          "jdbc:postgresql://" + ip + ":" + port + "/", user, pass);
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
    try {
      //Create Post Table
      db.mCreateTable = db.mConnection.prepareStatement("CREATE TABLE tblData ("
                                                        + "id SERIAL PRIMARY KEY,"
                                                        + "subject VARCHAR(100) NOT NULL,"
                                                        + "message VARCHAR(1024) NOT NULL,"
                                                        + "userid INT,"
                                                        + "status INT DEFAULT 1,"
                                                        + "FOREIGN KEY (userid) REFERENCES userData(id) ON DELETE CASCADE)");
      //Create User Table
      db.uCreateTable = db.mConnection.prepareStatement("CREATE TABLE userData ("
                                                        + "id SERIAL PRIMARY KEY,"
                                                        + "username VARCHAR(50) NOT NULL,"
                                                        + "email VARCHAR(50) NOT NULL UNIQUE,"
                                                        + "gender INT DEFAULT 0,"
                                                        + "so VARCHAR(10) DEFAULT 'private',"
                                                        + "sub CHAR(255) NOT NULL UNIQUE,"
                                                        + "note CHAR(2048) NOT NULL,"
                                                        + "status INT DEFAULT 1)");
      //Create Comment Table
      db.cCreateTable = db.mConnection.prepareStatement("CREATE TABLE commentData  ("
                                                        + "id SERIAL PRIMARY KEY,"
                                                        + "comMessage VARCHAR(2048) NOT NULL,"
                                                        + "post_id INT,"
                                                        + "userid INT,"
                                                        + "status INT DEFAULT 1,"
                                                        + "FOREIGN KEY (post_id) REFERENCES tblData(id) ON DELETE CASCADE,"
                                                        + "FOREIGN KEY (userid) REFERENCES userData(id) ON DELETE CASCADE)");
      //Create Like Table
      db.lCreateTable = db.mConnection.prepareStatement("CREATE TABLE likeData ("
                                                        + "id SERIAL PRIMARY KEY,"
                                                        + "post_id INT NOT NULL,"
                                                        + "vote INT NOT NULL,"
                                                        + "userid INT NOT NULL,"
                                                        + "FOREIGN KEY (post_id) REFERENCES tblData(id) ON DELETE CASCADE,"
                                                        + "FOREIGN KEY (userid) REFERENCES userData(id) ON DELETE CASCADE)");

      //Drop All Tables
      db.mDropTable = db.mConnection.prepareStatement("DROP TABLE tblData");
      db.uDropTable = db.mConnection.prepareStatement("DROP TABLE userData");
      db.cDropTable = db.mConnection.prepareStatement("DROP TABLE commentData ");
      db.lDropTable = db.mConnection.prepareStatement("DROP TABLE likeData");

      // Standard CRUD Messages operations
      db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblData WHERE id=?");
      db.mInsertOne = db.mConnection.prepareStatement(
          "INSERT INTO tblData VALUES (default, ?, ?, ?, default)");
      db.mSelectAll = db.mConnection.prepareStatement(
          "SELECT * FROM tblData");
      db.mSelectOne = db.mConnection.prepareStatement("SELECT * from tblData WHERE id=?");
      db.mUpdateOne = db.mConnection.prepareStatement(
          "UPDATE tblData SET message=? WHERE id=?");

      // Standard CRUD User operations
      db.uDeleteOne = db.mConnection.prepareStatement("DELETE FROM userData WHERE id=?");
      db.uInsertOne = db.mConnection.prepareStatement("INSERT INTO userData (username,email,note) VALUES (?,?,?)");
      db.uSelectUserByEmail = db.mConnection.prepareStatement("SELECT * FROM userData WHERE email=?");
      db.uSelectUser = db.mConnection.prepareStatement("SELECT * FROM userData WHERE id=?");
      db.uUpdateOne = db.mConnection.prepareStatement("UPDATE userData SET username=?, gender=?, so=?, note=? where id=?");
      db.uSelectAll = db.mConnection.prepareStatement("SELECT * FROM userData");


      // Standard CRUD Voting operations
      db.lSelectVoteFromUser = db.mConnection.prepareStatement("SELECT vote FROM likeData WHERE post_id=? AND userid=?");
      db.lSelectAllVotes = db.mConnection.prepareStatement("SELECT * FROM likeData");
      db.lDeleteVote = db.mConnection.prepareStatement("DELETE FROM likeData WHERE post_id=? AND userid=?");
      
      // Standard CRUD Comment operations
      db.cSelectAllCommentsForAPost = db.mConnection.prepareStatement("SELECT * FROM commentData  WHERE post_id=? ORDER BY ID DESC");
      db.cSelectAllCommentsForAUser = db.mConnection.prepareStatement("SELECT * FROM commentData  WHERE userid=? ORDER BY ID DESC");
      db.cSelectAllCommentsForAUserANDPost = db.mConnection.prepareStatement("SELECT * FROM commentData  WHERE post_id=? AND userid=? ORDER BY ID DESC");
      db.cSelectComment = db.mConnection.prepareStatement("SELECT * commentData  WHERE id=?");
      db.cDeleteComment = db.mConnection.prepareStatement("DELETE commentData  WHERE id=? and userid=?");
      db.cUpdateComment = db.mConnection.prepareStatement("UPDATE commentData  SET comMessage=? WHERE id=? AND userid=?");
      db.cInsertComment = db.mConnection.prepareStatement("INSERT INTO commentData (comMessage, post_id, userid) VALUES (?,?,?)");


    } catch (SQLException e) {
      System.err.println("Error creating prepared statement");
      e.printStackTrace();
      db.disconnect();
      return null;
    }
    return db;
  }

  /**
   * Method that closes the current connection to data database, if it exists
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
      System.err.println("Error: Connection.close() thre a SQLException");
      e.printStackTrace();
      mConnection = null;
      return false;
    }
    mConnection = null;
    return true;
  }


  //MESSAGE METHODS/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Method that inserts a row into the database. These rows are the user-inputted
   * subject+message
   *
   * @param subject The subject for this new row
   * @param message The message body for this new row
   * @param user user who will post
   * @return The number of rows that were inserted
   */
  int insertRow(String subject, String message, int user) {
    int count = 0;
    try {
      mInsertOne.setString(1, subject);
      mInsertOne.setString(2, message);
      mInsertOne.setInt(3, user);
      count += mInsertOne.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  /**
   * Method that queries the database for a list of all subjects and their IDs
   * 
   * @return All rows, as an ArrayList
   */
  ArrayList<RowData> selectAll() {
    ArrayList<RowData> res = new ArrayList<>();
    try {
      ResultSet rs = mSelectAll.executeQuery();
      while (rs.next()) {
        res.add(new RowData(rs.getInt("id"), rs.getString("subject"),
            rs.getString("message"), rs.getInt("userid"), rs.getInt("status")));
      }
      rs.close();
      return res;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Method that gets all the data for a specific row, by ID
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
            rs.getString("message"), rs.getInt("userid"), rs.getInt("status"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Method that deletes a row by ID
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
   * Method that updates the message for a row in the database
   *
   * @param id      The id of the row to update
   * @param message The new message contents
   * @return the number of rows udpated, -1 on error
   */
  int updateOne(int id, String message) {
    int res = -1;
    try {
      mUpdateOne.setString(1, message);
      mUpdateOne.setInt(2, id);
      res = mUpdateOne.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Method to create tblData. If it already exists, print error
   */
  void createTable() {
    try {
      mCreateTable.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to remove tblData from the database, print error if DNE
   */
  void dropTable() {
    try {
      mDropTable.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //USER METHODS/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Method that queries the database for a list of all Users and their attributes
   * 
   * @return All rows, as an ArrayList
   */
  ArrayList<UserRowData> selectAllUsers() {
    ArrayList<UserRowData> res = new ArrayList<>();
    try {
      ResultSet rs = uSelectAll.executeQuery();
      while (rs.next()) {
        res.add(new UserRowData(rs.getInt("id"), rs.getString("username"),
              rs.getString("email"), rs.getInt("gender"), rs.getString("so"),
              rs.getString("sub"), rs.getString("note"), rs.getInt("status")));
      }
      rs.close();
      return res;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
   
   /**
   * Method that gets all the data for a specific uer, by ID
   *
   * @param id The id being requested
   * @return the data for the requested row, null otherwise
   */
  UserRowData SelectUser(int id) {
    UserRowData res = null;
    try {
      uSelectUser.setInt(1, id);
      ResultSet rs = uSelectUser.executeQuery();
      if (rs.next()) {
        res = new UserRowData(rs.getInt("id"), rs.getString("username"),
            rs.getString("email"), rs.getInt("gender"), rs.getString("so"),
            rs.getString("sub"), rs.getString("note"), rs.getInt("status"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

   /**
   * Method that gets the users  for a specific row, by email
   *
   * @param email The email being requested
   * @return the data for the requested user, null otherwise
   */
  UserRowData SelectUserByEmail(String email) {
    UserRowData res = null;
    try {
      uSelectUserByEmail.setString(1, email);
      ResultSet rs = uSelectUserByEmail.executeQuery();
      if (rs.next()) {
        res = new UserRowData(rs.getInt("id"), rs.getString("username"),
            rs.getString("email"), rs.getInt("gender"), rs.getString("so"),
            rs.getString("sub"), rs.getString("note"), rs.getInt("status"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

   /**
   * Method that inserts a user into the database. These users are the user-inputted subject+message with null values for nonessential attribues
   *
   * @param username The subject for this new row
   * @param email The message body for this new row
   * @return The number of rows that were inserted
   */
  int insertUser(String username, String email, String note) {
    int count = 0;
    try {
      uInsertOne.setString(1, username);
      uInsertOne.setString(2, email);
      uInsertOne.setString(3, note);
      count += uInsertOne.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  /**
   * Method that deletes a user by ID
   * 
   * @param id The id of the users to delete
   * @return the number of users deleted, -1 if error
   */
  int deleteRowUser(int id) {
    int res = -1;
    try {
      uDeleteOne.setInt(1, id);
      res = uDeleteOne.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Method that updates a users attributes
   * 
   * @param username The name of the new user
   * @param gender The gender of the new user
   * @param so The so of the new user
   * @param id The id of the new user
   * @return the number of users deleted, -1 if error
   */
  int updateOneUser(String username, int gender, String so, String note, int id) {
    int res = -1;
    try {
      uUpdateOne.setString(1, username);
      uUpdateOne.setInt(2, gender);
      uUpdateOne.setString(3, so);
      uUpdateOne.setString(4, note);
      uUpdateOne.setInt(5, id);
      res = uUpdateOne.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Method to create userData. If it already exists, print error
   */
  void createUserTable() {
    try {
      uCreateTable.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to remove tblData from the database, print error if DNE
   */
  void dropUserTable() {
    try {
      uDropTable.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  //COMMENT METHODS/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Method that inserts a row into the database. These rows are the user-inputted
   * subject+message
   *
   * @param message The message for this new comment
   * @param post_id The post_id  for this new comment
   * @param userid The userid for this new comment
   * @return The number of rows that were inserted
   */
  int insertComment(String message, int post_id, int userid) {
    int count = 0;
    try {
      cInsertComment.setString(1, message);
      cInsertComment.setInt(2, post_id);
      cInsertComment.setInt(3, userid);
      count += cInsertComment.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  /**
   * Method that updates a comment  in the database
   *
   * @param comMessage The message of the comment to that will replace an old one
   * @param id The id of the comment to be replaced 
   * @param userid The user who left this comment 
   * @return the number of rows udpated, -1 on error
   */
  int updateComment(String comMessage, int id, int userid) {
    int res = -1;
    try {
      cUpdateComment.setString(1, comMessage);
      cUpdateComment.setInt(2, id);
      cUpdateComment.setInt(3, userid);
      res = mUpdateOne.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Method that deletes a row by ID
   * 
   * @param id The id of the comment to delete
   * @param userid The id of the comment to delete
   * @return the number of comment deleted, -1 if error
   */
  int deleteComment(int id, int userid) {
    int res = -1;
    try {
      cDeleteComment.setInt(1, id);
      cDeleteComment.setInt(2, userid);
      res = cDeleteComment.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Method that gets a comment, by id
   *
   * @param id The id of the comment being requested
   * @return the row data for the requested comment, null otherwise
   */
  CommentRowData selectComment(int id) {
    CommentRowData res = null;
    try {
      cSelectComment.setInt(1, id);
      ResultSet rs = cSelectComment.executeQuery();
      if (rs.next()) {
        res =new CommentRowData(rs.getInt("id"), rs.getString("comMessage"),
          rs.getInt("post_id"), rs.getInt("userid"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }


  /**
   * Method that queries the database for a list of all comments by a post
   * 
   * @param post_id The id of the post that's comments are being requested
   * @return All comments, as an ArrayList
   */
  ArrayList<CommentRowData> selectAllPostComments(int post_id) {
    ArrayList<CommentRowData> res = new ArrayList<>();
    try {
      cSelectAllCommentsForAPost.setInt(1, post_id);
      ResultSet rs = cSelectAllCommentsForAPost.executeQuery();
      while (rs.next()) {
        res.add(new CommentRowData(rs.getInt("id"), rs.getString("comMessage"),
            rs.getInt("post_id"), rs.getInt("userid")));
      }
      rs.close();
      return res;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
  /**
   * Method that queries the database for a list of all comments by a user
   * 
   * @param userid id of the user whose comments are being requested
   * @return All rows, as an ArrayList
   */
  ArrayList<CommentRowData> selectAllUserComments(int userid) {
    ArrayList<CommentRowData> res = new ArrayList<>();
    try {
      cSelectAllCommentsForAUser.setInt(1, userid);
      ResultSet rs = cSelectAllCommentsForAUser.executeQuery();
      while (rs.next()) {
        res.add(new CommentRowData(rs.getInt("id"), rs.getString("comMessage"),
            rs.getInt("post_id"), rs.getInt("userid")));
      }
      rs.close();
      return res;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Method that queries the database for a list of all the comments by a user on a single post
   * 
   * @param userid id of the user whose comments are being requested
   * @param post_id The id of the post that's comments are being requested
   * @return All rows, as an ArrayList
   */
  ArrayList<CommentRowData> selectAllUserCommentsOnPost(int userid, int post_id) {
    ArrayList<CommentRowData> res = new ArrayList<>();
    try {
      cSelectAllCommentsForAUserANDPost.setInt(1, post_id);
      cSelectAllCommentsForAUserANDPost.setInt(2, userid );
      ResultSet rs = cSelectAllCommentsForAUserANDPost.executeQuery();
      while (rs.next()) {
        res.add(new CommentRowData(rs.getInt("id"), rs.getString("comMessage"),
            rs.getInt("post_id"), rs.getInt("userid")));
      }
      rs.close();
      return res;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Method to create tblData. If it already exists, print error
   */
  void createCmntTable() {
    try {
      cCreateTable.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to remove tblData from the database, print error if DNE
   */
  void dropCmntTable() {
    try {
      cDropTable.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  //VOTING METHODS/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Method that gets a vote of a user on a post, by their id
   *
   * @param post_id The id of the post being requested
   * @param userid The id of the user being requested
   * @return the row data for the requested comment, null otherwise
   */
  LikeRowData selectUserVote(int post_id, int userid) {
    LikeRowData res = null;
    try {
      lSelectVoteFromUser.setInt(1, post_id);
      lSelectVoteFromUser.setInt(2, userid);
      ResultSet rs = lSelectVoteFromUser.executeQuery();
      if (rs.next()) {
        res = new LikeRowData(rs.getInt("id"), rs.getInt("post_id"),
            rs.getInt("userid"), rs.getInt("vote"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Method that deletes a vote by user and post
   * 
   * @param post_id The id of the post with the unwanted vote
   * @param userid The id of the user who voted unwantedly
   * @return the number of rows deleted, -1 if error
   */
  int deleteVote(int post_id, int userid) {
    int res = -1;
    try {
      lDeleteVote.setInt(1, post_id);
      lDeleteVote.setInt(2, userid);
      res = lDeleteVote.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Method that queries the database for a list of all likes and their attributes
   * 
   * @return All rows, as an ArrayList
   */
  ArrayList<LikeRowData> selectAllLikes() {
    ArrayList<LikeRowData> res = new ArrayList<>();
    try {
      ResultSet rs = lSelectAllVotes.executeQuery();
      while (rs.next()) {
        res.add(new LikeRowData(rs.getInt("id"), rs.getInt("post_id"),
            rs.getInt("userid"), rs.getInt("vote")));
      }
      rs.close();
      return res;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Method to create likeData in the database, print error if already made
   */
  void createLikeTable() {
    try {
      lCreateTable.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to remove likeData from the database, print error if DNE
   */
  void dropLikeTable() {
    try {
      lDropTable.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}