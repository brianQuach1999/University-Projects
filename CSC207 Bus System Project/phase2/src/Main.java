import api.Api;
import transit.TransitFareManager;
import ui.TitleScreen;
import ui.UiController;
import util.DataReadException;
import util.DataWriteException;
import util.EasyLogger;

public class Main {

  /**
   * Main method of the app. Creates a map using the map configuration at map.txt, creates a transit
   * fare manager with that map, and then applying the events in events.txt to the new transit fare
   * manager.
   */

  private static TransitFareManager transitFareManager;
  private static transient EasyLogger logger = new EasyLogger("main");

  public static void main(String[] args) {

    String path = "data/transitFareManager.ser";
    Api api = new Api();
    UiController.api = api;

    try {
      api.loadApplicationStateFromFile(path);
    } catch (DataWriteException e) {
      logger.log.severe("Couldn't write serializable file " + path);
    } catch (DataReadException e) {
      logger.log.severe("Couldn't load application state from file " + path);
      api.loadApplicationStateFromEventsTxt();
      api.saveApplicationStateToFile(path);
    }
    TitleScreen.view();

  }

}
