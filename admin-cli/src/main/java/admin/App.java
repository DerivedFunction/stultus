package admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * App is our basic admin app. For now, it is a demonstration of the six key
 * operations on a database: connect, insert, update, query, delete, disconnect
 */
public class App {

  /**
   * Method used to print the menu for our program
   */
  static void menu(){
    System.out.println("SELECT A TABLE TO MANIPULATE");
    System.out.println("[I] Ideas/Messages/Posts");
    System.out.println("[U] Users");
    System.out.println("[C] Comments");
    System.out.println("[L] Voting Results");
    System.out.println("[q] Quit Program");
    System.out.println("[?] Help (this message)");
  }
  /**
   * Method used to print options for message menu
   */
  static void messageMenu() {
    System.out.println("Message Menu");
    System.out.println("  [T] Create tblData");
    System.out.println("  [D] Drop tblData");
    System.out.println("  [1] Query for a specific post");
    System.out.println("  [*] Query for all post");
    System.out.println("  [-] Delete a post");
    System.out.println("  [+] Insert a new posts");
    System.out.println("  [~] Update a post");
    System.out.println("  [h] Hide/Unhide a post");
    System.out.println("  [q] Quit Table");
    System.out.println("  [?] Help (this message)");
  }
  /**
   * Method used to print options for user menu
   */
  static void userMenu() {
    System.out.println("User Menu");
    System.out.println("  [T] Create usrData"); 
    System.out.println("  [D] Drop usrData"); 
    System.out.println("  [1] Query a User By Email"); 
    System.out.println("  [2] Query a User By ID"); 
    System.out.println("  [*] Query for all users");
    System.out.println("  [-] Delete a user");
    System.out.println("  [+] Insert a new user");
    System.out.println("  [~] Update a user");
    System.out.println("  [h] Hide/Unhide a user");
    System.out.println("  [q] Quit Table");
    System.out.println("  [?] Help (this message)");
  }
  /**
   * Method used to print options for comment menu
   */
  static void cmntMenu() {
    System.out.println("Comments Menu");
    System.out.println("  [T] Create cmntData"); 
    System.out.println("  [D] Drop cmntData"); 
    System.out.println("  [1] Query a single post's comments");
    System.out.println("  [2] Query a single user's comments");
    System.out.println("  [3] Query a single user's comments on a post");
    System.out.println("  [-] Delete a comment");
    System.out.println("  [+] Insert a new comment");
    System.out.println("  [~] Update a comment");
    System.out.println("  [h] Hide/Unhide a Comment");
    System.out.println("  [q] Quit Table");
    System.out.println("  [?] Help (this message)");
  }
  /**
   * Method used to print options for vote menu
   */
  static void likeMenu() {
    System.out.println("Voting Menu");
    System.out.println("  [T] Create likeData");
    System.out.println("  [D] Drop likeData");
    System.out.println("  [1] Query for a specific vote row");
    System.out.println("  [*] Query for all votes");
    System.out.println("  [-] Delete a row");
    System.out.println("  [q] Quit Table");
    System.out.println("  [?] Help (this message)");
  }


  /**
   * Method used to ask the user to enter a menu option; repeat until we get a valid
   * option.
   * 
   * @param in A BufferedReader to read input from keyboard
   * @return the character corresponding to the chosen menu option
   */
  static char prompt(BufferedReader in) {
    // valid actions
    String actions = "TD1234*-+~hq?IUCL";
    // We repeat until a valid char is selected
    while (true) {
      System.out.print("[" + actions + "] :> ");
      String action;
      try {
        action = in.readLine();
      } catch (IOException e) {
        e.printStackTrace();
        continue;
      }
      if (action.length() != 1)
        continue;
      if (actions.contains(action))
        return action.charAt(0);
      System.out.println("Invalid command");
    }
  }

  /**
   * Method used to ask the user to enter a String message
   *
   * @param in      from the keyboard
   * @param message to be displayed
   * @return the string the user provided
   */
  static String getString(BufferedReader in, String message) {
    String s;
    try {
      System.out.print(message + " :> ");
      s = in.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }
    return s;
  }

  /**
   * Method used to ask the user to enter an int
   * 
   * @param in      from the keyboard
   * @param message to be displayed
   * @return the integer the user provided, -1 on error
   */
  static int getInt(BufferedReader in, String message) {
    int i = -1;
    try {
      System.out.print(message + " :> ");
      i = Integer.parseInt(in.readLine());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    return i;
  }

  /**
   * Method used to connect to database and handles all admin functionalities
   * 
   * @param argv command line arguments, unused in method
   */
  public static void main(String[] argv) {
    // get the Postgres configuration from the environment
    Map<String, String> env = System.getenv();
    String ip = env.get("POSTGRES_IP");
    String port = env.get("POSTGRES_PORT");
    String user = env.get("POSTGRES_USER");
    String pass = env.get("POSTGRES_PASS");

    // Connect to database
    Database db = Database.getDatabase(ip, port, user, pass);
    if (db == null)
      return;
    menu(); // Show menu
    // Interpret commands
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    boolean cont = true;
    while (cont) {
      char action = prompt(in);
      switch (action) {
        case 'I':
          postMethods(in, db);
          break;
        case 'U':
          userMethods(in, db);
          break;
        case 'C':
          commentMethods(in, db);
          break;
        case 'L':
          likeMethods(in, db);
          break;
        case 'q':
          cont = false;
          break;
        case '?':
          menu();
          break;
      }
    }
    db.disconnect();
  }

  /**
   * Refactored methods that manipulate comments
   * 
   * @param in for getString and getInt
   * @param db database to be manipulated
   */
  public static void commentMethods(BufferedReader in, Database db){
    cmntMenu();
    String printfmt = " [%3s] |  %-30s | %-40s | [%3s] | [%3s]%n";
    boolean cont = true;
    while(cont){
      char action = prompt(in);
      switch (action) {
        case '?':
          cmntMenu();
          break;
        case 'T':
          db.createCmntTable();
          break;
        case 'D':
          Scanner scan = new Scanner(System.in);
          String input;
          System.out.println("Are you sure you want to drop the table? \n Enter Y for Yes, anything else for No");
          input = scan.nextLine();
          if(input.equals("Y")){
            db.dropCmntTable();
            break;
          } else{
              System.out.println("Not dropping table.");
              break;
          }
        case '1':
          int id = getInt(in, "Enter the post ID");
          if (id == -1)
            continue;
            ArrayList<Database.CommentRowData> res = db.selectAllPostComments(id);
            System.out.println(" Current Comments On Queried Post");
            System.out.println(" -------------------------");
            System.out.printf(printfmt, "Id", "Message", "Post", "User", "Status");
            System.out.println("--------------------------------------------------------------------------------------------------");
            for (Database.CommentRowData rd : res) {
              String comment = rd.comment.length() > 30 ? rd.comment.substring(0, 30) : rd.comment;
              System.out.printf(printfmt, rd.cId, comment, rd.mId, rd.uId, rd.status);
            }
          break;
        case '2':
          int userId = getInt(in, "Enter the user ID");
          if (userId == -1)
            continue;
          ArrayList<Database.CommentRowData> res_1 = db.selectAllUserComments(userId);
          System.out.println(" Current Comments By Queried User");
          System.out.println(" -------------------------");
          System.out.printf(printfmt, "Id", "Message", "Post", "User", "Status");
          System.out.println("--------------------------------------------------------------------------------------------------");
          for (Database.CommentRowData rd : res_1) {
            String comment = rd.comment.length() > 30 ? rd.comment.substring(0, 30) : rd.comment;
            System.out.printf(printfmt, rd.cId, comment, rd.mId, rd.uId, rd.status);
          }
          break;
        case '3':
          int postId = getInt(in, "Enter the post ID");
          int userId_1 = getInt(in, "Enter the user ID");
          if (userId_1 == -1 || postId == -1)
            continue;
          ArrayList<Database.CommentRowData> res_2 = db.selectAllUserCommentsOnPost(userId_1, postId);
          System.out.println(" Comments On Post By Queried User");
          System.out.println(" -------------------------");
          System.out.printf(printfmt, "Id", "Message", "Post", "User", "Status");
          System.out.println("--------------------------------------------------------------------------------------------------");
          for (Database.CommentRowData rd : res_2) {
            String comment = rd.comment.length() > 30 ? rd.comment.substring(0, 30) : rd.comment;
            System.out.printf(printfmt, rd.cId, comment, rd.mId, rd.uId, rd.status);
          }
          break;
        case '-':{
          int comid = getInt(in, "Enter the comment ID");
          int usrId = getInt(in, "Enter the user ID");
          if (comid == -1 || usrId == -1)
            continue;
          int res_del = db.deleteComment(comid, usrId);
          if (res_del == -1)
            continue;
          System.out.println(" " + res_del + " rows deleted");
          break;
        }
        case '+':
          String message = getString(in, "Enter Comment Message");
          if(message.length() > 2048){
            System.out.println("Message length too long (must not exceed 2048 characters)");
            break;
          }
          int pid = getInt(in, "Enter Post ID");
          int uid = getInt(in, "Enter User ID");
          if(pid == -1 || uid == -1)
            continue;
          int resins = db.insertComment(message, pid, uid);
          System.out.println(resins + " comments added");
          break;
        case '~':
          String messageUpd = getString(in, "Enter Comment Message");
          if(messageUpd.length() > 2048){
            System.out.println("Message length too long (must not exceed 2048 characters)");
            break;
          }
          int pidup = getInt(in, "Enter Post ID");
          int uidup = getInt(in, "Enter User ID");
          if(pidup == -1 || uidup == -1)
            continue;
          int resup = db.updateComment(messageUpd, pidup, uidup);
          System.out.println(resup + " comments updated");
          break;
        case 'h': {
          int idh = getInt(in, "Enter the user ID");
          if (idh == -1)
            continue;
          int resh = db.toggleStatusCom(idh);
          if (resh == -1)
            continue;
          System.out.println(" " + resh + " statuses updated");
          break;
        }
        case 'q':
          cont = false;
          break;
      }
    }
  }
  /**
   * Refactored methods that manipulate users
   * 
   * @param in for getString and getInt
   * @param db database to be manipulated
   */
  public static void userMethods(BufferedReader in, Database db){
    userMenu();
    boolean cont = true;
    while(cont){
      char action = prompt(in);
      switch (action) {
        case '?':
          userMenu();
          break;
        case 'q':
          cont = false;
          break;
        case 'T':
          db.createUserTable();
          break;
        case 'D':
          Scanner scan = new Scanner(System.in);
          String input;
          System.out.println("Are you sure you want to drop the table? \n Enter Y for Yes, anything else for No");
          input = scan.nextLine();
          if(input.equals("Y")){
            db.dropUserTable();
            break;
          } else{
              System.out.println("Not dropping table.");
              break;
          }
        case '1':
          String email = getString(in, "Enter User's Email");
          Database.UserRowData res = db.SelectUserByEmail(email);
          if (res != null) {
            System.out.println(" [" + res.uId + "] " + res.uName);
            System.out.println(" Message Id: " + res.uEmail);
            System.out.println(" User Gender: " + res.uGender);
            System.out.println(" User SO: " + res.uSO);

            System.out.println(" User Sub: " + res.sub);
            System.out.println(" User Bio: " + res.note);
            if(res.status == 1){
              System.out.println(" User Status: Public");
            }else{
              System.out.println(" User Status: Blocked From Public View");
            }
          }
          break;  
        case '2':
          int uid = getInt(in, "Enter User's Id");
            if (uid == -1)
              continue;
            Database.UserRowData resid = db.SelectUser(uid);
            if (resid != null) {
              System.out.println(" [" + resid.uId + "] " + resid.uName);
              System.out.println(" Message ID: " + resid.uEmail);
              System.out.println(" User Gender: " + resid.uGender);
              System.out.println(" User SO: " + resid.uSO);

              System.out.println(" User Sub: " + resid.sub);
              System.out.println(" User Bio: " + resid.note);
              if(resid.status == 1){
                System.out.println(" User Status: Public");
              }else{
                System.out.println(" User Status: Blocked From Public View");
              }
            }
          break;
        case '*':{
          ArrayList<Database.UserRowData> resall = db.selectAllUsers();
          if (resall == null)
            continue;
          System.out.println(" All Current Users");
          System.out.println(" -------------------------");
          String printfmt = " [%3s] |  %-25s | %-30s | | %-15s | %-15s | %-15s | [%3s] |[%3s]%n";
          System.out.printf(printfmt, "Id", "Username", "Email", "SO", "Sub", "Note", "Gender", "Status");
          System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
          for (Database.UserRowData rd : resall) {
            String name = rd.uName.length() > 25 ? rd.uName.substring(0, 25) : rd.uName;
            String subemail = rd.uEmail.length() > 30 ? rd.uEmail.substring(0, 30) : rd.uEmail;
            String SO = rd.uSO.length() > 15 ? rd.uSO.substring(0,15) : rd.uSO; 
            String su = rd.sub.length() > 15 ? rd.sub.substring(0,15) : rd.sub;
            if(rd.note != null){
              String no = rd.note.length() > 15 ? rd.note.substring(0,15) : rd.note;
              System.out.printf(printfmt, rd.uId, name, subemail, SO, su, no, rd.uGender, rd.status);
            }else{
              System.out.printf(printfmt, rd.uId, name, subemail, SO, su, rd.note, rd.uGender, rd.status);
            }
            
            
          }
            break;
        }
        case '-':
          int delid = getInt(in, "Enter User ID");
          if (delid == -1)
            continue;
          int resdel = db.deleteRowUser(delid);
          if (resdel == -1)
            continue;
          System.out.println(" " + resdel + " user deleted");
          break;
        case '+':
          String insuser = getString(in, "Enter Username");
          String inemail = getString(in, "Enter Email");
          String innote = getString(in, "Enter Bio");
          if(insuser.length() > 50){
            System.out.println("Username length too long (must not exceed 50 characters)");
            break;
          }
          if(inemail.length() > 50){
            System.out.println("Email length too long (must not exceed 50 characters)");
            break;
          }
          if(innote.length() > 2048){
            System.out.println("Note length too long (must not exceed 2048 characters)");
            break;
          }
          int resins = db.insertUser(insuser, inemail, innote);
          System.out.println(resins + " rows added");
          break;
        case '~':
          String upuser = getString(in, "Enter Username");
          int upgender = getInt(in, "Enter Gender (INT)");
          String upso = getString(in, "Enter SO");
          String upnote = getString(in, "Enter Bio");
          int upuid = getInt(in, "Enter ID");
          if(upgender == -1 || upuid == -1)
            continue;
          int upres = db.updateOneUser(upuser, upgender, upso, upnote, upuid);
          if (upres == -1)
            continue;
          System.out.println(" " + upres + " users updated");
          break;
        case 'h': {
          int id = getInt(in, "Enter the user ID");
          if (id == -1)
            continue;
          int resh = db.toggleStatusUse(id);
          if (resh == -1)
            continue;
          System.out.println(" " + resh + " statuses updated");
          break;
        }
      }
    }
  }

  /**
   * Refactored methods that manipulate votes
   * 
   * @param in for getString and getInt
   * @param db database to be manipulated
   */
  public static void likeMethods(BufferedReader in, Database db){
    likeMenu();
    boolean cont = true;
    while(cont){
      char action = prompt(in);
      switch (action) {
        case '?':
          likeMenu();
          break;
        case 'q':
          cont = false;
          break;
        case 'T':
          db.createLikeTable();
          break;
        case 'D':
          Scanner scan = new Scanner(System.in);
          String input;
          System.out.println("Are you sure you want to drop the table? \n Enter Y for Yes, anything else for No");
          input = scan.nextLine();
          if(input.equals("Y")){
            db.dropLikeTable();
            break;
          } else{
              System.out.println("Not dropping table.");
              break;
          }
        case '1':
          int pid = getInt(in, "Enter Post ID");
          int uid = getInt(in, "Enter the User ID");
          if (pid == -1 || uid == -1)
            continue;
          Database.LikeRowData res = db.selectUserVote(pid, uid);
          if (res != null) {
            System.out.println(" [" + res.lId + "] " + res.lVote);
            System.out.println(" Message Id: " + res.mId);
            System.out.println(" User ID: " + res.uId);
          }
          break;  
        case '*':{
          ArrayList<Database.LikeRowData> resall = db.selectAllLikes();
          if (resall == null)
            continue;
          System.out.println(" Current Database Contents");
          System.out.println(" -------------------------");
          String printfmt = " [%3s] |  %-30s | %-40s | [%3s]%n";
          System.out.printf(printfmt, "Like Id", "Message Id", "User Id", "Vote Val");
          System.out.println("--------------------------------------------------------------------------------------------------");
          for (Database.LikeRowData rd : resall) {
            System.out.printf(printfmt, rd.lId, rd.mId, rd.uId, rd.lVote);
          }
            break;
        }
        case '-':
          int pId = getInt(in, "Enter Post ID");
          int uId = getInt(in, "Enter the User ID");
          if (pId == -1 || uId == -1)
            continue;
          int resdel = db.deleteVote(pId, uId);
          if (resdel == -1)
            continue;
          System.out.println(" " + resdel + " like deleted");
          break;
      }
    }
  }
  /**
   * Refactored methods that manipulate posts
   * 
   * @param in for getString and getInt
   * @param db database to be manipulated
   */
  public static void postMethods(BufferedReader in, Database db){
    messageMenu();
    boolean cont = true;
    while (cont) {
      char action = prompt(in);
      switch (action) {
        case '?':
          messageMenu();
          break;
        case 'q':
          cont = false;
          break;
        case 'T':
          db.createTable();
          break;
        case 'D':
        Scanner scan = new Scanner(System.in);
        String input;
        System.out.println("Are you sure you want to drop the table? \n Enter Y for Yes, anything else for No");
        input = scan.nextLine();
          if(input.equals("Y")){
          db.dropTable();
          break;
          } else{
            System.out.println("Not dropping table.");
            break;
          }
        case '1': {
          int id = getInt(in, "Enter the row ID");
          if (id == -1)
            continue;
          Database.RowData res = db.selectOne(id);
          if (res != null) {
            System.out.println(" [" + res.mId + "] " + res.mSubject);
            System.out.println(" Message: " + res.mMessage);
            System.out.println(" User: " + res.uID);
            if(res.status == 1){
              System.out.println(" Status: Visable To Users");
            }else{
              System.out.println(" Status: Hidden From Users");
            }
            
          }
          break;
        }
        case '*': {
          ArrayList<Database.RowData> res = db.selectAll();
          if (res == null)
            continue;
          System.out.println(" Current Database Contents");
          System.out.println(" -------------------------");
          String printfmt = " [%3s] |  %-30s | %-40s | [%3s] | [%3s]%n";
          System.out.printf(printfmt, "Id", "Subject", "Message", "User", "Status");
          System.out.println("--------------------------------------------------------------------------------------------------");
          for (Database.RowData rd : res) {
            String subject = rd.mSubject.length() > 30 ? rd.mSubject.substring(0, 30) : rd.mSubject;
            String message = rd.mMessage.length() > 40 ? rd.mMessage.substring(0, 40) : rd.mMessage;
            System.out.printf(printfmt, rd.mId, subject, message, rd.uID, rd.status);
          }
            break;
        }
        case '-': {
          int id = getInt(in, "Enter the message ID");
          if (id == -1)
            continue;
          int res = db.deleteRow(id);
          if (res == -1)
            continue;
          System.out.println(" " + res + " rows deleted");
          break;
        }
        case '+': {
          String subject = getString(in, "Enter the subject");
          if(subject.length() > 100){
            System.out.println("Subject length too long (must not exceed 100 characters)");
            break;
          }
          String message = getString(in, "Enter the message");
          if(message.length() > 1024){
            System.out.println("Message length too long (must not exceed 1024 characters)");
            break;
          }
          if (subject.equals("") || message.equals(""))
            continue;
          int user = getInt(in, "Enter the User ID");
          if (user == -1)
            continue;
          int res = db.insertRow(subject, message, user);
          System.out.println(res + " rows added");
          break;
        }
        case '~': {
          int id = getInt(in, "Enter the row ID");
          if (id == -1)
            continue;
          String newMessage = getString(in, "Enter the new message");
          int res = db.updateOne(id, newMessage);
          if (res == -1)
            continue;
          System.out.println(" " + res + " rows updated");
          break;
        }
        case 'h': {
          int id = getInt(in, "Enter the row ID");
          if (id == -1)
            continue;
          int res = db.toggleStatus(id);
          if (res == -1)
            continue;
          System.out.println(" " + res + " statuses updated");
          break;
        }
      }
    }
  }
}

