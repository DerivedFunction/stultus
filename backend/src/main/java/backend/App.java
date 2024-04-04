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

  /**
   * Whether or not a user needs to be verified to do certain actions.
   * Note: some methods will still need the cookies to perform certain actions
   */
  private static final boolean NEED_AUTH = Integer.parseInt(System.getenv("AUTH")) == 1 ? true : false;
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
  private static final String GET_USER_COMMENTS_FORMAT = String.format("%s/comments", USER_FORMAT); // "/user/:userID/comments"

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
    Spark.put(EDIT_COMMENT_FORMAT, putComment(gson, db)); // "/user/editMessage/:postID"

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

    // Old methods
    /*
     * POST route that adds a new element to database.
     * Reads JSON from body of request and turns it to a
     * SimpleRequest object, extracting the title and msg,
     * and also the object.
     * 
     */
    Spark.post(CONTEXT, postIdea_old(gson, db)); // "/messages"

    /*
     * PUT route for updating a row in database.
     */
    Spark.put(POST_FORMAT, putWithID_old(gson, db)); // "/messages/:postID"

    /*
     * PUT route for adding a like,
     */
    Spark.put(String.format("%s/%s", POST_FORMAT, LIKE_PARAM), putLike(gson, db)); // "/messages/:postID/like"

    /*
     * Delete route for removing a row from database
     */
    Spark.delete(POST_FORMAT, deleteWithID_old(gson, db)); // "/messages/:postID"

  }

  /**
   * Creates the route to handle delete requests
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @deprecated In favor of deleting with userID verification
   *             {@link #deleteIdea(Gson, Database)}
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.deleteOne
   */
  private static Route deleteWithID_old(final Gson gson, Database db) {
    return (req, res) -> {
      int postID = Integer.parseInt(req.params(POST_ID));
      int result = db.deleteRow(postID, 1);
      String errorType = "unable to delete row " + postID;
      boolean errResult = (result == -1);
      return JSONResponse(gson, errorType, errResult, null, null, res);
    };
  }

  /**
   * Creates the route to delete ideas
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.deleteOne
   */
  private static Route deleteIdea(final Gson gson, Database db) {
    return (req, res) -> {
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {
        res.type("application/json");
        res.status(401); // Unauthorized
        return gson.toJson(new StructuredResponse("err", "Unauthorized User", null));
      }

      int userID = TokenManager.getUserID(req.cookie(ID_TOKEN));
      int postID = Integer.parseInt(req.params(POST_ID));
      extractResponse(res);
      int result = db.deleteRow(postID, userID);
      String errorType = "unable to delete row " + postID;
      boolean errResult = (result == -1);
      return JSONResponse(gson, errorType, errResult, null, null, res);
    };
  }

  /**
   * Creates the route to handle content changes
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @deprecated In favor of editing with userID verification
   *             {@link #putIdea(Gson, Database)}
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
      return JSONResponse(gson, errorType, errResult, null, result, res);
    };
  }

  /**
   * Creates the route to handle content changes with post id
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.updatedOne
   */
  private static Route putIdea(final Gson gson, Database db) {
    return (req, res) -> {
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {
        res.type("application/json");
        res.status(401); // Unauthorized
        return gson.toJson(new StructuredResponse("err", "Unauthorized User", null));
      }

      int userID = TokenManager.getUserID(req.cookie(ID_TOKEN));
      int postID = Integer.parseInt(req.params(POST_ID));
      SimpleRequest sReq = gson.fromJson(req.body(), SimpleRequest.class);
      Integer result = db.updateOne(postID, sReq.mTitle, sReq.mMessage, userID);
      String errorType = "unable to update row " + postID + " by user " + userID;
      boolean errResult = (result < 1);
      return JSONResponse(gson, errorType, errResult, null, result, res);
    };
  }

  /**
   * Creates the route to handle user profile changes
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.updatedOne
   */
  private static Route putUser(final Gson gson, Database db) {
    return (req, res) -> {
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {
        res.type("application/json");
        res.status(401); // Unauthorized
        return gson.toJson(new StructuredResponse("err", "Unauthorized User", null));
      }
      String idToken = req.cookie(ID_TOKEN);
      String sub = req.cookie(SUB_TOKEN);
      int userID = TokenManager.getUserID(idToken);
      UserData profile = gson.fromJson(req.body(), UserData.class);
      Integer result = db.updateUser(userID, sub, profile.uUsername, profile.uGender, profile.uSO);
      String errorType = String.format("unable to update user: %s", profile.toString());
      boolean errResult = (result < 1);
      return JSONResponse(gson, errorType, errResult, null, result, res);
    };
  }

  /**
   * Creates the route to handle comment changes
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.updatedOne
   */
  private static Route putComment(final Gson gson, Database db) {
    return (req, res) -> {
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {
        res.type("application/json");
        res.status(401); // Unauthorized
        return gson.toJson(new StructuredResponse("err", "Unauthorized User", null));
      }

      int userID = TokenManager.getUserID(req.cookie(ID_TOKEN));
      int commentID = Integer.parseInt(req.params(COMMENT_ID));
      SimpleRequest sReq = gson.fromJson(req.body(), SimpleRequest.class);
      Integer result = db.updateComment(sReq.mMessage, commentID, userID);
      String errorType = "unable to update comment " + commentID + " by user " + userID;
      boolean errResult = (result < 1);
      return JSONResponse(gson, errorType, errResult, null, result, res);
    };
  }

  /**
   * Creates the route to handle like changes
   * 
   * @deprecated As of Sprint 8, this method is deprecated in favor of
   *             {@link #postUpVote(Gson, Database)}
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
      return JSONResponse(gson, errorType, errResult, null, null, res);
    };
  }

  /**
   * Creates the route to handle upvoting
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.togglelike
   */
  private static Route postUpVote(final Gson gson, Database db) {
    return (req, res) -> {

      // Verify the token
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {
        res.type("application/json");
        res.status(401); // Unauthorized
        return gson.toJson(new StructuredResponse("err", "Unauthorized User", null));
      }

      int userID = TokenManager.getUserID(req.cookie(ID_TOKEN));
      int postID = Integer.parseInt(req.params(POST_ID));
      int result = db.toggleVote(postID, 1, userID);
      String errorType = "error  upvote: post id=" + postID + " by user " + userID;
      boolean errResult = (result == -1);
      return JSONResponse(gson, errorType, errResult, null, null, res);
    };
  }

  /**
   * Creates the route to handle downvoting
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.togglelike
   */
  private static Route postDownVote(final Gson gson, Database db) {
    return (req, res) -> {
      // Verify the token
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {
        res.type("application/json");
        res.status(401); // Unauthorized
        return gson.toJson(new StructuredResponse("err", "Unauthorized User", null));
      }

      int userID = TokenManager.getUserID(req.cookie(ID_TOKEN));
      int postID = Integer.parseInt(req.params(POST_ID));
      int result = db.toggleVote(postID, -1, userID);
      String errorType = "error downvote: post id=" + postID + " by user " + userID;
      boolean errResult = (result == -1);
      return JSONResponse(gson, errorType, errResult, null, null, res);
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
      return JSONResponse(gson, errorType, errResult, message, null, res);
    };
  }

  /**
   * Creates the route to handle post idea requests with userID
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.insertRow
   */
  private static Route postIdea(final Gson gson, Database db) {
    return (req, res) -> {
      // Verify the token
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {
        res.type("application/json");
        res.status(401); // Unauthorized
        return gson.toJson(new StructuredResponse("err", "Unauthorized User", null));
      }

      int userID = TokenManager.getUserID(req.cookie(ID_TOKEN));
      SimpleRequest sReq = gson.fromJson(req.body(), SimpleRequest.class);
      // createEntry checks for null title/message (-1)
      int rowsAdded = db.insertRow(sReq.mTitle, sReq.mMessage, userID);
      String errorType = "error performing insertion";
      boolean errResult = (rowsAdded <= 0);
      String message = "" + rowsAdded;
      return JSONResponse(gson, errorType, errResult, message, null, res);
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
    return (req, res) -> {
      // Verify the token
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {
        res.type("application/json");
        res.status(401); // Unauthorized
        return gson.toJson(new StructuredResponse("err", "Unauthorized User", null));
      }

      int userID = TokenManager.getUserID(req.cookie(ID_TOKEN));
      int postID = Integer.parseInt(req.params(POST_ID));
      SimpleRequest sReq = gson.fromJson(req.body(), SimpleRequest.class);
      int commentsAdded = db.insertComment(sReq.mMessage, postID, userID);
      String errorType = String.format("Error inserting comment to postID %d by userID: %d",
          postID, userID);
      boolean errResult = (commentsAdded <= 0);
      String message = String.format("Succesfully inserted %d comment to postID %d by userID: %d",
          commentsAdded, postID, userID);
      return JSONResponse(gson, errorType, errResult, message, null, res);
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
        res.type("application/json");
        res.status(401); // Unauthorized
        return gson.toJson(new StructuredResponse("err", "Unauthorized User", null));
      }
      return JSONResponse(gson, "Invalid or missing ID token", !verified, null, db.selectAll(), res);
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
        res.type("application/json");
        res.status(401); // Unauthorized
        return gson.toJson(new StructuredResponse("err", "Unauthorized User", null));
      }
      int postID = Integer.parseInt(req.params(POST_ID));
      PostData data = db.selectOne(postID);
      String errorType = postID + " not found";
      boolean errResult = (data == null);
      String message = null;
      return JSONResponse(gson, errorType, errResult, message, data, res);
    };
  }

  /**
   * Creates the route to handle get user requests that give a specific id
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json behavior for
   *         db.selectOne()
   */
  private static Route getUser(final Gson gson, Database db) {
    return (req, res) -> {
      // Verify the token
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {
        res.type("application/json");
        res.status(401); // Unauthorized
        return gson.toJson(new StructuredResponse("err", "Unauthorized User", null));
      }
      int userID = Integer.parseInt(req.params(USER_ID));
      UserDataLite data = db.getUserSimple(userID);
      String errorType = userID + " not found";
      boolean errResult = (data == null);
      String message = null;
      return JSONResponse(gson, errorType, errResult, message, data, res);
    };
  }

  /**
   * Creates the route to handle get user profile requests
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json behavior for
   *         db.selectOne()
   */
  private static Route getUserFull(final Gson gson, Database db) {
    return (req, res) -> {
      // Verify the token
      boolean verified = getVerified(db, req);
      // If it doesn't exist in TokenManager, return error
      if (!verified) {
        res.status(401); // Unauthorized
        return gson.toJson(new StructuredResponse("err", "Unauthorized User", null));
      }
      extractResponse(res);

      int userID = TokenManager.getUserID(req.cookie(ID_TOKEN));
      UserData data = db.getUserFull(userID);
      String errorType = "Cannot verify user";
      return JSONResponse(gson, errorType, !verified, null, data, res);
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
        res.type("application/json");
        res.status(401); // Unauthorized
        return gson.toJson(new StructuredResponse("err", "Unauthorized User", null));
      }
      int postID = needsPost ? Integer.parseInt(req.params(POST_ID)) : 0;
      int userId = needsUser ? Integer.parseInt(req.params(USER_ID)) : 0;
      res.type("application/json");
      res.status(200);
      // Needs both of them
      if (needsUser && needsPost) {
        return gson.toJson(
            new StructuredResponse("ok", null, db.selectAllComments(userId, postID)));
      } else if (needsPost && !needsUser) { // Only needs postID
        return gson.toJson(
            new StructuredResponse("ok", null, db.selectAllCommentByPost(postID)));
      } else if (!needsPost && needsUser) { // Only needs userID
        return gson.toJson(
            new StructuredResponse("ok", null, db.selectAllCommentByUserID(userId)));
      } else { // needs neither postID nor userID, so get the commentID
        try {
          int commentID = Integer.parseInt(req.params(COMMENT_ID));
          return gson.toJson(new StructuredResponse(
              "ok", null, db.selectComment(commentID)));
        } catch (NumberFormatException e) {
          res.type("application/json");
          res.status(403);
          return gson.toJson(new StructuredResponse(
              "error", "invalid parameters", null));
        }
      }
    };
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
    boolean verified = (idToken != null)
        && Oauth.verifyToken(idToken)
        && TokenManager.containsToken(idToken)
        && (userID == userIDSub);
    // If it doesn't exist in TokenManager, return error
    return verified;
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
      String sub = null;
      int userID = 0;
      if (verified) {
        // Token is valid, extract email from payload
        email = Oauth.getEmail(idToken);
        name = Oauth.getName(idToken);
        sub = Oauth.getSub(idToken);
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
        res.cookie(ID_TOKEN, idToken);
        res.cookie(SUB_TOKEN, sub);
        res.redirect("./index.html");
      } else {
        // Token is invalid or missing
        res.status(401); // Unauthorized status code
      }
      String errorType = "Invalid or missing ID token: " + idToken;
      boolean errResult = !verified;
      UserData user = db.getUserFull(db.findUserID(email));
      return JSONResponse(gson, errorType, errResult, idToken, user, res);
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
   * @param errorMsg  Error Message
   * @param errResult Evaluation of result
   * @param message   mMessage for JSON on OK
   * @param data      mData for JSON on OK
   * @param response  for setting response status
   * @return JSON response
   */
  private static Object JSONResponse(final Gson gson, String errorMsg, boolean errResult, String message,
      Object data, Response response) {
    response.type("application/json");
    if (errResult) {
      response.status(403);
      return gson.toJson(new StructuredResponse(
          "error", errorMsg, data));
    } else {
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