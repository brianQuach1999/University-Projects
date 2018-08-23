package util;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EasyLogger {
  public Logger log;

  public EasyLogger(String fileName) {

    log = Logger.getLogger(fileName);

    // Disable default console handler
    log.setUseParentHandlers(false);

    // Associate the handler with the log.
    try {
      log.setLevel(Level.ALL);

      Handler consoleHandler = new ConsoleHandler();
      consoleHandler.setLevel(Level.ALL);

      Handler fileHandler = new FileHandler("log/" + fileName + ".log", true);
      fileHandler.setLevel(Level.ALL);

      Handler fileHandlerSevere = new FileHandler("log/" + fileName + "-error.log", true);
      fileHandlerSevere.setLevel(Level.SEVERE);

      // add handlers to logger
      log.addHandler(consoleHandler);
      log.addHandler(fileHandler);
      log.addHandler(fileHandlerSevere);

    } catch (IOException e) {
      Handler consoleHandler = new ConsoleHandler();
      log.setLevel(Level.ALL);
      consoleHandler.setLevel(Level.ALL);
      log.addHandler(consoleHandler);
      log.log(Level.SEVERE, "Could not open or create log files.");
    }
  }
}
