package backend;

import java.util.Map;

import com.google.gson.*;

import spark.Response;
import spark.Route;
import spark.Spark;

public class App {
  private static final String DEFAULT_PORT_DB = "5432";
  private static final int DEFAULT_PORT_SPARK = 4567;

  private static final String CONTEXT = "/posts";

  public App() {

  }

  public static void main(String[] args) {
    /**
     * gson allows conversion between JSON and objects. Must be final
     */
    final Gson gson = new Gson();

    Database db = getDatabaseConnection();
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
     * GET route that returns all messages and ids.
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
     * PUT route for adding a like, 
     */
    Spark.put(CONTEXT+"/:id/like",putLike(gson, db));

    /*
     * Delete route for removing a row from DataStore
     */
    Spark.delete(CONTEXT + "/:id", deleteWithID(gson, db));
  }

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
  private static Route putLike(final Gson gson, Database db)
  {
    return (request, response)->{
      int idx = Integer.parseInt(request.params("id"));
      int result =db.toggleLike(idx);
      if (result == -1) {
        return gson.toJson(new StructuredResponse(
            "error", "error performing insertion", null));
      } else {
        return gson.toJson(new StructuredResponse("ok", null, null));
      }
    };
  }

  private static Route postIdea(final Gson gson, Database db) {
    return (request, response) -> {
      SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
      extractResponse(response);
      // createEntry checks for null title/message (-1)
      int newId = db.insertRow(req.mTitle, req.mMessage);
      if (newId == -1) {
        return gson.toJson(new StructuredResponse(
            "error", "error performing insertion", null));
      } else {
        return gson.toJson(new StructuredResponse("ok", "" + newId, null));
      }
    };
  }

  private static Route getAll(final Gson gson, Database db) {
    return (request, response) -> {
      extractResponse(response);
      return gson.toJson(
          new StructuredResponse("ok", null, db.selectAll()));
    };
  }

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

  private static void extractResponse(Response response) {
    response.status(200);
    response.type("application/json");
  }

  /**
   * Gets database connect using enviroment variables
   * 
   * @return connected database
   */
  public static Database getDatabaseConnection() {
    if (System.getenv("DATABASE_URL") != null) {
      return Database.getDatabase(System.getenv("DATABASE_URL"), DEFAULT_PORT_DB);
    }
    // get the Postgres configuration from the environment
    Map<String, String> env = System.getenv();
    String ip = env.get("POSTGRES_IP");
    String port = env.get("POSTGRES_PORT");
    String user = env.get("POSTGRES_USER");
    String pass = env.get("POSTGRES_PASS");

    // Connect to database
    return Database.getDatabase(ip, port, "", user, pass);
  }

  /**
   * Get an integer environment variable if it exists, and otherwise return the
   * default value.
   * 
   * @envar The name of the environment variable to get.
   * @defaultVal The integer value to use as the default if envar isn't found
   * 
   * @returns The best answer we could come up with for a value for envar
   */
  static int getIntFromEnv(String envar, int defaultVal) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get(envar) != null) {
      return Integer.parseInt(processBuilder.environment().get(envar));
    }
    return defaultVal;
  }

}
