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

  /**
   * A list of table names to test
   * 
   * @return Arraylist of table names
   */
  private static ArrayList<String> dbTableElements() {
    ArrayList<String> ret = new ArrayList<>();
    ret.add("tblTest");
    ret.add("likeTest");
    ret.add("userTest");
    ret.add("commentTest");
    return ret;
  }

  /**
   * Our database to test
   */
  static Database db = App.getDatabaseConnection(dbTable);
  /**
   * Number of elements to add
   */
  static int num = App.getIntFromEnv("NUM_TESTS", 2);
  /**
   * A fixed userID for old tests
   */
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
          assertEquals(select, row);
          db.deleteRow(select.mId, USERID);
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
        assertEquals(1, db.insertRow(subject, message, USERID)); // Assert and new element
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
    String sub = "sub" + rngString();
    // First time it works
    assertEquals(1, db.insertUser(username, email, sub));
    // Second time it doesn't
    assertEquals(0, db.insertUser(username, email, sub));
    // We can delete it
    assertEquals(1, db.deleteUser(db.findUserID(email)));
  }

  /**
   * Tests if we can update a user
   */
  public static void testUpdateUser() {
    UserData test = createUser();
    String email = test.uEmail;
    String username = test.uUsername;
    int gender = test.uGender;
    String so = test.uSO;
    String sub = test.uSub;
    assertEquals(db.findUserIDfromSub(sub), test.uID);
    int userID = db.findUserID(email);
    db.updateUser(userID, sub, "a", gender + 1, "a");
    UserData updated = db.getUserFull(userID);
    assertFalse(test.equals(updated));
    db.updateUser(userID, sub, username, gender, so);
    updated = db.getUserFull(userID);
    assertEquals(test, updated);
    db.deleteUser(userID);
  }

  /**
   * Creates a new user
   * 
   * @return UserData of added user
   */
  private static UserData createUser() {
    String email = rngString() + "@example.com";
    String username = "test account";
    String sub = "sub" + rngString();
    db.insertUser(username, email, sub);
    UserData test = db.getUserFull(db.findUserID(email));
    return test;
  }

  /**
   * Tests voting system
   */
  public static void testVote() {
    UserData test = createUser();
    int userID = test.uID;
    db.insertRow("test", "test", userID);
    PostData post = db.selectAll().get(0);

    int postID = post.mId;
    int oldVotes = db.totalVotes(postID);
    // Upvote
    assertEquals(1, db.toggleVote(postID, 1, userID));
    assertEquals(oldVotes + 1, db.totalVotes(postID));
    // Upvote again to cancel
    assertEquals(1, db.toggleVote(postID, 1, userID));
    assertEquals(oldVotes, db.totalVotes(postID));

    // Downvote
    assertEquals(1, db.toggleVote(postID, -1, userID));
    assertEquals(oldVotes - 1, db.totalVotes(postID));
    // Downvote again to cancel
    assertEquals(1, db.toggleVote(postID, -1, userID));
    assertEquals(oldVotes, db.totalVotes(postID));

    // Upvote
    assertEquals(1, db.toggleVote(postID, 1, userID));
    assertEquals(oldVotes + 1, db.totalVotes(postID));
    // Downvote to undo the upvote
    assertEquals(1, db.toggleVote(postID, -1, userID));
    assertEquals(oldVotes - 1, db.totalVotes(postID));

    // Remove it from table
    assertEquals(1, db.deleteVote(postID, userID));
    db.deleteRow(postID, userID);
    db.deleteUser(userID);
  }

  /**
   * Generates a random comment
   * 
   * @param userID author
   * @param postID post to comment
   * @return CommentData
   */
  private static CommentData createComment(int userID, int postID) {
    ArrayList<CommentData> comment = null;
    db.insertComment(rngString(), postID, userID);
    comment = db.selectAllComments(userID, postID);
    return comment.get(0);
  }

  /**
   * Tests if Comments to a specific post
   * made by different users work
   */
  public static void testComments() {
    UserData user = createUser();
    UserData user2 = createUser();
    ArrayList<CommentData> user1Comments = new ArrayList<>();
    ArrayList<CommentData> user2Comments = new ArrayList<>();
    ArrayList<CommentData> comments = new ArrayList<>();

    // Create a new post
    db.insertRow(rngString(), rngString(), USERID);
    ArrayList<PostData> post = db.selectAll();
    int postID = post.get(0).mId;
    int userID1 = user.uID;
    int userID2 = user2.uID;
    // Each user will create a comment to that post
    for (int i = 1; i <= num; i++) {
      comments.add(createComment(userID1, postID));
      comments.add(createComment(userID2, postID));
      user1Comments = db.selectAllCommentByUserID(userID1);
      user2Comments = db.selectAllCommentByUserID(userID2);
      assertEquals(i, db.selectAllComments(userID1, postID).size());
      assertEquals(i, db.selectAllComments(userID2, postID).size());
      assertEquals(i, user2Comments.size());
      assertEquals(i * 2, db.selectAllCommentByPost(postID).size());
    }
    // Delete the comments
    for (int i = 0; i < num; i++) {
      assertEquals(1, db.deleteComment(user1Comments.get(i).cId, userID1));
      assertEquals(1, db.deleteComment(user2Comments.get(i).cId, userID2));
    }
    // Delete the specific post
    db.deleteRow(postID, USERID);
    // Delete the users
    db.deleteUser(userID1);
    db.deleteUser(userID2);
  }
}
