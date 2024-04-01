package backend;

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
  static int USERID = 2;

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
    ArrayList<PostData> sub = addElementstoDB(true);
    // Check if database contains all elements we just added
    ArrayList<PostData> res = db.selectAll();
    assertTrue(res.containsAll(sub));
    for (PostData row : res) {
      for (PostData su : sub) {
        if (row.equals(su)) {
          db.deleteRow(row.mId, USERID);
        }
      }
    }
  }

  /**
   * Tests for deletion (relies on insert for test articles)
   */
  public static void testDelete() {
    ArrayList<PostData> sub = addElementstoDB(false);
    // Check if database contains all elements we just added
    ArrayList<PostData> res = db.selectAll();
    for (PostData row : res) {
      for (PostData su : sub) {
        if (row.equals(su)) {
          int success = db.deleteRow(row.mId, USERID);
          assertEquals(success, USERID);
        }
      }
    }
  }

  /**
   * Test for selectOne() and selectAll()
   * relies on delete for cleanup and insert for creation
   */
  public static void testSelects() {

    ArrayList<PostData> sub = addElementstoDB(false);
    // Check if database contains all elements we just added
    ArrayList<PostData> res = db.selectAll();
    for (PostData row : res) {
      for (PostData su : sub) {
        if (row.equals(su)) {
          PostData select = db.selectOne(row.mId);
          assertTrue(select.equals(row));
          db.deleteRow(select.mId, USERID);
        }
      }
    }
  }

  /**
   * Tests for toggling likes with backend logic (coupled with inserts and
   * selects)
   */
  public static void testLikes() {
    ArrayList<PostData> sub = addElementstoDB(false);
    ;
    // Check if database contains all elements we just added
    ArrayList<PostData> res = db.selectAll();
    for (PostData row : res) {
      for (PostData su : sub) {
        if (row.equals(su)) {
          int likeStatus = row.numLikes;
          db.toggleLike(row.mId);
          PostData changed = db.selectOne(row.mId);
          assertTrue(changed.numLikes == likeStatus + 1);
          db.toggleLike(row.mId);
          changed = db.selectOne(row.mId);
          assertTrue(changed.numLikes == likeStatus - 1);
          db.deleteRow(row.mId, USERID);
        }
      }
    }
  }

  /**
   * Adds element to the DB arrayList for checking
   * 
   * @param sub the ArrayList to add elements
   */
  private static ArrayList<PostData> addElementstoDB(boolean isInsert) {
    ArrayList<PostData> sub = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      String subject = "Subject" + rngString();
      String message = "Message" + rngString();
      sub.add(new PostData(i, subject, message, 0, USERID));
      if (isInsert)
        assertTrue(db.insertRow(subject, message, USERID) == 1); // Assert and new element
    }
    return sub;
  }

  /**
   * test updates (relies on insert and select)
   */
  public static void testUpdates() {
    ArrayList<PostData> sub = addElementstoDB(false);
    // Check if database contains all elements we just added
    ArrayList<PostData> res = db.selectAll();
    for (PostData row : res) {
      for (PostData su : sub) {
        if (row.equals(su)) {
          int id = row.mId;
          String subject = "Subject" + rngString();
          String message = "Message" + rngString();
          db.updateOne(id, subject, message, USERID);
          PostData changed = db.selectOne(id);
          assertEquals(changed.mMessage, message);
          assertEquals(changed.mSubject, subject);
        }
      }
    }
  }

  /**
   * Test user creation
   */
  public static void testUser() {
    String email = rngString() + "@example.com";
    String username = "test account";
    // First time it works
    assertEquals(db.insertUser(username, email), 1);
    // Second time it doesn't
    assertEquals(db.insertUser(username, email), 0);
    // We can delete it
    assertEquals(db.deleteUser(db.findUser(email)), 1);
  }

  /**
   * Tests if we can update a user
   */
  public static void testUpdateUser() {
    UserData test = createUser();
    String email = test.mEmail;
    String username = test.mUsername;
    int gender = test.mGender;
    String so = test.mSO;
    int userID = db.findUser(email);
    db.updateUser(userID, "a", gender + 1, "a");
    UserData updated = db.getUserFull(userID);
    assertFalse(test.equals(updated));
    db.updateUser(userID, username, gender, so);
    updated = db.getUserFull(userID);
    assertTrue(test.equals(updated));
    db.deleteUser(userID);
  }

  private static UserData createUser() {
    String email = rngString() + "@example.com";
    String username = "test account";
    db.insertUser(username, email);
    UserData test = db.getUserFull(db.findUser(email));
    return test;
  }
}
