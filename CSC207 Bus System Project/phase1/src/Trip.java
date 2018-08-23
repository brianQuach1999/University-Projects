/* Brian */

import java.util.ArrayList;

/**
 * A transit journey consisting of bus and subway rides.
 */
public class Trip implements Comparable<Trip> {

  protected static final double MAX_CHARGE = 6;
  private double cost;
  private ArrayList<TapEvent> tapEvents = new ArrayList<>();

  /**
   * Returns the <code>Date</code> of the first tap in of this <code>Trip</code>.
   *
   * @return the starting date of the first tap in
   */
  public TransitDate getStartDate() {
    if (tapEvents.size() == 0) {
      return null;
    }
    return tapEvents.get(0).getTransitDate();
  }

  /**
   * Returns the <code>Date</code> of the last tap of this <code>Trip</code>.
   *
   * @return the date of the last tap
   */
  public TransitDate getEndDate() {
    if (tapEvents.size() == 0) {
      return null;
    }
    return tapEvents.get(tapEvents.size() - 1).getTransitDate();
  }

  /**
   * Returns an <code>ArrayList</code> of <code>TapEvent</code> that this <code>Trip</code> has.
   *
   * @return the list of tap events of this <code>Trip</code>
   */
  public ArrayList<TapEvent> getTapEvents() {
    return this.tapEvents;
  }

  /**
   * Returns the current cumulative charge for this <code>Trip</code>.
   *
   * @return the current cumulative charge for this <code>Trip</code>
   */
  public double getCost() {
    return this.cost;
  }

  /**
   * Checks if the given <code>TapInEvent</code> is legal and if it is the start of a new
   * <code>Trip
   * </code>. If it is the start of a new <code>Trip</code>,bit will throw a new <code>
   * TripInvalidTapEventException</code>. If it is not legal, it will throw a new <code>
   * UnnaturalTapSequenceException</code>. Else, it documents the given <code>TapInEvent</code>, and
   * returns the charge for the tap.
   *
   * @param tapInEvent the tap event to be registered
   * @return the charge of the tap
   * @throws TripInvalidTapEventException if the tap is the start of a new trip
   * @throws TripUnnaturalTapSequenceException if the tap is illegal
   */
  public double registerTapInEvent(TapInEvent tapInEvent)
      throws TripInvalidTapEventException, TripUnnaturalTapSequenceException {
    if (!isTapInEventLegal(tapInEvent)) {
      throw new TripUnnaturalTapSequenceException();
    }
    if (!canAddTapInToCurrentTrip(tapInEvent)) {
      throw new TripInvalidTapEventException();
    }
    tapEvents.add(tapInEvent);
    double tapPrice = tapInEvent.getStation().tapInPrice;
    if (cost + tapPrice > MAX_CHARGE) {
      tapPrice = MAX_CHARGE - cost;
    }
    cost += tapPrice;
    return tapPrice;
  }

  /**
   * Checks if the given <code>TapOutEvent</code> is legal. If it is not legal, it will throw a new
   * <code> UnnaturalTapSequenceException</code>. Else, it documents the given <code>TapInEvent
   * </code>, and returns the charge for the tap.
   *
   * @param tapOutEvent the tap event to be registered
   * @return the charge for the tap
   * @throws TripUnnaturalTapSequenceException if the tap is illegal
   */
  public double registerTapOutEvent(TapOutEvent tapOutEvent)
      throws TripUnnaturalTapSequenceException {
    if (!isTapOutEventLegal(tapOutEvent)) {
      throw new TripUnnaturalTapSequenceException();
    }

    Station stationIn = tapEvents.get(tapEvents.size() - 1).getStation();
    Station stationOut = tapOutEvent.getStation();
    tapEvents.add(tapOutEvent);
    int routeLength = stationOut.getRoute().getRouteLength(stationIn, stationOut);
    double tapPrice = routeLength * stationOut.passThroughPrice;
    if (cost + tapPrice > MAX_CHARGE) {
      tapPrice = MAX_CHARGE - cost;
    }
    cost += tapPrice;
    return tapPrice;
  }

  private boolean canAddTapInToCurrentTrip(TapInEvent tapInEvent) {
    // if this is the first tap in of this trip, return true
    if (tapEvents.size() == 0) {
      return true;
    }
    // if tapInEvent is past 2hrs. from first tapInEvent 2hrs = 2*60*60*1000 ms
    if (tapInEvent.getTransitDate().getDate().getTime() - getStartDate().getDate().getTime()
        > 7200000) {
      return false;
    }
    // check if this tap in is adjacent to the previous tap out
    Station previousStation = tapEvents.get(tapEvents.size() - 1).getStation();
    return tapInEvent.getStation().isAdjacentToStation(previousStation);
  }

  private boolean isTapInEventLegal(TapInEvent tapInEvent) {
    if (tapEvents.size() == 0) {
      return true;
    }
    TapEvent previousTap = tapEvents.get(tapEvents.size() - 1);
    // 2 tapInEvents in a row is not legal.
    return !(previousTap instanceof TapInEvent);
  }

  private boolean isTapOutEventLegal(TapOutEvent tapOutEvent) {
    // initializing a trip with a tap out event is not legal
    if (tapEvents.size() == 0) {
      return false;
    }
    // 2 tapOutEvents in a row is not legal
    TapEvent previousTap = tapEvents.get(tapEvents.size() - 1);
    if (previousTap instanceof TapOutEvent) {
      return false;
    }
    // if last tap in was at same route as tap out route
    return previousTap.getStation().getRoute() == tapOutEvent.getStation().getRoute();
  }

  /**
   * Returns a <code>String</code> representation of this <code>Trip</code>.
   *
   * @return a <code>String</code> representation of this <code>Trip</code>
   */
  @Override
  public String toString() {
    return "Trip Start: "
        + this.getStartDate().toDateTimeString()
        + " | End: "
        + this.getEndDate().toDateTimeString()
        + " | Cost: "
        + this.getCost()
        + " | Tap Log: "
        + ConfigFileParser.getStringFromList(tapEvents, "Taps");
  }

  @Override
  public int compareTo(Trip td) {
    if (getStartDate() == null || td.getStartDate() == null) {
      return 0;
    }
    return getStartDate().compareTo(td.getStartDate());
  }
}
