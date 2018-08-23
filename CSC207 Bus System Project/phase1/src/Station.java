/* Loic */

import java.util.ArrayList;

/**
 * An abstract class that models a generic station.
 *
 * @author group 0136
 */
public abstract class Station {

  /**
   * Price of passing through this station.
   */
  protected final double passThroughPrice;
  /**
   * Price of tapping in to this station.
   */
  protected final double tapInPrice;
  /**
   * List of adjacent stations, i.e. stations which one may legally transfer to on one trip.
   */
  private final ArrayList<Station> adjacentStations = new ArrayList<>();
  private final String name;
  private final Route route;

  /**
   * Station constructor.
   *
   * @param name name of station
   * @param tapInPrice price of tapping into this station
   * @param passThroughPrice price of passing through this station
   * @param route route that this station is on
   */
  public Station(String name, double tapInPrice, double passThroughPrice, Route route) {
    this.name = name;
    this.route = route;
    this.tapInPrice = tapInPrice;
    this.passThroughPrice = passThroughPrice;
  }

  /**
   * Return generic identifier of a station.
   */
  public abstract String getGenericIdentifier();

  /**
   * Add a station adjacent to this station.
   */
  public void addAdjacentStation(Station station) {
    this.adjacentStations.add(station);
  }

  /**
   * Check if some station is adjacent to this station.
   *
   * @return - True if the input station is adjacent.
   */
  public boolean isAdjacentToStation(Station station) {
    return this.adjacentStations.contains(station);
  }

  /**
   * Return the route of this station.
   */
  public Route getRoute() {
    return this.route;
  }

  /**
   * Return the name of this station.
   */
  public String getName() {
    return this.name;
  }

  /**
   * return the name and the generic identifier of a Station.
   *
   * @return - the concatenated String representation of a Station.
   */
  @Override
  public String toString() {
    return this.name + " " + getGenericIdentifier();
  }

}
