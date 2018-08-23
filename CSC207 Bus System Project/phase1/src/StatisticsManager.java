/* Dan */

import java.util.ArrayList;

/**
 * Class that manages information and statistics of the transit system.
 *
 * @author group 0136
 */
public class StatisticsManager {

  // Instance variables storing information the transit system.
  private static ArrayList<TransitDate> invalidTapEventsDates = new ArrayList<>();
  private static ArrayList<Trip> trips = new ArrayList<>();

  /**
   * Returns an array list of dates where invalid taps occurred.
   *
   * @return - array list of dates where invalid taps occurred.
   */
  public static ArrayList<TransitDate> getInvalidTapEvents() {
    return invalidTapEventsDates;
  }

  /**
   * Returns an array list trips taken.
   *
   * @return - array list trips taken.
   */
  public static ArrayList<Trip> getTrips() {
    return trips;
  }

  /**
   * Adds a trip to the array list of recorded trips.
   *
   * @param trip - trip to add to the array list of recorded trips.
   */
  public static void addTrip(Trip trip) {
    trips.add(trip);
  }

  /**
   * Adds a invalid tap date to the array list of recorded invalid taps dates.
   *
   * @param date - date to add to the array list of recorded invalid tap dates.
   */
  public static void addInvalidTapEvent(TransitDate date) {
    invalidTapEventsDates.add(date);
  }

  /**
   * returns the number of invalid taps on a single day.
   *
   * @param day - the day of which invalid taps occurred.
   * @return - the number of invalid taps on a single day.
   */
  public static int countInvalidTapsOnDate(TransitDate day) {
    int counter = 0;

    for (TransitDate invalidTapEventsDate : invalidTapEventsDates) {
      if (day.onSameDay(invalidTapEventsDate)) {
        counter++;
      }
    }
    return counter;
  }

  /**
   * returns the number of invalid taps on a multiple days.
   *
   * @param days - the array list of days of which invalid taps occurred.
   * @return - the number of invalid taps on a multiple days.
   */
  public static int countInvalidTapsMultiDays(ArrayList<TransitDate> days) {
    int counter = 0;

    for (TransitDate day : days) {
      for (TransitDate invalidTapEventsDate : invalidTapEventsDates) {
        if (day.onSameDay(invalidTapEventsDate)) {
          counter++;
        }
      }
    }
    return counter;
  }

  /**
   * returns the number of taps on a single day.
   *
   * @param date - the day of which taps occurred.
   * @return - the number of taps on a single day.
   */
  public static ArrayList<TapEvent> getTapsOnDate(TransitDate date) {
    ArrayList<TapEvent> dateMatchTapEvents = new ArrayList<>();

    for (Trip trip : trips) {
      for (int x = 0; x < trip.getTapEvents().size(); x++) {
        if (date.onSameDay(trip.getTapEvents().get(x).getTransitDate())) {
          dateMatchTapEvents.add(trip.getTapEvents().get(x));
        }
      }
    }
    return dateMatchTapEvents;
  }

  /**
   * returns the number of trips on a single day.
   *
   * @param date - the day of which trips occurred.
   * @return - the number of trips on a single day.
   */
  public static ArrayList<Trip> getTripsOnDate(TransitDate date) {
    ArrayList<Trip> tripsOnDate = new ArrayList<>();
    for (Trip trip : trips) {
      if (trip.getStartDate().onSameDay(date) || trip.getEndDate().onSameDay(date)) {
        tripsOnDate.add(trip);
      }
    }
    return tripsOnDate;
  }

  /**
   * returns revenue collected from recorded trips.
   *
   * @param trips - array list of trips.
   * @return - revenue collected from recorded trips.
   */
  public static double calculateRevenueFromTrips(ArrayList<Trip> trips) {
    double revenue = 0;
    for (Trip trip : trips) {
      revenue += trip.getCost();
    }
    return revenue;
  }

  /**
   * returns revenue gained on a single day.
   *
   * @param date - the day to return revenue from.
   * @return - revenue gained on a single day.
   */
  public static double calculateRevenueOnDate(TransitDate date) {
    ArrayList<Trip> tripsOnDate = getTripsOnDate(date);
    return calculateRevenueFromTrips(tripsOnDate) + countInvalidTapsOnDate(date) * 6;
  }

  /**
   * Calculate total revenue gained thus far.
   *
   * @return - total revenue.
   */
  public static double calculateRevenue() {
    return calculateRevenueFromTrips(trips) + invalidTapEventsDates.size() * 6;
  }

  /**
   * Calculate total profit gained thus far.
   *
   * @param cost - total cost of running the transit system.
   * @param trips - array list of trips that will be used determine revenue.
   * @return - total profit.
   */
  public static double calculateProfit(ArrayList<Trip> trips, double cost) {
    return cost - calculateRevenueFromTrips(trips);
  }

  /**
   * Calculate array list of stations used on one day.
   *
   * @param date - date to record the number of stations reached.
   * @return - array list of stations used on one day.
   */
  public static ArrayList<Station> getStationsReachedOnDate(TransitDate date) {
    ArrayList<Trip> trips = getTripsOnDate(date);
    ArrayList<Station> stationsReached = new ArrayList<>();
    for (Trip trip : trips) {
      for (TapEvent event : trip.getTapEvents()) {
        if (date.onSameDay(
            event.getTransitDate())) { // determine if station was tapped on the particular date
          stationsReached.add(event.getStation());
        }
      }
    }
    return stationsReached;
  }
}
