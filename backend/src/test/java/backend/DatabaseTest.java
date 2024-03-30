package backend;

import backend.Database.RowData;
import java.util.ArrayList;
import java.util.Random;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class DatabaseTest extends TestCase {
  static ArrayList<String> dbTable = dbTableElements();

  private static ArrayList<String> dbTableElements() {
    ArrayList<String> ret = new ArrayList<>();
    ret.add("tblTest");
    ret.add("likeTest");
    ret.add("userTest");
    return ret;
  }

  static Database db = App.getDatabaseConnection(dbTable);
  static int num = App.getIntFromEnv("NUM_TESTS", 2);

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public DatabaseTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(DatabaseTest.class);
  }

  /**
   * Randomize and return a string using "SUB" and 1" to "10000"
   */
  public static String rngString() {
    Random random = new Random();
    int num = random.nextInt(10000) + 1;
    return " : " + num;
  }

  /**
   * Tests that inserts work properly (cleanup coupled with delete)
   */
  public static void testInsert() {
    ArrayList<RowData> sub = addElementstoDB(true);
    // Check if database contains all elements we just added
    ArrayList<RowData> res = db.selectAll();
    assertTrue(res.containsAll(sub));
    for (RowData row : res) {
      for (RowData su : sub) {
        if (row.mMessage.equals(su.mMessage) && row.mSubject.equals(su.mSubject)) {
          db.deleteRow(row.mId, 1);
        }
      }
    }
  }

  /**
   * Tests for deletion (relies on insert for test articles)
   */
  public static void testDelete() {
    ArrayList<RowData> sub = addElementstoDB(false);
    // Check if database contains all elements we just added
    ArrayList<RowData> res = db.selectAll();
    for (RowData row : res) {
      for (RowData su : sub) {
        if (row.mMessage.equals(su.mMessage) && row.mSubject.equals(su.mSubject)) {
          int success = db.deleteRow(row.mId, 1);
          assertEquals(success, 1);
        }
      }
    }
  }

  /**
   * Test for selectOne() and selectAll()
   * relies on delete for cleanup and insert for creation
   */
  public static void testSelects() {

    ArrayList<RowData> sub = addElementstoDB(false);
    // Check if database contains all elements we just added
    ArrayList<RowData> res = db.selectAll();
    for (RowData row : res) {
      for (RowData su : sub) {
        if (row.mMessage.equals(su.mMessage) && row.mSubject.equals(su.mSubject)) {
          RowData select = db.selectOne(row.mId);
          assertTrue(select.mId == row.mId);
          assertTrue(select.mMessage == row.mMessage);
          assertTrue(select.mSubject == row.mSubject);
          db.deleteRow(select.mId, 1);
        }
      }
    }
  }

  /**
   * Tests for toggling likes with backend logic (coupled with inserts and
   * selects)
   */
  public static void testLikes() {
    ArrayList<RowData> sub = addElementstoDB(false);
    ;
    // Check if database contains all elements we just added
    ArrayList<RowData> res = db.selectAll();
    for (RowData row : res) {
      for (RowData su : sub) {
        if (row.mMessage.equals(su.mMessage) && row.mSubject.equals(su.mSubject)) {
          int likeStatus = row.numLikes;
          db.toggleLike(row.mId);
          RowData changed = db.selectOne(row.mId);
          assertTrue(changed.numLikes == likeStatus + 1);
          db.toggleLike(row.mId);
          changed = db.selectOne(row.mId);
          assertTrue(changed.numLikes == likeStatus - 1);
          db.deleteRow(row.mId, 1);
        }
      }
    }
  }

  /**
   * Adds element to the DB arrayList for checking
   * 
   * @param sub the ArrayList to add elements
   */
  private static ArrayList<RowData> addElementstoDB(boolean isInsert) {
    ArrayList<RowData> sub = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      String subject = "Subject" + rngString();
      String message = "Message" + rngString();
      sub.add(new RowData(i, subject, message, 0));
      if (isInsert)
        assertTrue(db.insertRow(subject, message, 1) == 1); // Assert and new element
    }
    return sub;
  }

  /**
   * test updates (relies on insert and select)
   */
  public static void testUpdates() {
    ArrayList<RowData> sub = addElementstoDB(false);
    // Check if database contains all elements we just added
    ArrayList<RowData> res = db.selectAll();
    for (RowData row : res) {
      for (RowData su : sub) {
        if (row.mMessage.equals(su.mMessage) && row.mSubject.equals(su.mSubject)) {
          int id = row.mId;
          String subject = "Subject" + rngString();
          String message = "Message" + rngString();
          db.updateOne(id, subject, message, 1);
          RowData changed = db.selectOne(id);
          assertEquals(changed.mMessage, message);
          assertEquals(changed.mSubject, subject);
        }
      }
    }
  }
}
