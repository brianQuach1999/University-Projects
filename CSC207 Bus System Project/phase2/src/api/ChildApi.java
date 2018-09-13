package api;

import transit.StatisticsManager;
import transit.TransitFareManager;
import util.EasyLogger;

/**
 * Class for domain-specific APIs. Subclassed by CardApi, MapApi, StatsApi, and UserAPi.
 */
abstract public class ChildApi {

  protected TransitFareManager transitFareManager;
  protected transient EasyLogger logger;
  protected StatisticsManager statisticsManager;

  public ChildApi(TransitFareManager transitFareManager, StatisticsManager statisticsManager,
      EasyLogger logger) {
    this.transitFareManager = transitFareManager;
    this.logger = logger;
    this.statisticsManager = statisticsManager;
  }
}
