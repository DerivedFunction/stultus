package backend;

/**
 * StructuredResponse provides an object that can be converted to JSON
 */
public class StructuredResponse {
  /**
   * The status indicates if it is valid or not
   */
  public String mStatus;

  /**
   * Message is useful on errors or when data is null
   */
  public String mMessage;

  /**
   * Any JSON-friendly object
   */
  public Object mData;

  /**
   * Contruct a StructuredResponse by providing a status, message, and data.
   * If status is not provided, set to "invalid"
   * 
   * @param status  usually "ok" or "error"
   * @param message message w/ error status
   * @param data    with data to send to client
   */
  public StructuredResponse(String status, String message, Object data) {
    mStatus = (status != null) ? status : "invalid";
    mMessage = message.trim();
    mData = data;
  }
}
