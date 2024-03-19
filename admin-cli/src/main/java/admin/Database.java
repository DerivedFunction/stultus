package admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {
  /**
   * The connection to the database. When there is no connection, it
   * should be null. Otherwise, there is a valid open connection
   * */
  private Connection mConnection;

  /**
   * A prepared statement for getting all the data in the Database
   * */
  private PreparedStatement mSelectAll;

  /**
   * A Prepared Statement for getting one row from the Database
   * */
  private PreparedStatement mSelectOne;

  /**
   * A Prepared Statement for deleting  one row from the Database
   * */
  private PreparedStatement mDeleteOne;

  /**
   * A Prepared Statement for inserting  one row from the Database
   * */
  private PreparedStatement mInsertOne;

  /**
   * A Prepared Statement for updating  one row from the Database
   * */
  private PreparedStatement mUpdateOne;

  /**
   * A Prepared Statement for creating the table from the Database
   * */
  private PreparedStatement mCreateTable;

  /**
   * A Prepared Statement for deleting the table from the Database
   * */
  private PreparedStatement mDropTable;

  /**
   * In the context of the database, RowData represents the data
   * we'd see in a row.
   *
   * We make RowData a static class of Database because it is only
   * an abstract representation of a row of the database. RowData
   * and Database are connected: Both gets updated if one changes.
   * */
  public static class RowData {
    /**
     * The ID of this row of the database
     * */
    int mId;

    /**
     * Likes for comments/posts
     */
    int mLikes;

    /**
     * The subject stored in this row
     * */
    String mSubject;

    /**
     * The message stored in this row
     * */
    String mMessage;

    /**
     * Method that constructs a RowData object by providing values for its fields
     * 
     * @param id the id of the message
     * @param subject the user-inputted subject/title of their message
     * @param message the user-inputted message/content of their post
     * @param likes the number of likes a message has
     * */
    public RowData(int id, String subject, String message, int likes) {
      mId = id;
      mSubject = subject;
      mMessage = message;
      mLikes = likes;
    }
    /**
     * Method that checks if the subject or message of one post is identical to another post
     * 
     * @param other the other object(post) that is being compared to the post being created
     * @return boolean determining whether the two objects are equal or not
     */
    @Override
    public boolean equals(Object other) {
      RowData obj = (RowData)other;
      if (!this.mSubject.equals(obj.mSubject))
        return false;
      if (!this.mMessage.equals(obj.mMessage))
        return false;
      return true;
    }
  }

  // Database constructor is private
  private Database() {}

  /**
   * Method that gets a fully configured connection to the database
   * 
   * @param ip The IP address of server
   * @param port The port on the server
   * @param user The user ID to use
   * @param pass The password to use
   * @return the db object created in which we are connecting
   * */

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
      db.mCreateTable =
          db.mConnection.prepareStatement("CREATE TABLE tblData ("
                                          + "id SERIAL PRIMARY KEY,"
                                          + "subject VARCHAR(100) NOT NULL,"
                                          + "message VARCHAR(2048) NOT NULL,"
                                          + "likes INT DEFAULT 0)");
      db.mDropTable = db.mConnection.prepareStatement("DROP TABLE tblData");

      // Standard CRUD operations
      db.mDeleteOne =
          db.mConnection.prepareStatement("DELETE FROM tblData WHERE id=?");
      db.mInsertOne = db.mConnection.prepareStatement(
          "INSERT INTO tblData VALUES (default, ?, ?, 0)");
      db.mSelectAll = db.mConnection.prepareStatement(
          "SELECT * FROM tblData");
      db.mSelectOne =
          db.mConnection.prepareStatement("SELECT * from tblData WHERE id=?");
      db.mUpdateOne = db.mConnection.prepareStatement(
          "UPDATE tblData SET message=? WHERE id=?");
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
   * */
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
  /**
   * Method that inserts a row into the database. These rows are the user-inputted subject+message
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
   * */
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
   * */
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
   * */
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
   * @param id The id of the row to update
   * @param message The new message contents
   * @return the number of rows udpated, -1 on error
   * */
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
   * */
  void createTable() {
    try {
      mCreateTable.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to remove tblData from the database, print error if DNE
   * */
  void dropTable() {
    try {
      mDropTable.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
