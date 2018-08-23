/* Loic */

import java.lang.Math;
import java.util.ArrayList;

/**
 * An abstract class that models a generic route.
 *
 * @author group 0136
 */
public abstract class Route {

  private final String name;
  private ArrayList<Station> stations = new ArrayList<>();

  /**
   * Route constructor that does not require stations on its route.
   */
  public Route(String name) {
    this.name = name;
  }

  /**
   * Route constructor that is initialized with a route of stations.
   */
  public Route(String name, ArrayList<Station> stations) {
    this.name = name;
    this.stations = stations;
  }

  /**
   * Add a station by name.
   */
  public abstract void addStationByName(String name);

  /**
   * Get the generic identifier.
   */
  public abstract String getGenericIdentifier();

  /**
   * Get the name of the station.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Add a station.
   */
  public void addStation(Station station) {
    this.stations.add(station);
  }

  /**
   * Set all stations at once.
   */
  public void setStations(ArrayList<Station> stations) {
    this.stations = stations;
  }

  /**
   * Get a station by its index.
   */
  public Station getStationByIndex(int index) {
    return this.stations.get(index);
  }

  /**
   * Get a station by its name.
   */
  public Station getStationByName(String name) {
    for (Station station : stations) {
      if (station.getName().equals(name)) {
        return station;
      }
    }
    return null;
  }

  /**
   * Return the distance between two stations.
   *
   * @return - the distance
   */
  public int getRouteLength(Station s1, Station s2) {
    int s1Index = this.stations.indexOf(s1);
    int s2Index = this.stations.indexOf(s2);
    return Math.abs(s1Index - s2Index);
  }

  /**
   * Returns a String version of the stations within this route constituting of its generic
   * identifier and its name.
   *
   * @return - The concatenated String.
   */
  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder(this.name + " " + getGenericIdentifier() + " (");
    for (Station station : this.stations) {
      ret.append(station.toString()).append(", ");
    }
    ret = new StringBuilder(ret.substring(0, ret.length() - 2));
    return ret + ")";
  }
}
