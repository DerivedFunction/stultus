package backend;

import backend.Database.RowData;
import java.util.ArrayList;
import java.util.Random;

import backend.App;
import backend.Database;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class DataRowTest extends TestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public DataRowTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(DataRowTest.class);
  }

  /**
   * Randomize and return a string using "SUB" and 1" to "100"
   */
  public static String rngString() {
    Random random = new Random();
    int num = random.nextInt(10000) + 1;
    return " : " + num;
  }

  public static void testApp() {

    int num = Integer.parseInt(System.getenv("NUM_TESTS"));

    // // Connect to database
    Database db = App.getDatabaseConnection();
    ArrayList<RowData> sub = new ArrayList<>();
    ArrayList<String> subject = new ArrayList<>();
    ArrayList<String> message = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      subject.add("subject" + rngString());
      message.add("message" + rngString());
      sub.add(new RowData(i, subject.get(i), message.get(i)));
      assertEquals(db.insertRow(subject.get(i), message.get(i)), 1); // add new element
    }
    ArrayList<RowData> res = db.selectAll();
    assertTrue(res.containsAll(sub));
    db.disconnect();
  }
}
