package backend;

import java.net.URI;
import java.net.URISyntaxException;
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
  /** 
   * A prepared statement for adding a like to a message
   */
  private PreparedStatement mAddLike;

  /** 
   * A prepared statement for removing a like to a message
   */
  private PreparedStatement mRemoveLike;


  
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
     * Construct a RowData object by providing values for its fields
     */
    public RowData(int id, String subject, String message) {
      mId = id;
      mSubject = subject;
      mMessage = message;
    }

    @Override
    public boolean equals(Object other) {
      RowData obj = (RowData) other;
      if (!this.mSubject.equals(obj.mSubject))
        return false;
      return this.mMessage.equals(obj.mMessage);
    }
  }

  // Database constructor is private
  private Database() {
  }

  /**
   * Get a fully-configured connection to the database
   * 
   * @param db_url       The url to the database
   * @param port_default port to use if absent in db_url
   * 
   * @return A Database object, or null if we cannot connect properly
   */
  static Database getDatabase(String db_url, String port_default) {
    try {
      URI dbUri = new URI(db_url);
      String username = dbUri.getUserInfo().split(":")[0];
      String password = dbUri.getUserInfo().split(":")[1];
      String host = dbUri.getHost();
      String path = dbUri.getPath();
      String port = dbUri.getPort() == -1 ? port_default : Integer.toString(dbUri.getPort());

      return getDatabase(host, port, path, username, password);
    } catch (URISyntaxException s) {
      System.out.println("URI Syntax Error");
      return null;
    }
  }

  /**
   * Get a fully configured connected to the database
   * 
   * @param ip   The IP address of server
   * @param port The port on the server
   * @param path The path
   * @param user The user ID to use
   * @param pass The password to use
   */

  static Database getDatabase(String ip, String port, String path, String user, String pass) {
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
    return createPreparedStatements(db);
  }

  private static Database createPreparedStatements(Database db) {
    try {
      db.mCreateTable = db.mConnection.prepareStatement("CREATE TABLE tblData ("
          + "id SERIAL PRIMARY KEY,"
          + "subject VARCHAR(50) NOT NULL,"
          + "message VARCHAR(500) NOT NULL)");
      db.mDropTable = db.mConnection.prepareStatement("DROP TABLE tblData");

      // Standard CRUD operations
      db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblData WHERE id=?");
      db.mInsertOne = db.mConnection.prepareStatement(
          "INSERT INTO tblData VALUES (default, ?, ?,default)");
      db.mSelectAll = db.mConnection.prepareStatement(
          "SELECT id, subject, message, likes FROM tblData ORDER BY id DESC");
      db.mSelectOne = db.mConnection.prepareStatement("SELECT * from tblData WHERE id=?");
      db.mUpdateOne = db.mConnection.prepareStatement(
          "UPDATE tblData SET subject=?, message=? WHERE id=?");
      db.mAddLike=db.mConnection.prepareStatement("UPDATE tblDatan SET likes=likes+1 WHERE id=? AND likes=0");
      db.mRemoveLike=db.mConnection.prepareStatement("UPDATE tblDatan SET likes=likes-1 WHERE id=? AND likes=1");
      
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
      System.err.println("Error: Connection.close() thre a SQLException");
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
        res.add(new RowData(rs.getInt("id"), rs.getString("subject"),
            rs.getString("message")));
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
            rs.getString("message"));
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
   * @param message The new msg contents
   *
   * @return the number of rows udpated, -1 on error
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
   * @param id The id of the row to add the like
   * @return the number of rows updated
   */
  int toggleLike(int id)
   {
    int res=-1;
    try {
      mAddLike.setInt(1,id);
    res =mAddLike.executeUpdate();
    if(res ==0)
      {
        mRemoveLike.setInt(1, id);
        res=mRemoveLike.executeUpdate();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }



}
