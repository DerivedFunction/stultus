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
    

    for (int i = 0; i < num; i++) {
      String subject = "Subject" + rngString();
      String message = "Message" + rngString();
      sub.add(new RowData(i, subject, message, 0));
      assertTrue(db.insertRow(subject, message) == 1); // add new element
    }
    // Check if database contains all elements we just added
    ArrayList<RowData> res = db.selectAll();
    assertTrue(res.containsAll(sub));

    for(int i = 0; i < num; i++){
      String email = "Email" + rngString();
      String username = "Username" + rngString();
      subU.add(new UserRowData(i, username, email, 0, "None"));
      assertTrue(db.insertUser(username, email) == 1);
    }

    subC.add(new CommentRowData(num, "Comment", 32, 1));
    assertTrue(db.insertComment("Comment", 32, 1) == 1);

    /**
     * Get the maxId, the id of the last element we just added
     * It is in last element of the res ArrayList
     */
    /*
     * int maxId = res.get(res.size() - 1).mId;
    // Delete the test elements from out Database
    for (int i = 0; i < num; i++) {
      assertTrue(db.deleteRow(maxId - i) > 0);
    }
     
    
    // The test elements should no longer be in our database
    res = db.selectAll();
    assertFalse(res.containsAll(sub));
  */
    db.disconnect();
  }
}
