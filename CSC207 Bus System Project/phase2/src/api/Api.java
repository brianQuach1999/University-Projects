package api;

import transit.EventConfigFileParser;
import transit.Map;
import transit.MapConfigFileParser;
import transit.StatisticsManager;
import transit.TransitFareManager;
import util.DataReadException;
import util.DataReadWrite;
import util.DataWriteException;
import util.EasyLogger;

/**
 * Class to function as an interface between the transit system and the UI.
 */
public class Api {

  /**
   * Child APIs for domain-specific API queries.
   */
  public UserApi user;
  public CardApi card;
  public MapApi map;
  public StatsApi stats;

  private TransitFareManager transitFareManager;
  private StatisticsManager statisticsManager;
  private transient EasyLogger logger = new EasyLogger("api");

  /**
   * Initializes Child APIs.
   */
  private void init() {
    user = new UserApi(transitFareManager, statisticsManager, logger);
    card = new CardApi(transitFareManager, statisticsManager, logger);
    map = new MapApi(transitFareManager, statisticsManager, logger);
    stats = new StatsApi(transitFareManager, statisticsManager, logger);
  }

  /**
   * Gets deserialized application data from file.
   *
   * @param fileName name of file containing serialized application data
   */
  public void loadApplicationStateFromFile(String fileName)
      throws DataReadException, DataWriteException {
    DataReadWrite<TransitFareManager> dataReadWrite = new DataReadWrite<>(fileName);
    transitFareManager = dataReadWrite.read();
    logger.log.fine("Successfully loaded application state from " + fileName);
    statisticsManager = new StatisticsManager(transitFareManager);
    init();
  }

  /**
   * Gets application data by parsing events.txt.
   */
  public void loadApplicationStateFromEventsTxt() {
    logger.log.fine("Creating new application state from events.txt.");
    // Create data from events.txt and map.txt
    Map map = new Map();
    MapConfigFileParser mapConfigFileParser = new MapConfigFileParser("map.txt", map, logger);
    mapConfigFileParser.parse();
    transitFareManager = new TransitFareManager(map);
    statisticsManager = new StatisticsManager(transitFareManager);
    EventConfigFileParser eventConfigFileParser = new EventConfigFileParser("events.txt",
        transitFareManager, statisticsManager, logger);
    eventConfigFileParser.parse();
    init();
  }

  /**
   * Saves serialized application state to default file "data/transitFareManager.ser".
   */
  public void saveApplicationState() {
    saveApplicationStateToFile("data/transitFareManager.ser");
  }

  /**
   * Saves serialized application state to file specified by parameter fileName.
   *
   * @param fileName location of file to save serialized data to
   */
  public void saveApplicationStateToFile(String fileName) {
    try {
      DataReadWrite<TransitFareManager> dataReadWrite = new DataReadWrite<>(fileName);
      dataReadWrite.save(transitFareManager);
      logger.log.fine("Successfully saved application state to file " + fileName);
    } catch (Exception e) {
      logger.log.severe("Couldn't save application state to file " + fileName);
    }
  }

}
