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
public class DatabaseTest extends TestCase {
  static Database db=App.getDatabaseConnection("tblTest");
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
   * Randomize and return a string using "SUB" and 1" to "100"
   */
  public static String rngString() {
    Random random = new Random();
    int num = random.nextInt(10000) + 1;
    return " : " + num;
  }
  // tests that inserts work properly (cleanup coupled with delete)
  public static void testInsert ()
  {   
   int num = App.getIntFromEnv("NUM_TESTS",1);
ArrayList<RowData> sub = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      

      String subject = "Subject" + rngString();
      String message = "Message" + rngString();
      sub.add(new RowData(i, subject, message, 0));
      assertTrue(db.insertRow(subject, message) == 1); // add new element
    }
    // Check if database contains all elements we just added
    ArrayList<RowData> res = db.selectAll();
    assertTrue(res.containsAll(sub));
    for(RowData row :res)
    {
      for(RowData su:sub)
      {
       if (row.mMessage.equals(su.mMessage) && row.mSubject.equals(su.mSubject)) {
        db.deleteRow(row.mId);
       } 
        
      }
      
    }
  
  }
  //tests that deletes also work (relies on insert for test articles)
  public static void testDelete ()
  {   
   int num = App.getIntFromEnv("NUM_TESTS",1);
ArrayList<RowData> sub = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      

      String subject = "Subject" + rngString();
      String message = "Message" + rngString();
      sub.add(new RowData(i, subject, message, 0));
     // add new element
    }
    // Check if database contains all elements we just added
    
    ArrayList<RowData> res = db.selectAll();
    for(RowData row :res)
    {
      for(RowData su:sub)
      {
       if (row.mMessage.equals(su.mMessage) && row.mSubject.equals(su.mSubject)) {
        int success=db.deleteRow(row.mId);
        assertEquals(success, 1);
       } 
        
      }
      
    }
    
  }
  // Test to make sure that select one and select all aggree (relies on delete for cleanup and insert for creation)
  public static void testSelects()
  {
    int num = App.getIntFromEnv("NUM_TESTS",1);
    ArrayList<RowData> sub = new ArrayList<>();
        for (int i = 0; i < num; i++) {
          
    
          String subject = "Subject" + rngString();
          String message = "Message" + rngString();
          sub.add(new RowData(i, subject, message, 0));
         // add new element
        }
        // Check if database contains all elements we just added
        ArrayList<RowData> res = db.selectAll();
        for(RowData row :res)
        {
          for(RowData su:sub)
          {
           if (row.mMessage.equals(su.mMessage) && row.mSubject.equals(su.mSubject)) {
              RowData select=db.selectOne(row.mId);
              assertTrue(select.mId==row.mId);
              assertTrue(select.mMessage==row.mMessage);
              assertTrue(select.mSubject==row.mSubject);
              db.deleteRow(select.mId);
           } 
            
          }
          
        }
  }
  // tests toggling likes with backend logic (coupled with inserts and selects)
  public static void testLikes()
  {
    int num = App.getIntFromEnv("NUM_TESTS",1);
    ArrayList<RowData> sub = new ArrayList<>();
        for (int i = 0; i < num; i++) {
          
    
          String subject = "Subject" + rngString();
          String message = "Message" + rngString();
          sub.add(new RowData(i, subject, message, 0));
         // add new element
        }
        // Check if database contains all elements we just added
        ArrayList<RowData> res = db.selectAll();
        for(RowData row :res)
        {
          for(RowData su:sub)
          {
           if (row.mMessage.equals(su.mMessage) && row.mSubject.equals(su.mSubject)) {
            int likeStatus=row.numLikes;
            db.toggleLike(row.mId); 
            RowData changed=db.selectOne(row.mId);
            assertTrue(changed.numLikes==likeStatus+1);
            db.toggleLike(row.mId); 
            changed=db.selectOne(row.mId);
            assertTrue(changed.numLikes==likeStatus-1);
            db.deleteRow(row.mId);
           } 
            
          }
          
        }
  }
  // test updates (relies on insert and select)
  public static void testUpdates()
  {
    int num = App.getIntFromEnv("NUM_TESTS",1);
    ArrayList<RowData> sub = new ArrayList<>();
        for (int i = 0; i < num; i++) {
          
    
          String subject = "Subject" + rngString();
          String message = "Message" + rngString();
          sub.add(new RowData(i, subject, message, 0));
         // add new element
        }
        // Check if database contains all elements we just added
        ArrayList<RowData> res = db.selectAll();
        for(RowData row :res)
        {
          for(RowData su:sub)
          {
           if (row.mMessage.equals(su.mMessage) && row.mSubject.equals(su.mSubject)) {
              int id=row.mId;
                String subject = "Subject" + rngString();
                 String message = "Message" + rngString();
              db.updateOne(id, subject, message);
              RowData changed=db.selectOne(id);
              assertEquals( changed.mMessage,message );
              assertEquals(changed.mSubject,subject);
           } 
         
          }
          
        }
  }
}
