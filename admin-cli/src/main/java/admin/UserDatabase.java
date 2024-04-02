package admin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class UserDatabase {
  /**
   * The connection to the database. When there is no connection, it
   * should be null. Otherwise, there is a valid open connection
   * */
  private Connection uConnection;

  /**
   * A prepared statement for getting all the data in the Database
   * */
  private PreparedStatement uSelectAll;

  /**
   * A Prepared Statement for getting one row from the Database
   * */
  private PreparedStatement uSelectOne;

  /**
   * A Prepared Statement for deleting  one row from the Database
   * */
  private PreparedStatement mDeleteOne;

  /**
   * A Prepared Statement for inserting  one row from the Database
   * */
  private PreparedStatement uInsertOne;

  /**
   * A Prepared Statement for updating  one row from the Database
   * */
  private PreparedStatement uUpdateOne;

  /**
   * A Prepared Statement for creating the table from the Database
   * */
  private PreparedStatement uCreateTable;

  /**
   * A Prepared Statement for deleting the table from the Database
   * */
  private PreparedStatement uDropTable;

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
    int uId;

    /**
     * Name for the user (username if you will) 
     */
    String uName;

    /**
     * The email address stored in this row
     * */
    String uEmail;

    /**
     * The Gender Identity stored in this row
     * */
    int uGender;

    /**
     * The Sexual Orientation stored in this row
     * */
    String uSO;

    /**
     * Method that constructs a RowData object by providing values for its fields
     * 
     * @param id the id of the message
     * @param subject the user-inputted subject/title of their message
     * @param message the user-inputted message/content of their post
     * @param likes the number of likes a message has
     * */
    public RowData(int id, String name, String email, int gender, String SO) {
      uId = id;
      uName = name;
      uEmail = email;
      uGender = gender;
      uSO = SO;
    }
    /**
     * Method that checks if the username or id of one post is identical to another post
     * 
     * @param other the other object(post) that is being compared to the post being created
     * @return boolean determining whether the two objects are equal or not
     */
    @Override
    public boolean equals(Object other) {
      RowData obj = (RowData)other;
      if (!this.uName.equals(obj.uName))
        return false;
      if (this.uId != obj.uId )
        return false;
      return true;
    }
  }

  // Database constructor is private
  private UserDatabase() {}

  /**
   * Method that gets a fully configured connection to the database
   * 
   * @param ip The IP address of server
   * @param port The port on the server
   * @param user The user ID to use
   * @param pass The password to use
   * @return the db object created in which we are connecting
   * */

  static UserDatabase getDatabase(String ip, String port, String user,
                              String pass) {
    // Create an unconfigured Database obj
    UserDatabase db = new UserDatabase();

    // Give the Database obj a connection, or else fail
    try {
      Connection conn = DriverManager.getConnection(
          "jdbc:postgresql://" + ip + ":" + port + "/", user, pass);
      if (conn == null) {
        System.err.println("Error: DriverManager.getConnection() returns null");
        return null;
      }
      db.uConnection = conn;
    } catch (SQLException e) {
      System.err.println(
          "Error: DriverManager.getConnection() threw a SQLException");
      e.printStackTrace();
      return null;
    }
    try {
      db.uCreateTable =
          db.uConnection.prepareStatement("CREATE TABLE usrData ("
                                          + "id SERIAL PRIMARY KEY,"
                                          + "username VARCHAR(50) NOT NULL UNIQUE,"
                                          + "email VARCHAR(50) NOT NULL,"
                                          + "gender INT DEFAULT 0,"
                                          + "so VARCHAR(10) DEFAULT 'private')");
      db.uDropTable = db.uConnection.prepareStatement("DROP TABLE usrData");

      // Standard CRUD operations
      db.mDeleteOne =
          db.uConnection.prepareStatement("DELETE FROM usrData WHERE id=?");
      db.mDeleteOne =
          db.uConnection.prepareStatement("DELETE FROM tblData WHERE uid=?");
      /*
       * db.uInsertOne = db.uConnection.prepareStatement(
          "INSERT INTO usrData VALUES (default, ?, ?, 0, default)");
       */
      
      db.uSelectAll = db.uConnection.prepareStatement(
          "SELECT * FROM usrData");
      db.uSelectOne =
          db.uConnection.prepareStatement("SELECT * from usrData WHERE id=?");
      db.uUpdateOne = db.uConnection.prepareStatement(
          "UPDATE usrData SET message=? WHERE id=?");
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
    if (uConnection == null) {
      System.err.println("Unable to close connection: Connection was null");
      return false;
    }
    try {
      uConnection.close();
    } catch (SQLException e) {
      System.err.println("Error: Connection.close() thre a SQLException");
      e.printStackTrace();
      uConnection = null;
      return false;
    }
    uConnection = null;
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
      uInsertOne.setString(1, subject);
      uInsertOne.setString(2, message);
      count += uInsertOne.executeUpdate();
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
      ResultSet rs = uSelectAll.executeQuery();
      while (rs.next()) {
        res.add(new RowData(rs.getInt("id"), rs.getString("username"),
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
   * */
  RowData selectOne(int id) {
    RowData res = null;
    try {
      uSelectOne.setInt(1, id);
      ResultSet rs = uSelectOne.executeQuery();
      if (rs.next()) {
        res = new RowData(rs.getInt("id"), rs.getString("username"),
                          rs.getString("email"), rs.getInt("gender"), rs.getString("so"));
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
      uUpdateOne.setString(1, message);
      uUpdateOne.setInt(2, id);
      res = uUpdateOne.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Method to create usrData. If it already exists, print error
   * */
  void createTable() {
    try {
      uCreateTable.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to remove usrData from the database, print error if DNE
   * */
  void dropTable() {
    try {
      uDropTable.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}