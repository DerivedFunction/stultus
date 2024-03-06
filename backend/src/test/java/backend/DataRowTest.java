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


  }
}
