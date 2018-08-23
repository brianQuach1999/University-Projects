package api;

import java.util.ArrayList;
import transit.Route;
import transit.Station;
import transit.StatisticsManager;
import transit.TapEvent;
import transit.Transaction;
import transit.TransitFareManager;
import transit.Trip;
import transit.UserAccount;
import transit.simplemodel.SimpleRoute;
import transit.simplemodel.SimpleStation;
import transit.simplemodel.SimpleTap;
import transit.simplemodel.SimpleTransaction;
import transit.simplemodel.SimpleTrip;
import transit.simplemodel.SimpleUser;
import util.EasyLogger;
import util.FormattedDate;

/**
 * Class to handle stats-related API queries.
 */
public class StatsApi extends ChildApi {

  public StatsApi(TransitFareManager transitFareManager, StatisticsManager statisticsManager,
      EasyLogger logger) {
    super(transitFareManager, statisticsManager, logger);
  }

  /**
   * Get revenue on date. If date is null, get total revenue.
   */
  public double getRevenueOnDate(FormattedDate date) {
    if (date == null) {
      return getTotalRevenue();
    }
    logger.log.fine("Getting revenue for date " + date);
    return this.statisticsManager.calculateRevenueOnDate(date);
  }

  /**
   * Get a list of SimpleUsers corresponding to the users active on specified date.
   */
  public ArrayList<SimpleUser> getUsers(FormattedDate date) {
    if (date == null) {
      return getUsers();
    }
    return getUsers(statisticsManager.getUsersReachedOnDate(date));
  }


  /**
   * Get list SimpleUsers corresponding to all users.
   */
  public ArrayList<SimpleUser> getUsers() {
    return getUsers(transitFareManager.getUsers());
  }

  /**
   * Get a list of SimpleRoutes corresponding to the routes reached on specified date.
   */
  public ArrayList<SimpleRoute> getRoutesSimple(FormattedDate date) {
    if (date == null) {
      return getRoutesSimple();
    }
    return getRoutesSimple(statisticsManager.getRoutesReachedOnDate(date));
  }


  /**
   * Get a list of SimpleTaps corresponding to the taps occurring on specified date.
   */
  public ArrayList<SimpleTap> getTapsSimple(FormattedDate date) {
    if (date == null) {
      return getTapsSimple();
    }
    return getTapsSimple(statisticsManager.getTapsOnDate(date));
  }

  /**
   * Get a list of SimpleTransaction corresponding to the transactions occurring on specified date.
   */
  public ArrayList<SimpleTransaction> getTransactionsSimple(FormattedDate date) {
    if (date == null) {
      return getTransactionsSimple();
    }
    return getTransactionsSimple(statisticsManager.getTransactionsOnDate(date));
  }

  /**
   * Get a list of SimpleStations corresponding to the stations reached specified date.
   */
  public ArrayList<SimpleStation> getStationsSimple(FormattedDate date) {
    if (date == null) {
      return getStationsSimple();
    }
    return getStationsSimple(statisticsManager.getStationsReachedOnDate(date));
  }


  /**
   * Get all SimpleTrip objects corresponding to certain date.
   */
  public ArrayList<SimpleTrip> getTripsSimple(FormattedDate date) {
    if (date == null) {
      return getTripsSimple();
    }
    return getTripsSimple(statisticsManager.getTripsOnDate(date));
  }

  /**
   * Get all stations.
   */
  public ArrayList<Station> getStations() {
    return transitFareManager.getMap().getStations();
  }


  /**
   * Get all trips.
   */
  private ArrayList<Trip> getTrips() {
    return transitFareManager.getTrips();
  }

  /**
   * Get all trips as SimpleTrip objects.
   */
  private ArrayList<SimpleTrip> getTripsSimple() {
    return getTripsSimple(getTrips());
  }

  /**
   * Get all SimpleTrip objects corresponding to specified list of trips.
   */
  private ArrayList<SimpleTrip> getTripsSimple(ArrayList<Trip> trips) {
    ArrayList<SimpleTrip> result = new ArrayList<>();
    for (Trip trip : trips) {
      result.add(new SimpleTrip(trip));
    }
    return result;
  }

  /**
   * Get all stations as SimpleStation objects.
   */
  private ArrayList<SimpleStation> getStationsSimple() {
    return getStationsSimple(getStations());
  }

  /**
   * Get a list of SimpleStations corresponding to the given list of stations.
   */
  private ArrayList<SimpleStation> getStationsSimple(ArrayList<Station> stations) {
    ArrayList<SimpleStation> result = new ArrayList<>();
    for (Station station : stations) {
      result.add(new SimpleStation(station));
    }
    return result;
  }

  /**
   * Get all transactions.
   */
  private ArrayList<Transaction> getTransactions() {
    return transitFareManager.getTransactions();
  }

  /**
   * Get all transactions as SimpleTransaction objects.
   */
  private ArrayList<SimpleTransaction> getTransactionsSimple() {
    return getTransactionsSimple(getTransactions());
  }

  /**
   * Get all SimpleTransaction objects corresponding to specified list of transactions.
   */
  private ArrayList<SimpleTransaction> getTransactionsSimple(ArrayList<Transaction> transactions) {
    ArrayList<SimpleTransaction> result = new ArrayList<>();
    for (Transaction transaction : transactions) {
      result.add(new SimpleTransaction(transaction));
    }
    return result;
  }

  /**
   * Get all taps.
   */
  private ArrayList<TapEvent> getTaps() {
    return statisticsManager.getTaps();
  }

  /**
   * Get all taps as SimpleTap objects.
   */
  private ArrayList<SimpleTap> getTapsSimple() {
    return getTapsSimple(getTaps());
  }

  /**
   * Get all SimpleTaps objects corresponding to specified list of taps.
   */
  private ArrayList<SimpleTap> getTapsSimple(ArrayList<TapEvent> taps) {
    ArrayList<SimpleTap> result = new ArrayList<>();
    for (TapEvent tap : taps) {
      result.add(new SimpleTap(tap));
    }
    return result;
  }


  /**
   * Get all routes.
   */
  private ArrayList<Route> getRoutes() {
    return transitFareManager.getMap().getRoutes();
  }

  /**
   * Get all routes as SimpleRoute objects.
   */
  private ArrayList<SimpleRoute> getRoutesSimple() {
    return getRoutesSimple(getRoutes());
  }

  /**
   * Get all SimpleRoute objects corresponding to specified list of routes.
   */
  private ArrayList<SimpleRoute> getRoutesSimple(ArrayList<Route> routes) {
    ArrayList<SimpleRoute> result = new ArrayList<>();
    for (Route route : routes) {
      result.add(new SimpleRoute(route));
    }
    return result;
  }

  /**
   * Get list of SimpleUsers corresponding to specified list of users.
   */
  private ArrayList<SimpleUser> getUsers(ArrayList<UserAccount> users) {
    ArrayList<SimpleUser> result = new ArrayList<>();
    for (UserAccount user : users) {
      result.add(new SimpleUser(user));
    }
    return result;
  }

  /**
   * Get total revenue.
   */
  private double getTotalRevenue() {
    return this.statisticsManager.calculateRevenue();
  }

}
