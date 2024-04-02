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
    ret.add("commentTest");
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
    assertEquals(db.deleteUser(db.findUserID(email)), 1);
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
    int userID = db.findUserID(email);
    db.updateUser(userID, "a", gender + 1, "a");
    UserData updated = db.getUserFull(userID);
    assertFalse(test.equals(updated));
    db.updateUser(userID, username, gender, so);
    updated = db.getUserFull(userID);
    assertTrue(test.equals(updated));
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
    db.insertUser(username, email);
    UserData test = db.getUserFull(db.findUserID(email));
    return test;
  }

  /**
   * Tests voting system
   */
  public static void testVote() {
    UserData test = createUser();
    int userID = test.mId;
    db.insertRow("test", "test", userID);
    PostData post = db.selectAll().get(0);

    int postID = post.mId;
    int oldVotes = db.totalVotes(postID);
    // Upvote
    assertTrue(db.toggleVote(postID, 1, userID) == 1);
    assertTrue(db.totalVotes(postID) == oldVotes + 1);
    // Upvote again to cancel
    assertTrue(db.toggleVote(postID, 1, userID) == 1);
    assertTrue(db.totalVotes(postID) == oldVotes);

    // Downvote
    assertTrue(db.toggleVote(postID, -1, userID) == 1);
    assertTrue(db.totalVotes(postID) == oldVotes - 1);
    // Downvote again to cancel
    assertTrue(db.toggleVote(postID, -1, userID) == 1);
    assertTrue(db.totalVotes(postID) == oldVotes);

    // Upvote
    assertTrue(db.toggleVote(postID, 1, userID) == 1);
    assertTrue(db.totalVotes(postID) == oldVotes + 1);
    // Downvote to undo the upvote
    assertTrue(db.toggleVote(postID, -1, userID) == 1);
    assertTrue(db.totalVotes(postID) == oldVotes - 1);

    // Remove it from table
    assertTrue(db.deleteVote(postID, userID) == 1);
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
    int userID1 = user.mId;
    int userID2 = user2.mId;
    // Each user will create a comment to that post
    for (int i = 1; i <= num; i++) {
      comments.add(createComment(userID1, postID));
      comments.add(createComment(userID2, postID));
      user1Comments = db.selectAllCommentByUserID(userID1);
      user2Comments = db.selectAllCommentByUserID(userID2);
      assertTrue(user1Comments.size() == i);
      assertTrue(user2Comments.size() == i);
      assertTrue(db.selectAllCommentByPost(postID).size() == i * 2);
    }
    // Delete the comments
    for (int i = 0; i < num; i++) {
      assertTrue(1 == db.deleteComment(user1Comments.get(i).mId, userID1));
      assertTrue(1 == db.deleteComment(user2Comments.get(i).mId, userID2));
    }
    // Delete the specific post
    db.deleteRow(postID, USERID);
    // Delete the users
    db.deleteUser(userID1);
    db.deleteUser(userID2);
  }
}
