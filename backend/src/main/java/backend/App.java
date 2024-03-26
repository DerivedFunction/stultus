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
   * parameters for website
   */
  private static final String CONTEXT = "/messages";
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
      System.out.println("New location: " + static_location_override);
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
    Spark.get(CONTEXT, getAll(gson, db));

    /*
     * GET route that returns message with specific id.
     * Converts StructuredResponses to JSON
     */
    Spark.get(CONTEXT + "/:id", getWithId(gson, db));

    /*
     * POST route that adds a new element to DataStore.
     * Reads JSON from body of request and turns it to a
     * SimpleRequest object, extracting the title and msg,
     * and also the object.
     */
    Spark.post(CONTEXT, postIdea(gson, db));

    /*
     * PUT route for updating a row in DataStore. Almost the same
     * as POST
     */
    Spark.put(CONTEXT + "/:id", putWithID(gson, db));

    /*
     * PUT route for voting
     */
    Spark.put(CONTEXT + "/:id/vote/:voteValue", putVote(gson, db));

    /*
     * PUT route for adding a like,
     */
    Spark.put(CONTEXT + "/:id/like", putLike(gson, db));

    /*
     * Delete route for removing a row from DataStore
     */
    Spark.delete(CONTEXT + "/:id", deleteWithID(gson, db));
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
    return (request, response) -> {
      int idx = Integer.parseInt(request.params("id"));
      extractResponse(response);
      int result = db.deleteRow(idx);

      if (result == -1) {
        return gson.toJson(new StructuredResponse(
            "error", "unable to delete row " + idx, null));
      } else {
        return gson.toJson(new StructuredResponse("ok", null, null));
      }
    };
  }

  /**
   * Creates the route to handle content changes
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.updatedOne
   */
  private static Route putWithID(final Gson gson, Database db) {
    return (request, response) -> {
      int idx = Integer.parseInt(request.params("id"));
      SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
      extractResponse(response);
      int result = db.updateOne(idx, req.mTitle, req.mMessage);
      if (result < 1) {
        return gson.toJson(new StructuredResponse(
            "error", "unable to update row " + idx, null));
      } else {
        return gson.toJson(new StructuredResponse("ok", null, result));
      }
    };
  }

  /**
   * Creates the route to handle like changes
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.togglelike
   */
  private static Route putLike(final Gson gson, Database db) {
    return (request, response) -> {
      int idx = Integer.parseInt(request.params("id"));
      int result = db.toggleLike(idx);
      if (result == -1) {
        return gson.toJson(new StructuredResponse(
            "error", "error performing insertion", null));
      } else {
        return gson.toJson(new StructuredResponse("ok", null, null));
      }
    };
  }

  /**
   * Creates the route to handle like changes
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.togglelike
   */
  private static Route putVote(final Gson gson, Database db) {
    return (request, response) -> {
      int idx = Integer.parseInt(request.params("id"));
      int vote = Integer.parseInt(request.params("voteValue"));
      System.err.println("post: " + idx + " vote value" + vote);
      int result = db.toggleVote(idx, vote, 0);
      if (result == -1) {
        return gson.toJson(new StructuredResponse(
            "error", "error updating vote", null));
      } else {
        return gson.toJson(new StructuredResponse("ok", null, null));
      }
    };
  }

  /**
   * Creates the route to handle put requests
   * 
   * @param gson Gson object that handles shared serialization
   * @param db   Database object to execute the method of
   * @return Returns a spark Route object that handles the json response behavior
   *         for db.insertRow
   */
  private static Route postIdea(final Gson gson, Database db) {
    return (request, response) -> {
      SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
      extractResponse(response);
      // createEntry checks for null title/message (-1)
      int rowsAdded = db.insertRow(req.mTitle, req.mMessage);
      if (rowsAdded <= 0) {
        return gson.toJson(new StructuredResponse(
            "error", "error performing insertion", null));
      } else {
        return gson.toJson(new StructuredResponse("ok", "" + rowsAdded, null));
      }
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
    return (request, response) -> {
      int idx = Integer.parseInt(request.params("id"));
      extractResponse(response);
      Database.RowData data = db.selectOne(idx);
      if (data == null) {
        return gson.toJson(
            new StructuredResponse("error", idx + " not found", null));
      } else {
        return gson.toJson(new StructuredResponse("ok", null, data));
      }
    };
  }

  /**
   * Responseesponse on success
   * 
   * @param response 200 OK
   */
  private static void extractResponse(Response response) {
    response.status(200);
    response.type("application/json");
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
