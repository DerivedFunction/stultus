package backend;

import java.util.ArrayList;
import java.util.Map;

import com.google.gson.*;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Default backend App
 */
public class App {

  /**
   * Default port to listen to database
   */
  private static final String DEFAULT_PORT_DB = "5432";

  /**
   * Default port for Spark
   */
  private static final int DEFAULT_PORT_SPARK = 4567;

  /**
   * Parameters for website context
   */
  private static final String CONTEXT = "/messages";

  /**
   * Parameter name for ID in website
   */
  private static final String POST_ID = "postID";

  /**
   * Parameter name for user ID in website
   */
  private static final String USER_ID = "userID";

  /**
   * Parameter name for user ID in website
   */
  private static final String COMMENT_ID = "commentID";

  /**
   * Parameters for basic message with ID in website
   */
  private static final String POST_FORMAT = String.format("%s/:%s", CONTEXT, POST_ID); // "/messages/:postID"

  /**
   * Parameters for basic message with user ID in website
   */
  private static final String USER_FORMAT = String.format("/%s/:%s", "user", USER_ID); // "/user/:userID"

  /**
   * Parameters for adding a basic message with user ID in website
   */
  private static final String ADD_FORMAT = String.format("/%s/addMessage", USER_FORMAT); // "/user/:userID/addMessage"

  /**
   * Parameters for editing a basic message with user ID in website
   */
  private static final String EDIT_FORMAT = String.format("%s/editMessage/:%s", USER_FORMAT, POST_ID); // "/user/:userID/editMessage/:postID"

  /**
   * Parameters for deleting a basic message with user ID in website
   */
  private static final String DELETE_FORMAT = String.format("%s/deleteMessage/:%s", USER_FORMAT, POST_ID); // "/user/:userID/deleteMessage/:postID"

  /**
   * Parameters for basic voting with user ID and post ID in website
   */
  private static final String UPVOTE_FORMAT = String.format("%s/upvote/:%s", USER_FORMAT, POST_ID); // "/user/:userID/upvote/:postID"

  /**
   * Parameters for basic voting with user ID and post ID in website
   */
  private static final String DOWNVOTE_FORMAT = String.format("%s/downvote/:%s", USER_FORMAT, POST_ID); // "/user/:userID/downvote/:postID"

  /**
   * Parameters for getting all comments for a specific post
   */
  private static final String GET_POST_COMMENT_FORMAT = String.format("%s/comments", POST_FORMAT); // "/messages/:postID/comments"

  /**
   * Parameters for getting all comments made by a user for a specific post
   */
  private static final String GET_USER_COMMENTS_POSTS_FORMAT = String.format("%s/:%s",
      GET_POST_COMMENT_FORMAT, USER_ID); // "/messages/:postID/comments/:userID"

  /**
   * Parameters for getting all coments made a specific user
   */
  private static final String GET_USER_COMMENTS_FORMAT = String.format("%s/comments", USER_FORMAT); // "/user/:userID/comments"
  /**
   * Parameters for adding a comment
   */
  private static final String ADD_COMMENT_FORMAT = String.format("%s/addComment/:%s", USER_FORMAT, POST_ID); // "/user/:userID/addComment/:postID"

  /**
   * Parameters for getting a single comment
   */
  private static final String GET_SINGLE_COMMENT_FORMAT = String.format("/comment/:%s", COMMENT_ID); // "/comment/:commentID"
  /**
   * The redirect parameter
   */
  private static final String AUTH_FORMAT = "/authenticate";

  /**
   * deprecated method: parameters for like in website
   */
  private static final String LIKE_PARAM = "like";

  /**
   * the list of all SQL table names to use
   */
  private static ArrayList<String> dbElements = dbTableElements();

  /**
   * Method to get SQL table names
   * 
   * @return ArrayList of table names.
   */
  private static ArrayList<String> dbTableElements() {
    ArrayList<String> ret = new ArrayList<>();
    ret.add("tblData");
    ret.add("likeData");
    ret.add("userData");
    ret.add("commentData");
    return ret;
  }

  /**
   * Default constructor
   */
  public App() {

  }

  /**
   * main method run by the JVM in the Dokku Container
   * reads important information from environment variables, not from arguments
   * 
   * @param args does nothing
   */
  public static void main(String[] args) {
    /**
     * gson allows conversion between JSON and objects. Must be final
     */
    final Gson gson = new Gson();

    Database db = getDatabaseConnection(dbElements);
    if (db == null)
      return;

    // Set the port on which to listen for requests from the environment
    Spark.port(getIntFromEnv("PORT", DEFAULT_PORT_SPARK));
    // Set up the location for serving static files
    String static_location_override = System.getenv("STATIC_LOCATION");
    if (static_location_override == null) {
      Spark.staticFileLocation("/web");
    } else {
      Log.info("New location: " + static_location_override);
      Spark.staticFiles.externalLocation(static_location_override);
    }
    // if CORS enablesd, set backend to accept foriegn requests
    if ("True".equalsIgnoreCase(System.getenv("CORS_ENABLED"))) {
      final String acceptCrossOriginRequestsFrom = "*";
      final String acceptedCrossOriginRoutes = "GET,PUT,POST,DELETE,OPTIONS";
      final String supportedRequestHeaders = "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin";
      enableCORS(acceptCrossOriginRequestsFrom, acceptedCrossOriginRoutes, supportedRequestHeaders);
    }

    // Set up route for serving main page
    Spark.get("/", (req, res) -> {
      res.redirect("/index.html");
      return "";
    });

    /*
     * GET route that returns all messages and ids.
     * Converts StructuredResponses to JSON
     */
    Spark.get(CONTEXT, getAll(gson, db)); // "/messages"

    /*
     * GET route that returns message with specific id.
     * Converts StructuredResponses to JSON
     */
    Spark.get(POST_FORMAT, getWithId(gson, db)); // "/messages/:postID"

    /*
     * GET route that returns user information.
     * Converts StructuredResponses to JSON
     */
    Spark.get(USER_FORMAT, getUser(gson, db)); // "/user/:userID"

    /**
     * GET route that returns all comments for specific post
     */
    Spark.get(GET_POST_COMMENT_FORMAT, getCommentsForPost(gson, db, false, true)); // "/messages/:postID/comments"

    /**
     * GET route that returns all comments for specific post by a user
     */
    Spark.get(GET_USER_COMMENTS_POSTS_FORMAT, getCommentsForPost(gson, db, true, true)); // "/messages/:postID/comments/:userID"

    /**
     * GET route that returns all comments made by a user
     */
    Spark.get(GET_USER_COMMENTS_FORMAT, getCommentsForPost(gson, db, true, false)); // "/user/:userID/comments"

    /**
     * GET route that returns a comments by commentID
     */
    Spark.get(GET_SINGLE_COMMENT_FORMAT, getCommentsForPost(gson, db, false, false));
    /*
     * POST route that adds a new element to DataStore.
     * Reads JSON from body of request and turns it to a
     * SimpleRequest object, extracting the title and msg,
     * and also the object.
     */
    Spark.post(ADD_FORMAT, postIdea(gson, db)); // "/user/:userID/addMessage"

    /*
     * POST route that authenticates a token
     */
    Spark.post(AUTH_FORMAT, authenticateToken(gson, db)); // "/authenticate"

    Spark.post(ADD_COMMENT_FORMAT, postComment(gson, db)); // "/user/:userID/addComment/:postID"
    /*
     * PUT route for updating a row in DataStore. Almost the same
     * as POST
     */
    Spark.put(EDIT_FORMAT, putWithID(gson, db)); // "/user/:userID/editMessage/:postID"

    /*
     * PUT route for voting
     */
    Spark.put(UPVOTE_FORMAT, putUpVote(gson, db)); // "/user/:userID/upvote/:postID"
    /*
     * PUT route for voting
     */
    Spark.put(DOWNVOTE_FORMAT, putDownVote(gson, db)); // "/user/:userID/downvote/:postID"

    /*
     * Delete route for removing a row from DataStore
     */
    Spark.delete(DELETE_FORMAT, deleteWithID(gson, db)); // "/user/:userID/deleteMessage/:postID"

    // Old methods
    /*
     * POST route that adds a new element to DataStore.
     * Reads JSON from body of request and turns it to a
     * SimpleRequest object, extracting the title and msg,
     * and also the object.
     * 
     */
    Spark.post(CONTEXT, postIdea_old(gson, db)); // "/messages"

    /*
     * PUT route for updating a row in DataStore. Almost the same
     * as POST
     */
    Spark.put(POST_FORMAT, putWithID_old(gson, db)); // "/messages/:postID"

    /*
     * PUT route for adding a like,
     */
    Spark.put(String.format("%s/%s", POST_FORMAT, LIKE_PARAM), putLike(gson, db)); // "/messages/:postID/like"

    /*
     * Delete route for removing a row from DataStore
     */
    Spark.delete(POST_FORMAT, deleteWithID_old(gson, db)); // "/messages/:postID"

  }

  /**
   * Creates the route to handle delete requests
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @deprecated In favor of deleting with userID verification
   *             {@link #deleteWithID(Gson, Database)}
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.deleteOne
   */
  private static Route deleteWithID_old(final Gson gson, Database db) {
    return (req, res) -> {
      int postID = Integer.parseInt(req.params(POST_ID));
      extractResponse(res);
      int result = db.deleteRow(postID, 1);
      String errorType = "unable to delete row " + postID;
      boolean errResult = (result == -1);
      return JSONResponse(gson, errorType, errResult, null, null);
    };
  }

  /**
   * Creates the route to handle delete requests
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.deleteOne
   */
  private static Route deleteWithID(final Gson gson, Database db) {
    return (req, res) -> {
      int postID = Integer.parseInt(req.params(POST_ID));
      int userID = Integer.parseInt(req.params(USER_ID));
      extractResponse(res);
      int result = db.deleteRow(postID, userID);
      String errorType = "unable to delete row " + postID;
      boolean errResult = (result == -1);
      return JSONResponse(gson, errorType, errResult, null, null);
    };
  }

  /**
   * Creates the route to handle content changes
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @deprecated In favor of editing with userID verification
   *             {@link #putWithID(Gson, Database)}
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.updatedOne
   */
  private static Route putWithID_old(final Gson gson, Database db) {
    return (req, res) -> {
      int postID = Integer.parseInt(req.params(POST_ID));
      SimpleRequest sReq = gson.fromJson(req.body(), SimpleRequest.class);
      extractResponse(res);
      Integer result = db.updateOne(postID, sReq.mTitle, sReq.mMessage, 1);
      String errorType = "unable to update row " + postID;
      boolean errResult = (result < 1);
      return JSONResponse(gson, errorType, errResult, null, result);
    };
  }

  /**
   * Creates the route to handle content changes with USER id
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.updatedOne
   */
  private static Route putWithID(final Gson gson, Database db) {
    return (req, res) -> {
      int postID = Integer.parseInt(req.params(POST_ID));
      int userID = Integer.parseInt(req.params(USER_ID));
      SimpleRequest sReq = gson.fromJson(req.body(), SimpleRequest.class);
      extractResponse(res);
      Integer result = db.updateOne(postID, sReq.mTitle, sReq.mMessage, userID);
      String errorType = "unable to update row " + postID;
      boolean errResult = (result < 1);
      return JSONResponse(gson, errorType, errResult, null, result);
    };
  }

  /**
   * Creates the route to handle like changes
   * 
   * @deprecated As of Sprint 8, this method is deprecated in favor of
   *             {@link #putUpVote(Gson, Database)}
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.togglelike
   */
  private static Route putLike(final Gson gson, Database db) {
    return (req, res) -> {
      int postID = Integer.parseInt(req.params(POST_ID));
      int result = db.toggleLike(postID);
      String errorType = "error performing like";
      boolean errResult = (result == -1);
      return JSONResponse(gson, errorType, errResult, null, null);
    };
  }

  /**
   * Creates the route to handle voting
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.togglelike
   */
  private static Route putUpVote(final Gson gson, Database db) {
    return (req, res) -> {
      int postID = Integer.parseInt(req.params(POST_ID));
      int userID = Integer.parseInt(req.params(USER_ID));
      int result = db.toggleVote(postID, 1, userID);
      String errorType = "error updating vote: post id=" + postID + " vote=" + 1;
      boolean errResult = (result == -1);
      return JSONResponse(gson, errorType, errResult, null, null);
    };
  }

  /**
   * Creates the route to handle voting
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.togglelike
   */
  private static Route putDownVote(final Gson gson, Database db) {
    return (req, res) -> {
      int postID = Integer.parseInt(req.params(POST_ID));
      int userID = Integer.parseInt(req.params(USER_ID));
      int result = db.toggleVote(postID, -1, userID);
      String errorType = "error updating vote: post id=" + postID + " vote=" + -1;
      boolean errResult = (result == -1);
      return JSONResponse(gson, errorType, errResult, null, null);
    };
  }

  /**
   * Creates the route to handle post requests
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @deprecated In favor of adding a post with userID verification
   *             {@link #postIdea(Gson, Database)}
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.insertRow
   */
  private static Route postIdea_old(final Gson gson, Database db) {
    return (req, res) -> {
      SimpleRequest sReq = gson.fromJson(req.body(), SimpleRequest.class);
      extractResponse(res);
      // createEntry checks for null title/message (-1)
      int rowsAdded = db.insertRow(sReq.mTitle, sReq.mMessage, 1);
      String errorType = "error performing insertion";
      boolean errResult = (rowsAdded <= 0);
      String message = "" + rowsAdded;
      return JSONResponse(gson, errorType, errResult, message, null);
    };
  }

  /**
   * Creates the route to handle post requests with userID
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.insertRow
   */
  private static Route postIdea(final Gson gson, Database db) {
    return (req, res) -> {
      int id = Integer.parseInt(req.params(USER_ID));
      SimpleRequest sReq = gson.fromJson(req.body(), SimpleRequest.class);
      extractResponse(res);
      // createEntry checks for null title/message (-1)
      int rowsAdded = db.insertRow(sReq.mTitle, sReq.mMessage, id);
      String errorType = "error performing insertion";
      boolean errResult = (rowsAdded <= 0);
      String message = "" + rowsAdded;
      return JSONResponse(gson, errorType, errResult, message, null);
    };
  }

  /**
   * Creates the route to handle post comment requests with userID
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.insertRow
   */
  private static Route postComment(final Gson gson, Database db) {
    return (request, response) -> {
      int postID = Integer.parseInt(request.params(POST_ID));
      int userID = Integer.parseInt(request.params(USER_ID));
      SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
      extractResponse(response);

      int commentsAdded = db.insertComment(req.mMessage, postID, userID);
      String errorType = String.format("Error inserting comment to postID %d by userID: %d",
          postID, userID);
      boolean errResult = (commentsAdded <= 0);
      String message = String.format("Succesfully inserted %d comment to postID %d by userID: %d",
          commentsAdded, postID, userID);
      return JSONResponse(gson, errorType, errResult, message, null);
    };
  }

  /**
   * Creates the route to handle get requests that do not give a specific id
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json behavior for
   *         db.selectAll()
   */
  private static Route getAll(final Gson gson, Database db) {
    return (request, response) -> {
      extractResponse(response);
      return gson.toJson(
          new StructuredResponse("ok", null, db.selectAll()));
    };
  }

  /**
   * Creates the route to handle get requests that give a specific id
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json behavior for
   *         db.selectOne()
   */
  private static Route getWithId(final Gson gson, Database db) {
    return (req, res) -> {
      int postID = Integer.parseInt(req.params(POST_ID));
      extractResponse(res);
      PostData data = db.selectOne(postID);
      String errorType = postID + " not found";
      boolean errResult = (data == null);
      String message = null;
      return JSONResponse(gson, errorType, errResult, message, data);
    };
  }

  /**
   * Creates the route to handle get requests that give a specific id
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json behavior for
   *         db.selectOne()
   */
  private static Route getUser(final Gson gson, Database db) {
    return (req, res) -> {
      int userID = Integer.parseInt(req.params(USER_ID));
      extractResponse(res);
      UserDataLite data = db.getUserSimple(userID);
      String errorType = userID + " not found";
      boolean errResult = (data == null);
      String message = null;
      return JSONResponse(gson, errorType, errResult, message, data);
    };
  }

  /**
   * Creates the route to handle get requests that do not give a specific id
   * 
   * @param gson      Gson object that handles shared serialization
   * @param db        Database object to execute the method of
   * @param needsUser If a specific userID is needed
   * @param needsPost If a specific postID is needed
   * @return Returns a spark Route object that handles the json behavior for
   *         comments
   * 
   */
  private static Route getCommentsForPost(final Gson gson, Database db, boolean needsUser, boolean needsPost) {
    return (req, res) -> {
      int postID = needsPost ? 0 : Integer.parseInt(req.params(POST_ID));
      int userID = needsUser ? 0 : Integer.parseInt(req.params(USER_ID));

      extractResponse(res);
      // Needs both of them
      if (needsUser && needsPost) {
        return gson.toJson(
            new StructuredResponse("ok", null, db.selectAllComments(userID, postID)));
      } else if (needsPost && !needsUser) { // Only needs postID
        return gson.toJson(
            new StructuredResponse("ok", null, db.selectAllCommentByPost(postID)));
      } else if (!needsPost && needsUser) { // Only needs userID
        return gson.toJson(
            new StructuredResponse("ok", null, db.selectAllCommentByUserID(userID)));
      } else { // needs neither postID nor userID, so get the commentID
        int commentID = Integer.parseInt(req.params(COMMENT_ID));
        return gson.toJson(
            new StructuredResponse("ok", null, db.selectComment(commentID)));
      }
    };
  }

  /**
   * Authenticates Token via `Oauth.java`
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json behavior
   * 
   */
  private static Route authenticateToken(final Gson gson, Database db) {
    return (req, res) -> {
      String idToken = req.queryParams("credential"); // Assuming the ID token is sent as a query parameter
      boolean verified = idToken != null && Oauth.verifyToken(idToken);
      String email = null;
      String name = null;
      int userID = 0;
      if (verified) {
        // Token is valid, extract email from payload
        email = Oauth.getEmail(idToken);
        name = Oauth.getName(idToken);
        // Checks if user exists in Database
        UserData user = db.getUserFull(db.findUserID(email));
        if (user == null) { // creating a new user account
          Log.info("new account detected creating new user");
          db.insertUser(name, email);
        }
        userID = db.findUserID(email);
        if (TokenManager.getToken(userID) != null) {
          Log.info("User has already logged in. Deleting old credentials");
          TokenManager.removeToken(userID);
        }
        TokenManager.addToken(userID, idToken);
        Log.info("Added new token to TokenManager");
      } else {
        // Token is invalid or missing
        res.status(401); // Unauthorized status code
      }
      String errorType = "Invalid or missing ID token: " + idToken;
      boolean errResult = !verified;
      UserData user = db.getUserFull(db.findUserID(email));
      return JSONResponse(gson, errorType, errResult, idToken, user);
    };
  }

  /**
   * Response on success
   * 
   * @param response 200 OK
   */
  private static void extractResponse(Response response) {
    response.status(200);
    response.type("application/json");
  }

  /**
   * Returns JSON response on error or OK
   * 
   * @param gson      GSON to convert to JSON
   * @param errorType Error Message
   * @param errResult Evaluation of result
   * @param message   mMessage for JSON on OK
   * @param data      mData for JSON on OK
   * @return JSON response
   */
  private static Object JSONResponse(final Gson gson, String errorType, boolean errResult, String message,
      Object data) {
    if (errResult) {
      return gson.toJson(new StructuredResponse(
          "error", errorType, data));
    } else {
      return gson.toJson(new StructuredResponse("ok", message, data));
    }
  }

  /**
   * Gets database connect using enviroment variables
   * 
   * @param table name of the main message table
   * @return connected database
   */
  public static Database getDatabaseConnection(ArrayList<String> table) {
    if (System.getenv("DATABASE_URL") != null) {
      return Database.getDatabase(System.getenv("DATABASE_URL"), DEFAULT_PORT_DB, table);
    }
    // get the Postgres configuration from the environment
    Map<String, String> env = System.getenv();
    String ip = env.get("POSTGRES_IP");
    String port = env.get("POSTGRES_PORT");
    String user = env.get("POSTGRES_USER");
    String pass = env.get("POSTGRES_PASS");

    // Connect to database
    return Database.getDatabase(ip, port, "", user, pass, table);
  }

  /**
   * get an integer environment variable if it exists, and otherwise return the
   * default value.
   * 
   * @param envar      The name of the environment variable to get.
   * @param defaultVal The integer value to use as the default if envar isn't
   *                   found
   * 
   * @return The best answer we could come up with for a value for envar
   */
  static int getIntFromEnv(String envar, int defaultVal) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get(envar) != null) {
      return Integer.parseInt(processBuilder.environment().get(envar));
    }
    return defaultVal;
  }

  /**
   * Set up CORS headers for the OPTIONS verb, and for every response that the
   * server sends. This only needs to be called once.
   * 
   * @param origin  The server that is allowed to send requests to this server
   * @param methods The allowed HTTP verbs from the above origin
   * @param headers The headers that can be sent with a request from the above
   *                origin
   */
  private static void enableCORS(String origin, String methods, String headers) {
    // Create an OPTIONS route that reports the allowed CORS headers and methods
    Spark.options("/*", (request, response) -> {
      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }
      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }
      return "OK";
    });

    /**
     * 'before' is a decorator, which will run before any
     * get/post/put/delete. In our case, it will put three extra CORS
     * headers into the response
     */
    Spark.before((request, response) -> {
      response.header("Access-Control-Allow-Origin", origin);
      response.header("Access-Control-Request-Method", methods);
      response.header("Access-Control-Allow-Headers", headers);
    });

  }

}