package backend;

import java.util.ArrayList;
import java.util.Map;

import com.google.gson.*;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Default backend App
 */
public class App {

  private static final String LOGIN_HTML = "/login.html";

  private static final String HOME_HTML = "/home.html";

  /**
   * string to have response to use JSON
   */
  private static final String APPLICATION_JSON = "application/json";

  /**
   * Whether or not a user needs to be verified to do certain actions.
   * Note: some methods will still need the cookies to perform certain actions
   */
  private static final boolean NEED_AUTH = Integer.parseInt(System.getenv("AUTH")) == 1;
  /**
   * Default port to listen to database
   */
  private static final String DEFAULT_PORT_DB = "5432";

  /**
   * Default port for Spark
   */
  private static final int DEFAULT_PORT_SPARK = 4567;

  /**
   * idToken Cookie
   */
  private static final String ID_TOKEN = "idToken";

  /**
   * sub value Cookie
   */
  private static final String SUB_TOKEN = "sub";

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
  private static final String USER_FORMAT = "/user"; // "/user"

  /**
   * Parameters for basic message with user ID in website
   */
  private static final String USER_ID_FORMAT = String.format("%s/:%s", USER_FORMAT, USER_ID); // "/user/:userID"

  /**
   * Parameters for adding a basic message with user ID in website
   */
  private static final String ADD_FORMAT = String.format("%s/addMessage", USER_FORMAT); // "/user/addMessage"

  /**
   * Parameters for editing a basic message with user ID in website
   */
  private static final String EDIT_FORMAT = String.format("%s/editMessage/:%s", USER_FORMAT, POST_ID); // "/user/editMessage/:postID"

  /**
   * Parameters for deleting a basic message with user ID in website
   */
  private static final String DELETE_FORMAT = String.format("%s/deleteMessage/:%s", USER_FORMAT, POST_ID); // "/user/deleteMessage/:postID"

  /**
   * Parameters for basic voting with user ID and post ID in website
   */
  private static final String UPVOTE_FORMAT = String.format("%s/upvote/:%s", USER_FORMAT, POST_ID); // "/user/upvote/:postID"

  /**
   * Parameters for basic voting with user ID and post ID in website
   */
  private static final String DOWNVOTE_FORMAT = String.format("%s/downvote/:%s", USER_FORMAT, POST_ID); // "/user/downvote/:postID"

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
  private static final String GET_USER_COMMENTS_FORMAT = String.format("%s/comments", USER_ID_FORMAT); // "/user/:userID/comments"

  /**
   * Parameters for adding a comment
   */
  private static final String COMMENT_FORMAT = String.format("%s/comment/:%s", USER_FORMAT, POST_ID); // "/user/comment/:postID"

  /**
   * Parameters for editing a comment
   */
  private static final String EDIT_COMMENT_FORMAT = String.format("%s/editComment/:%s", USER_FORMAT, COMMENT_ID); // "/user/editComment/:commentID"

  /**
   * Parameters for getting a single comment
   */
  private static final String SINGLE_COMMENT_FORMAT = String.format("/comment/:%s", COMMENT_ID); // "/comment/:commentID"

  /**
   * The login parameter
   */
  private static final String AUTH_FORMAT = "/authenticate";

  /**
   * The logout parameter
   */
  private static final String LOGOUT_FORMAT = "/logout";

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
    // Only one app at a time
  }

  /**
   * main method run by the JVM in the Dokku Container
   * reads important information from environment variables, not from arguments
   * 
   * @param args does nothing
   */
  public static void main(String[] args) {
    Log.info("Verification is set to " + NEED_AUTH);
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
    String staticLocationOverride = System.getenv("STATIC_LOCATION");
    if (staticLocationOverride == null) {
      Spark.staticFileLocation("/web");
    } else {
      Log.info("New location: " + staticLocationOverride);
      Spark.staticFiles.externalLocation(staticLocationOverride);
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
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {
        res.redirect(LOGIN_HTML);
      }
      res.redirect(HOME_HTML);
      return "";
    });

    /*
     * GET route that returns all messages and ids.
     * Converts StructuredResponses to JSON
     */
    Spark.get(CONTEXT, getAllIdeas(gson, db)); // "/messages"

    /*
     * GET route that returns message with specific id.
     * Converts StructuredResponses to JSON
     */
    Spark.get(POST_FORMAT, getIdea(gson, db)); // "/messages/:postID"

    /*
     * GET route that returns user information.
     * Converts StructuredResponses to JSON
     */
    Spark.get(USER_ID_FORMAT, getUser(gson, db)); // "/user/:userID"
    /*
     * GET route that returns current user information.
     * Converts StructuredResponses to JSON
     */
    Spark.get(USER_FORMAT, getUserFull(gson, db)); // "/user"

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
    Spark.get(SINGLE_COMMENT_FORMAT, getCommentsForPost(gson, db, false, false)); // "/comment/:commentID"
    /*
     * POST route that adds a new element to database.
     * Reads JSON from body of request and turns it to a
     * SimpleRequest object, extracting the title and msg,
     * and also the object.
     */
    Spark.post(ADD_FORMAT, postIdea(gson, db)); // "/user/addMessage"

    /**
     * POST route that adds a new comment
     */
    Spark.post(COMMENT_FORMAT, postComment(gson, db)); // "/user/comment/:postID"

    /*
     * POST route for voting
     */
    Spark.post(UPVOTE_FORMAT, postUpVote(gson, db)); // "/user/upvote/:postID"

    /*
     * POST route for voting
     */
    Spark.post(DOWNVOTE_FORMAT, postDownVote(gson, db)); // "/user/downvote/:postID"

    /*
     * PUT route for updating a row in database.
     */
    Spark.put(EDIT_FORMAT, putIdea(gson, db)); // "/user/editMessage/:postID"

    /*
     * PUT route for updating a comment in database.
     */
    Spark.put(EDIT_COMMENT_FORMAT, putComment(gson, db)); // "/user/editComment/:postID"

    /*
     * PUT route for updating a user in database.
     */
    Spark.put(USER_FORMAT, putUser(gson, db)); // "/user"

    /*
     * Delete route for removing a row from database
     */
    Spark.delete(DELETE_FORMAT, deleteIdea(gson, db)); // "/user/deleteMessage/:postID"

    /*
     * POST route that authenticates a token
     */
    Spark.post(AUTH_FORMAT, authenticateToken(gson, db)); // "/authenticate"
    /**
     * DELETE route that signs a user out
     */
    Spark.delete(LOGOUT_FORMAT, logout(db)); // "/logout"

  }

  /**
   * Creates the route to delete ideas
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.deleteRow
   */
  private static Route deleteIdea(final Gson gson, Database db) {
    return (req, res) -> {
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {

        return unAuthJSON(gson, res);
      }
      int userID = getUserIDfromCookie(req);
      int postID = getPostID(req);
      int result = db.deleteRow(postID, userID);
      return getJSONResponse(gson,
          String.format("unable to delete post %d by user %d", postID, userID), (result <= 0),
          String.format("post %d was deleted by user %d", postID, userID), null, res);
    };
  }

  /**
   * Gets postID parameter from request
   * 
   * @param req Request
   * @return postID
   */
  private static int getPostID(Request req) {
    return Integer.parseInt(req.params(POST_ID));
  }

  /**
   * Gets userID parameter from ID_TOKEN cookie
   * 
   * @param req Request
   * @return userID
   */
  private static int getUserIDfromCookie(Request req) {
    return TokenManager.getUserID(req.cookie(ID_TOKEN));
  }

  /**
   * Gets commentID parameter from request
   * 
   * @param req Request
   * @return commentID
   */
  private static int getCommentID(Request req) {
    return Integer.parseInt(req.params(COMMENT_ID));
  }

  /**
   * Return a JSON response stating unauthorized access
   * 
   * @param gson Gson object that handles shared serialization
   * @param res  Response
   * @return JSON response
   */
  private static String unAuthJSON(final Gson gson, Response res) {
    res.type(APPLICATION_JSON);
    res.status(401); // Unauthorized
    return gson.toJson(new StructuredResponse("err", "Unauthorized User", null));
  }

  /**
   * Creates the route to handle content changes with post id
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.updatedOne()
   */
  private static Route putIdea(final Gson gson, Database db) {
    return (req, res) -> {
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {
        return unAuthJSON(gson, res);
      }

      int userID = getUserIDfromCookie(req);
      int postID = getPostID(req);
      SimpleRequest sReq = gson.fromJson(req.body(), SimpleRequest.class);
      Integer result = db.updateOne(postID, sReq.mTitle, sReq.mMessage, userID);
      return getJSONResponse(gson,
          String.format("unable to update post %d by user %d", postID, userID), (result < 1),
          String.format("post %d was updated by user %d", postID, userID),
          result, res);
    };
  }

  /**
   * Creates the route to handle user profile changes
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.updateUser()
   */
  private static Route putUser(final Gson gson, Database db) {
    return (req, res) -> {
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {

        return unAuthJSON(gson, res);
      }
      String sub = req.cookie(SUB_TOKEN);
      int userID = getUserIDfromCookie(req);
      UserData profile = gson.fromJson(req.body(), UserData.class);
      Integer result = db.updateUser(userID, sub, profile.uUsername, profile.uGender, profile.uSO, profile.uNote);
      return getJSONResponse(gson,
          String.format("unable to update user: %s", profile.toString()), (result < 1),
          String.format("user was updated: %s", db.getUserFull(userID)),
          result, res);
    };
  }

  /**
   * Creates the route to handle comment changes
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.updateComment()
   */
  private static Route putComment(final Gson gson, Database db) {
    return (req, res) -> {
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {

        return unAuthJSON(gson, res);
      }
      int userID = getUserIDfromCookie(req);
      int commentID = getCommentID(req);
      SimpleRequest sReq = gson.fromJson(req.body(), SimpleRequest.class);
      Integer result = db.updateComment(sReq.mMessage, commentID, userID);
      return getJSONResponse(gson,
          String.format("unable to update comment %d by user %d", commentID, userID), (result < 1),
          String.format("comment %d was updated by user %d", commentID, userID), result, res);
    };
  }

  /**
   * Creates the route to handle upvoting
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.toggleVote()
   */
  private static Route postUpVote(final Gson gson, Database db) {
    return (req, res) -> {

      // Verify the token
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {

        return unAuthJSON(gson, res);
      }
      int userID = getUserIDfromCookie(req);
      int postID = getPostID(req);
      int result = db.toggleVote(postID, 1, userID);
      return getJSONResponse(gson,
          String.format("cannot upvote post %d by user %d", postID, userID), (result < 1),
          String.format("upvoted post %d by user %d", postID, userID), result, res);
    };
  }

  /**
   * Creates the route to handle downvoting
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.toggleVote()
   */
  private static Route postDownVote(final Gson gson, Database db) {
    return (req, res) -> {
      // Verify the token
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {

        return unAuthJSON(gson, res);
      }
      int userID = getUserIDfromCookie(req);
      int postID = getPostID(req);
      int result = db.toggleVote(postID, -1, userID);
      return getJSONResponse(gson,
          String.format("cannot downvote post %d by user %d", postID, userID), (result < 1),
          String.format("downvoted post %d by user %d", postID, userID), result, res);
    };
  }

  /**
   * Creates the route to handle post idea requests with userID
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.insertRow()
   */
  private static Route postIdea(final Gson gson, Database db) {
    return (req, res) -> {
      // Verify the token
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {

        return unAuthJSON(gson, res);
      }
      int userID = getUserIDfromCookie(req);
      SimpleRequest sReq = gson.fromJson(req.body(), SimpleRequest.class);
      // createEntry checks for null title/message (-1)
      int rowsAdded = db.insertRow(sReq.mTitle, sReq.mMessage, userID);
      return getJSONResponse(gson,
          String.format("error adding a post by user %d", userID), (rowsAdded < 1),
          String.format("user %d added %d post", userID, rowsAdded), rowsAdded, res);
    };
  }

  /**
   * Creates the route to handle post comment requests with userID
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.insertComment()
   */
  private static Route postComment(final Gson gson, Database db) {
    return (req, res) -> {
      // Verify the token
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {

        return unAuthJSON(gson, res);
      }
      int userID = getUserIDfromCookie(req);
      int postID = getPostID(req);
      SimpleRequest sReq = gson.fromJson(req.body(), SimpleRequest.class);
      int commentsAdded = db.insertComment(sReq.mMessage, postID, userID);
      return getJSONResponse(gson,
          String.format("error adding comment to post %d by user %d", postID, userID), (commentsAdded < 1),
          String.format("added %d comment to post %d by user %d", commentsAdded, postID, userID),
          commentsAdded, res);
    };
  }

  /**
   * Creates the route to handle get all ideas requests
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json behavior for
   *         db.selectAll()
   */
  private static Route getAllIdeas(final Gson gson, Database db) {
    return (req, res) -> {
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {

        return unAuthJSON(gson, res);
      }
      return getJSONResponse(gson, "Invalid or missing ID token", !verified,
          "Successfully retreived all messages", db.selectAll(), res);
    };
  }

  /**
   * Creates the route to handle get idea requests that give a specific id
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json behavior for
   *         db.selectOne()
   */
  private static Route getIdea(final Gson gson, Database db) {
    return (req, res) -> {
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {

        return unAuthJSON(gson, res);
      }
      int postID = getPostID(req);
      PostData data = db.selectOne(postID);
      return getJSONResponse(gson,
          String.format("post %d not found", postID), (data == null),
          String.format("post %d found", postID), data, res);
    };
  }

  /**
   * Creates the route to handle get user requests that give a specific id
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json behavior for
   *         db.getUserSimple()
   */
  private static Route getUser(final Gson gson, Database db) {
    return (req, res) -> {
      // Verify the token
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {

        return unAuthJSON(gson, res);
      }
      int userID = Integer.parseInt(req.params(USER_ID));
      UserDataLite data = db.getUserSimple(userID);

      return getJSONResponse(gson,
          String.format("user %d not found", userID), (data == null),
          String.format("user %d found", userID), data, res);
    };
  }

  /**
   * Creates the route to handle get user profile requests
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json behavior for
   *         db.getUserFull()
   */
  private static Route getUserFull(final Gson gson, Database db) {
    return (req, res) -> {
      // Verify the token
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {
        res.status(401); // Unauthorized
        return unAuthJSON(gson, res);
      }
      int userID = getUserIDfromCookie(req);
      UserData data = db.getUserFull(userID);
      return getJSONResponse(gson, "Cannot verify user identity", !verified, "Verified user found", data, res);
    };
  }

  /**
   * Creates the route to handle get comments requests
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
      boolean verified = getVerified(db, req);
      if (!verified) {

        return unAuthJSON(gson, res);
      }
      int postID = needsPost ? getPostID(req) : 0;
      int userId = needsUser ? Integer.parseInt(req.params(USER_ID)) : 0;
      res.type(APPLICATION_JSON);
      res.status(200);
      // Needs both of them
      if (needsUser && needsPost) {
        return getCommentUserPost(gson, db, postID, userId);
      } else if (needsPost && !needsUser) { // Only needs postID
        return getCommentPost(gson, db, postID);
      } else if (!needsPost && needsUser) { // Only needs userID
        return getCommentUser(gson, db, userId);
      } else { // needs neither postID nor userID, so get the commentID
        return getCommentbyID(gson, db, req, res);
      }
    };
  }

  /**
   * Calls to get comment by commentID
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @param req  The request
   * @param res  The response
   * @return returns one comment by commentID
   */
  private static Object getCommentbyID(final Gson gson, Database db, Request req, Response res) {
    try {
      int commentID = getCommentID(req);
      return gson.toJson(new StructuredResponse("ok",
          String.format("Found comment %d", commentID),
          db.selectComment(commentID)));
    } catch (NumberFormatException e) {
      res.type(APPLICATION_JSON);
      res.status(403);
      return gson.toJson(new StructuredResponse(
          "error", "invalid parameters", null));
    }
  }

  /**
   * Calls to get comment by userID
   * 
   * @param gson   Gson object that handles shared serialization
   * @param db     Database object to execute the method of
   * @param userId the user's userID
   * @return all comemnts by user
   */
  private static String getCommentUser(final Gson gson, Database db, int userId) {
    return gson.toJson(
        new StructuredResponse("ok",
            String.format("Found comments for user %d", userId),
            db.selectAllCommentByUserID(userId)));
  }

  /**
   * Calls to get comment by postID
   * 
   * @param gson   Gson object that handles shared serialization
   * @param db     Database object to execute the method of
   * @param postID the post's postID
   * @return all comemnts for post
   */
  private static String getCommentPost(final Gson gson, Database db, int postID) {
    return gson.toJson(
        new StructuredResponse("ok",
            String.format("Found comments for post %d", postID),
            db.selectAllCommentByPost(postID)));
  }

  /**
   * Calls to get comment by user for post
   * 
   * @param gson   Gson object that handles shared serialization
   * @param db     Database object to execute the method of
   * @param postID the post's postID
   * @param userID the user's userID
   * @return all comemnts by user for that post
   */
  private static String getCommentUserPost(final Gson gson, Database db, int postID, int userID) {
    return gson.toJson(
        new StructuredResponse("ok",
            String.format("Found user %d's comments for post %d", userID, postID),
            db.selectAllComments(userID, postID)));
  }

  /**
   * Verifies if the user is valid
   * 
   * @param db  The database to find the user
   * @param req The request to grab cookies
   * @return True if user is verified
   */
  private static boolean getVerified(Database db, Request req) {
    // Don't need authentication, then skip this phase
    // (other phases might still need it though)
    if (!NEED_AUTH)
      return true;
    // Retrieve the value of the ID_TOKEN cookie
    String idToken = req.cookie(ID_TOKEN);
    String sub = req.cookie(SUB_TOKEN);
    if (idToken == null || sub == null)
      return false;

    int userID = TokenManager.getUserID(idToken);
    int userIDSub = db.findUserIDfromSub(sub);
    // Verify the token
    // If it doesn't exist in TokenManager, return error
    return (Oauth.verifyToken(idToken)
        && TokenManager.containsToken(idToken)
        && (userID == userIDSub));
  }

  /**
   * Authenticates Token via Oauth.java
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
      String sub = null;
      int userID = 0;
      if (verified) {
        // Token is valid, extract email from payload
        email = Oauth.getEmail(idToken);
        name = Oauth.getName(idToken);
        sub = Oauth.getSub(idToken);
        Log.info(String.format("A user is attempting to login: %s", email));
        // Checks if user exists in Database
        UserData user = db.getUserFull(db.findUserID(email));
        if (user == null) { // creating a new user account
          Log.info(String.format("Creating acount: %s, %s, %s", name, email, sub));
          db.insertUser(name, email, sub);
        }
        userID = db.findUserIDfromSub(sub);
        if (TokenManager.getToken(userID) != null) {
          Log.info("User has already logged in. Deleting old credentials");
          TokenManager.removeToken(userID);
        }
        TokenManager.addToken(userID, idToken);
        Log.info("Added new token to TokenManager");
        Log.info("Adding new cookies to client");
        res.cookie(ID_TOKEN, idToken);
        res.cookie(SUB_TOKEN, sub);
        res.redirect(HOME_HTML);
      } else {
        // Token is invalid or missing
        res.status(401); // Unauthorized status code
      }
      UserData user = db.getUserFull(db.findUserID(email));
      return getJSONResponse(gson,
          String.format("Invalid or missing ID token: %s", idToken), !verified,
          String.format("ID token authenticated: %s", idToken), user, res);
    };
  }

  /**
   * Logs out a user by clearing its cookies
   * 
   * @param db Database object to execute the method of
   * 
   * @return 200 OK on logout
   */
  private static Route logout(Database db) {
    return (req, res) -> {
      String sub = req.cookie(SUB_TOKEN);
      String idToken = req.cookie(ID_TOKEN);
      int userID = 0;
      if (sub != null) {
        userID = db.findUserIDfromSub(sub);
        Log.info("A user is attempting to log out: " + db.getUserFull(userID).uEmail);
      } else if (idToken != null) {
        userID = db.findUserID(Oauth.getEmail(idToken));
        Log.info("A user is attempting to log out: " + db.getUserFull(userID).uEmail);
      }
      if (userID > 0)
        TokenManager.removeToken(userID);
      res.cookie(ID_TOKEN, "", 0);
      res.cookie(SUB_TOKEN, "", 0);
      // Return a response indicating successful logout
      res.status(200); // OK status code
      return "User logout successful. You can now close this page";
    };
  }

  /**
   * Returns JSON response on error or OK
   * 
   * @param gson      GSON to convert to JSON
   * @param errorMsg  Error Message
   * @param errResult Evaluation of result
   * @param message   mMessage for JSON on OK
   * @param data      mData for JSON on OK
   * @param response  for setting response status
   * @return JSON response
   */
  private static Object getJSONResponse(final Gson gson, String errorMsg, boolean errResult, String message,
      Object data, Response response) {
    response.type(APPLICATION_JSON);
    if (errResult) {
      Log.info(errorMsg);
      response.status(403);
      return gson.toJson(new StructuredResponse(
          "error", errorMsg, data));
    } else {
      Log.info(message);
      response.status(200);
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