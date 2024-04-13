package admin;

import admin.Database.CommentRowData;
//import admin.Database.LikeRowData;
import admin.Database.RowData;
import admin.Database.UserRowData;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

  /**
   * Creates the test case
   *
   * @param testName name of the test case
   */
  public AppTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(AppTest.class);
  }

  /**
   * Method used to randomize a string for the test case
   * 
   * @return randomized string using "SUB" and "1" to "100"
   */
  public static String rngString() {
    Random random = new Random();
    int num = random.nextInt(10000) + 1;
    return " : " + num;
  }

  /**
   * Method used to test the admin functionality. 
   * 
   * Creates a connection with the database, checks if an added element is successfully added 
   * and checks if a deleted element is sucessfully deleted.
   */

  public void testApp() {
    // get the Postgres configuration and tests from the environment
    Map<String, String> env = System.getenv();
    String ip = env.get("POSTGRES_IP");
    String port = env.get("POSTGRES_PORT");
    String user = env.get("POSTGRES_USER");
    String pass = env.get("POSTGRES_PASS");
    int num = Integer.parseInt(env.get("NUM_TESTS"));
    
    // Connect to database
    Database db = Database.getDatabase(ip, port, user, pass);
    ArrayList<RowData> sub = new ArrayList<>();
    ArrayList<UserRowData> subU = new ArrayList<>();
    ArrayList<CommentRowData> subC = new ArrayList<>();

    //Create User
    String username = "Test User" + rngString();
    String email = "email@yahoo" + rngString();
    String subv = rngString();
    assertTrue(db.insertUser(username, email, subv, "Test Notes") == 1);
    subU = db.selectAllUsers();
    int maxUser = subU.get(subU.size() - 1).uId;

    //Create Idea Element Using New User
    sub = db.selectAll();
    int maxId = sub.get(sub.size() - 1).mId;
    for (int i = 0; i < num; i++) {
      String subject = "Subject" + rngString();
      String message = "Message" + rngString();
      assertTrue(db.insertRow(subject, message, maxUser) == 1); // add new element
    }

    //Comment message on Post with same user
    sub = db.selectAll();
    maxId = sub.get(sub.size() - 1).mId;
    assertTrue(db.insertComment("Test Message", maxId, maxUser) == 1);

    //Delete User and All It's Content
    assertTrue(db.deleteRowUser(maxUser) == 1);

    db.disconnect();

  }
}
