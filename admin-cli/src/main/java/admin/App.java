package admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

/**
 * App is our basic admin app. For now, it is a demonstration of the six key
 * operations on a database: connect, insert, update, query, delete, disconnect
 */
public class App {

  /**
   * Print the menu for our program
   */
  static void menu() {
    System.out.println("Main Menu");
    System.out.println("  [T] Create tblData");
    System.out.println("  [D] Drop tblData");
    System.out.println("  [1] Query for a specific row");
    System.out.println("  [*] Query for all rows");
    System.out.println("  [-] Delete a row");
    System.out.println("  [+] Insert a new row");
    System.out.println("  [~] Update a row");
    System.out.println("  [q] Quit Program");
    System.out.println("  [?] Help (this message)");
  }

  /**
   * Ask the user to enter a menu option; repeat until we get a valid
   * option.
   * 
   * @param in A BufferedReader to read input from keyboard
   * @return the character corresponding to the chosen menu option
   */
  static char prompt(BufferedReader in) {
    // valid actions
    String actions = "TD1*-+~q?";
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
   * Ask the user to enter a String message
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
   * As the user to enter an int
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
        case '?':
          menu();
          break;
        case 'q':
          cont = false;
          break;
        case 'T':
          db.createTable();
          break;
        case 'D':
          db.dropTable();
          break;
        case '1': {
          int id = getInt(in, "Enter the row ID");
          if (id == -1)
            continue;
          Database.RowData res = db.selectOne(id);
          if (res != null) {
            System.out.println(" [" + res.mId + "] " + res.mSubject);
            System.out.println(" Message: " + res.mMessage);
            System.out.println(" Likes: " + res.mLikes);
          }
          break;
        }
        case '*': {
          ArrayList<Database.RowData> res = db.selectAll();
          if (res == null)
            continue;
          System.out.println(" Current Database Contents");
          System.out.println(" -------------------------");
          String printfmt = " [%3s] |  %-30s | %-40s | [%3s]%n";
          System.out.printf(printfmt, "Id", "Subject", "Message", "Likes");
          System.out.println("--------------------------------------------------------------------------------------------------");
          for (Database.RowData rd : res) {
            String subject = rd.mSubject.length() > 30 ? rd.mSubject.substring(0, 30) : rd.mSubject;
            String message = rd.mMessage.length() > 40 ? rd.mMessage.substring(0, 40) : rd.mMessage;
            System.out.printf(printfmt, rd.mId, subject, message, rd.mLikes);
          }
            break;
        }
        case '-': {
          int id = getInt(in, "Enter the row ID");
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
          if(message.length() > 2048){
            System.out.println("Message length too long (must not exceed 2048 characters)");
            break;
          }
          if (subject.equals("") || message.equals(""))
            continue;
          int res = db.insertRow(subject, message);
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
      }
    }
    db.disconnect();
  }
}

