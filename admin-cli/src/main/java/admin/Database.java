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
   * A Prepared Statement for creating the table from the Database
   */
  private PreparedStatement mCreateTable;

  /**
   * A Prepared Statement for deleting the table from the Database
   */
  private PreparedStatement mDropTable;

  /* USER STATEMENTS *///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * A prepared statement for getting all the data in the Database
   */
  private PreparedStatement uSelectAll;

  /**
   * A Prepared Statement for deleting one row from the Database
   */
  private PreparedStatement uDeleteOne;

  /**
   * A Prepared Statement for updating one row from the Database
   */
  private PreparedStatement uUpdateOne;
  /**
   * A Prepared Statement for updating one row from the Database
   */
  private PreparedStatement uSelectUser;
  /**
   * A Prepared Statement for updating one row from the Database
   */
  private PreparedStatement uSelectUserByEmail;

  /**
   * A Prepared Statement for creating the table from the Database
   */
  private PreparedStatement uCreateTable;

  /**
   * A Prepared Statement for deleting the table from the Database
   */
  private PreparedStatement uDropTable;

  /**
   * A Prepared Statement for deleting the table from the Database
   */
  private PreparedStatement uInsertOne;

  /*COMMENT STATEMENTS *///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * A prepared statement for getting all the data in the Database
   */
  private PreparedStatement cCreateTable;
  /**
   * A Prepared Statement for deleting the table from the Database
   */
  private PreparedStatement cDropTable;
  /**
   * A Prepared Statement for deleting the table from the Database
   */
  private PreparedStatement cSelectAllCommentsForAPost;
  /**
   * A Prepared Statement for deleting the table from the Database
   */
  private PreparedStatement cSelectAllCommentsForAUser;
  /**
   * A Prepared Statement for deleting the table from the Database
   */
  private PreparedStatement cSelectAllCommentsForAUserANDPost;
  /**
   * A Prepared Statement for deleting the table from the Database
   */
  private PreparedStatement cSelectComment;
  /**
   * A Prepared Statement for deleting the table from the Database
   */
  private PreparedStatement cDeleteComment;
  /**
   * A Prepared Statement for deleting the table from the Database
   */
  private PreparedStatement cUpdateComment;
  /**
   * A Prepared Statement for deleting the table from the Database
   */
  private PreparedStatement cInsertComment;


  /*VOTING STATEMENTS *///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * A prepared statement for getting all the data in the Database
   */
  private PreparedStatement lCreateTable;
  /**
   * A Prepared Statement for deleting the table from the Database
   */
  private PreparedStatement lDropTable;
  /**
   * A Prepared Finding How A User Voted
   */
  private PreparedStatement lSelectVoteFromUser;
  /**
   * A Prepared Statement for deleting the vote from the database
   */
  private PreparedStatement lDeleteVote;
  /**
   * A Prepared Statement for deleting the vote from the database
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
     * Likes for comments/posts
     */
    int mLikes;

    /**
     * The subject stored in this row
     */
    String mSubject;

    /**
     * The message stored in this row
     */
    String mMessage;

    /**
     * Method that constructs a RowData object by providing values for its fields
     * 
     * @param id      the id of the message
     * @param subject the user-inputted subject/title of their message
     * @param message the user-inputted message/content of their post
     * @param likes   the number of likes a message has
     */
    public RowData(int id, String subject, String message, int likes) {
      mId = id;
      mSubject = subject;
      mMessage = message;
      mLikes = likes;
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
     * Method that constructs a RowData object by providing values for its fields
     * 
     * @param id      the id of the message
     * @param subject the user-inputted subject/title of their message
     * @param message the user-inputted message/content of their post
     * @param likes   the number of likes a message has
     */
    public UserRowData(int id, String name, String email, int gender, String SO) {
      uId = id;
      uName = name;
      uEmail = email;
      uGender = gender;
      uSO = SO;
    }

    /**
     * Method that checks if the username or id of one post is identical to another
     * post
     * 
     * @param other the other object(post) that is being compared to the post being
     *              created
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
     * The ID of this row of the database
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
     * @param id      the id of the message
     * @param subject the user-inputted subject/title of their message
     * @param message the user-inputted message/content of their post
     * @param likes   the number of likes a message has
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
     * @param other the other object(post) that is being compared to the post being
     *              created
     * @return boolean determining whether the two objects are equal or not
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
     * The ID of this row of the database
     */
    int cId;

    /**
     * Message id for the liked post
     */
    String comment;

    /**
     * The user id of the liker stored in this row
     */
    int mId;

    /**
     * Like/Dislike Sign stored in this row
     */
    int uId;

    /**
     * Method that constructs a RowData object by providing values for its fields
     * 
     * @param id      the id of the message
     * @param subject the user-inputted subject/title of their message
     * @param message the user-inputted message/content of their post
     * @param likes   the number of likes a message has
     */
    public CommentRowData(int cid, String com, int mid, int uid) {
      cId = cid;
      comment = com;
      mId = mid;
      uId = uid;
    }

    /**
     * Method that checks if the username or id of one post is identical to another
     * post
     * 
     * @param other the other object(post) that is being compared to the post being
     *              created
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
                                                        + "user_id INT,"
                                                        + "likes INT DEFAULT 0)");
      //Create User Table
      db.uCreateTable = db.mConnection.prepareStatement("CREATE TABLE usrData ("
                                                        + "id SERIAL PRIMARY KEY,"
                                                        + "username VARCHAR(50) NOT NULL,"
                                                        + "email VARCHAR(50) NOT NULL UNIQUE,"
                                                        + "gender INT DEFAULT 0,"
                                                        + "so VARCHAR(10) DEFAULT 'private')");
      //Create Comment Table
      db.cCreateTable = db.mConnection.prepareStatement("CREATE TABLE cmntData ("
                                                        + "id SERIAL PRIMARY KEY,"
                                                        + "comMessage VARCHAR(2048) NOT NULL,"
                                                        + "post_id INT,"
                                                        + "user_id INT,"
                                                        + "FOREIGN KEY (post_id) REFERENCES tblData(id),"
                                                        + "FOREIGN KEY (user_id) REFERENCES usrData(id))");
      //Create Like Table
      db.lCreateTable = db.mConnection.prepareStatement("CREATE TABLE likeData ("
                                                        + "id SERIAL PRIMARY KEY,"
                                                        + "post_id INT,"
                                                        + "vote INT,"
                                                        + "user_id INT,"
                                                        + "FOREIGN KEY (post_id) REFERENCES tblData(id),"
                                                        + "FOREIGN KEY (user_id) REFERENCES usrData(id))");

      //Drop All Tables
      db.mDropTable = db.mConnection.prepareStatement("DROP TABLE tblData");
      db.uDropTable = db.mConnection.prepareStatement("DROP TABLE usrData");
      db.cDropTable = db.mConnection.prepareStatement("DROP TABLE cmntData");
      db.lDropTable = db.mConnection.prepareStatement("DROP TABLE likeData");

      // Standard CRUD Messages operations
      db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblData WHERE id=?");
      db.mInsertOne = db.mConnection.prepareStatement(
          "INSERT INTO tblData VALUES (default, ?, ?, 0)");
      db.mSelectAll = db.mConnection.prepareStatement(
          "SELECT * FROM tblData");
      db.mSelectOne = db.mConnection.prepareStatement("SELECT * from tblData WHERE id=?");
      db.mUpdateOne = db.mConnection.prepareStatement(
          "UPDATE tblData SET message=? WHERE id=?");

      // Standard CRUD User operations
      db.uDeleteOne = db.mConnection.prepareStatement("DELETE FROM usrData WHERE id=?");
      db.uInsertOne = db.mConnection.prepareStatement("INSERT INTO usrData (username,email) VALUES (?,?)");
      db.uSelectUserByEmail = db.mConnection.prepareStatement("SELECT * FROM usrData WHERE email=?");
      db.uSelectUser = db.mConnection.prepareStatement("SELECT * FROM usrData WHERE id=?");
      db.uUpdateOne = db.mConnection.prepareStatement("UPDATE usrData SET username=?, gender=?, so=? where id=?");
      db.uSelectAll = db.mConnection.prepareStatement("SELECT * FROM usrData");


      // Standard CRUD Voting operations
      db.lSelectVoteFromUser = db.mConnection.prepareStatement("SELECT vote FROM likeData WHERE post_id=? AND user_id=?");
      db.lSelectAllVotes = db.mConnection.prepareStatement("SELECT * FROM likeData");
      db.lDeleteVote = db.mConnection.prepareStatement("DELETE FROM likeData WHERE post_id=? AND user_id=?");
      
      // Standard CRUD Comment operations
      db.cSelectAllCommentsForAPost = db.mConnection.prepareStatement("SELECT * FROM cmntData WHERE post_id=? ORDER BY ID DESC");
      db.cSelectAllCommentsForAUser = db.mConnection.prepareStatement("SELECT * FROM cmntData WHERE user_id=? ORDER BY ID DESC");
      db.cSelectAllCommentsForAUserANDPost = db.mConnection.prepareStatement("SELECT * FROM cmntData WHERE post_id=? AND user_id=? ORDER BY ID DESC");
      db.cSelectComment = db.mConnection.prepareStatement("SELECT * cmntData WHERE id=?");
      db.cDeleteComment = db.mConnection.prepareStatement("DELETE cmntData WHERE id=? and user_id=?");
      db.cUpdateComment = db.mConnection.prepareStatement("UPDATE cmntData SET comMessage=? WHERE id=? AND user_id=?");
      db.cInsertComment = db.mConnection.prepareStatement("INSERT INTO cmntData (comMessage, post_id, user_id) VALUES (?,?,?)");


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
            rs.getString("message"), rs.getInt("likes")));
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
            rs.getString("message"), rs.getInt("likes"));
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

  ArrayList<UserRowData> selectAllUsers() {
    ArrayList<UserRowData> res = new ArrayList<>();
    try {
      ResultSet rs = uSelectAll.executeQuery();
      while (rs.next()) {
        res.add(new UserRowData(rs.getInt("id"), rs.getString("username"),
              rs.getString("email"), rs.getInt("gender"), rs.getString("so")));
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
  UserRowData SelectUser(int id) {
    UserRowData res = null;
    try {
      uSelectUser.setInt(1, id);
      ResultSet rs = uSelectUser.executeQuery();
      if (rs.next()) {
        res = new UserRowData(rs.getInt("id"), rs.getString("username"),
            rs.getString("email"), rs.getInt("gender"), rs.getString("so"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

   /**
   * Method that gets all the data for a specific row, by ID
   *
   * @param id The id being requested
   * @return the data for the requested row, null otherwise
   */
  UserRowData SelectUserByEmail(String email) {
    UserRowData res = null;
    try {
      uSelectUserByEmail.setString(1, email);
      ResultSet rs = uSelectUserByEmail.executeQuery();
      if (rs.next()) {
        res = new UserRowData(rs.getInt("id"), rs.getString("username"),
            rs.getString("email"), rs.getInt("gender"), rs.getString("so"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

   /**
   * Method that inserts a row into the database. These rows are the user-inputted
   * subject+message
   *
   * @param subject The subject for this new row
   * @param message The message body for this new row
   * @return The number of rows that were inserted
   */
  int insertUser(String username, String email) {
    int count = 0;
    try {
      uInsertOne.setString(1, username);
      uInsertOne.setString(2, email);
      count += uInsertOne.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  /**
   * Method that deletes a row by ID
   * 
   * @param id The id of the row to delete
   * @return the number of rows deleted, -1 if error
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

  //private PreparedStatement uUpdateOne;
  int updateOneUser(String username, int gender, String so, int id) {
    int res = -1;
    try {
      uUpdateOne.setString(1, username);
      uUpdateOne.setInt(2, gender);
      uUpdateOne.setString(3, so);
      uUpdateOne.setInt(4, id);
      res = uUpdateOne.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Method to create usrData. If it already exists, print error
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

  /* 
  db.cCreateTable = db.mConnection.prepareStatement("CREATE TABLE cmntData ("
                                                        + "id SERIAL PRIMARY KEY,"
                                                        + "comMessage VARCHAR(2048) NOT NULL,"
                                                        + "post_id INT,"
                                                        + "user_id INT,"
                                                        + "FOREIGN KEY (post_id) REFERENCES tblData(id),"
                                                        + "FOREIGN KEY (user_id) REFERENCES userData(id))");
  }

  /**
   * Method that inserts a row into the database. These rows are the user-inputted
   * subject+message
   *
   * @param subject The subject for this new row
   * @param message The message body for this new row
   * @return The number of rows that were inserted
   */
  int insertComment(String message, int post_id, int user_id) {
    int count = 0;
    try {
      cInsertComment.setString(1, message);
      cInsertComment.setInt(2, post_id);
      cInsertComment.setInt(3, user_id);
      count += cInsertComment.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  /**
   * Method that updates the message for a row in the database
   *
   * @param id      The id of the row to update
   * @param message The new message contents
   * @return the number of rows udpated, -1 on error
   */
  int updateComment(String comMessage, int id, int user_id) {
    int res = -1;
    try {
      cUpdateComment.setString(1, comMessage);
      cUpdateComment.setInt(2, id);
      cUpdateComment.setInt(3, user_id);
      res = mUpdateOne.executeUpdate();
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
  int deleteComment(int id, int user_id) {
    int res = -1;
    try {
      cDeleteComment.setInt(1, id);
      cDeleteComment.setInt(2, user_id);
      res = cDeleteComment.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  CommentRowData selectComment(int id) {
    CommentRowData res = null;
    try {
      cSelectComment.setInt(1, id);
      ResultSet rs = cSelectComment.executeQuery();
      if (rs.next()) {
        res =new CommentRowData(rs.getInt("id"), rs.getString("comMessage"),
          rs.getInt("post_id"), rs.getInt("user_id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }


  /**
   * Method that queries the database for a list of all subjects and their IDs
   * 
   * @return All rows, as an ArrayList
   */
  ArrayList<CommentRowData> selectAllPostComments(int post_id) {
    ArrayList<CommentRowData> res = new ArrayList<>();
    try {
      cSelectAllCommentsForAPost.setInt(1, post_id);
      ResultSet rs = cSelectAllCommentsForAPost.executeQuery();
      while (rs.next()) {
        res.add(new CommentRowData(rs.getInt("id"), rs.getString("comMessage"),
            rs.getInt("post_id"), rs.getInt("user_id")));
      }
      rs.close();
      return res;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
  /**
   * Method that queries the database for a list of all subjects and their IDs
   * 
   * @return All rows, as an ArrayList
   */
  ArrayList<CommentRowData> selectAllUserComments(int user_id) {
    ArrayList<CommentRowData> res = new ArrayList<>();
    try {
      cSelectAllCommentsForAUser.setInt(1, user_id);
      ResultSet rs = cSelectAllCommentsForAUser.executeQuery();
      while (rs.next()) {
        res.add(new CommentRowData(rs.getInt("id"), rs.getString("comMessage"),
            rs.getInt("post_id"), rs.getInt("user_id")));
      }
      rs.close();
      return res;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Method that queries the database for a list of all subjects and their IDs
   * 
   * @return All rows, as an ArrayList
   */
  ArrayList<CommentRowData> selectAllUserCommentsOnPost(int user_id, int post_id) {
    ArrayList<CommentRowData> res = new ArrayList<>();
    try {
      cSelectAllCommentsForAUserANDPost.setInt(1, post_id);
      cSelectAllCommentsForAUserANDPost.setInt(2, user_id );
      ResultSet rs = cSelectAllCommentsForAUserANDPost.executeQuery();
      while (rs.next()) {
        res.add(new CommentRowData(rs.getInt("id"), rs.getString("comMessage"),
            rs.getInt("post_id"), rs.getInt("user_id")));
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
  /*
   * //Create Like Table
      db.lCreateTable = db.mConnection.prepareStatement("CREATE TABLE likeData ("
                                                        + "id SERIAL PRIMARY KEY,"
                                                        + "post_id INT,"
                                                        + "vote INT,"
                                                        + "user_id INT,"
                                                        + "FOREIGN KEY (post_id) REFERENCES tblData(id),"
                                                        + "FOREIGN KEY (user_id) REFERENCES userData(id))");
   */
  LikeRowData selectUserVote(int post_id, int user_id) {
    LikeRowData res = null;
    try {
      lSelectVoteFromUser.setInt(1, post_id);
      lSelectVoteFromUser.setInt(2, user_id);
      ResultSet rs = lSelectVoteFromUser.executeQuery();
      if (rs.next()) {
        res = new LikeRowData(rs.getInt("id"), rs.getInt("post_id"),
            rs.getInt("user_id"), rs.getInt("vote"));
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
  int deleteVote(int post_id, int user_id) {
    int res = -1;
    try {
      lDeleteVote.setInt(1, post_id);
      lDeleteVote.setInt(2, user_id);
      res = lDeleteVote.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Method that queries the database for a list of all subjects and their IDs
   * 
   * @return All rows, as an ArrayList
   */
  ArrayList<LikeRowData> selectAllLikes() {
    ArrayList<LikeRowData> res = new ArrayList<>();
    try {
      ResultSet rs = lSelectAllVotes.executeQuery();
      while (rs.next()) {
        res.add(new LikeRowData(rs.getInt("id"), rs.getInt("post_id"),
            rs.getInt("user_id"), rs.getInt("vote")));
      }
      rs.close();
      return res;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  void createLikeTable() {
    try {
      lCreateTable.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to remove tblData from the database, print error if DNE
   */
  void dropLikeTable() {
    try {
      lDropTable.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}