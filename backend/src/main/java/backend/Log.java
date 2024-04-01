package backend;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple way to log messages
 */
public class Log {

    /**
     * Our logger for backend
     */
    private static final Logger logger = Logger.getLogger("Backend");

    /**
     * Logs an informational message.
     *
     * @param message The message to log.
     */
    public static void info(String message) {
        logger.log(Level.INFO, message);
    }

    /**
     * Logs a warning message.
     *
     * @param message The message to log.
     */
    public static void warning(String message) {
        logger.log(Level.WARNING, message);
    }

    /**
     * Logs an error message.
     *
     * @param message The message to log.
     */
    public static void error(String message) {
        logger.log(Level.SEVERE, message);
    }

}
