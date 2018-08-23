package transit;/* Loic */

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that models the overall map of the transit system.
 *
 * @author group 0136
 */
public class Map implements Serializable {

  private final ArrayList<Route> routes = new ArrayList<>();

  /**
   * Add a route the map.
   */
  public void addRoute(Route route) {
    this.routes.add(route);
  }

  /**
   * Get a list of routes on the map.
   *
   * @return - ArrayList of Routes
   */
  public ArrayList<Route> getRoutes() {
    return this.routes;
  }

  /**
   * Check if a route is a Bus Route or a Subway Route.
   *
   * @return - returns True if the Route is either a Bus Route or a Subway Route.
   */
  private boolean routeIsType(Route route, String type) {
    return (((type.equals("Subway")) && route instanceof SubwayRoute)
        || ((type.equals("Bus")) && route instanceof BusRoute));
  }

  /**
   * Returns the desired Route from the list of routes on the map. Returns null if such route
   * doesn't exist. The name of the station, as well as its type needs to be specified.
   *
   * @return - the wanted Route or null if it doesn't exist.
   */
  public Route getRouteByNameAndType(String name, String type) {
    for (Route route : routes) {
      if (this.routeIsType(route, type) && route.getName().equals(name)) {
        return route;
      }
    }
    return null;
  }

  /**
   * Add a Route to the list of current Routes on the map.
   */
  public void addRouteByNameAndType(String name, String type) {
    Route newRoute;
    if (type.equals("Subway")) {
      newRoute = new SubwayRoute(name);
    } else {
      newRoute = new BusRoute(name);
    }
    addRoute(newRoute);
  }

  /**
   * Returns the desired station based on the station's name, the route's name, and the route's
   * type. Returns null if it does not exists.
   *
   * @return - The desired station or null if it does not exist.
   */
  public Station getStationByNameAndRoute(String stationName, String routeName, String routeType) {
    Route route = this.getRouteByNameAndType(routeName, routeType);
    if (route == null) {
      return null;
    }
    return route.getStationByName(stationName);
  }

  public ArrayList<Station> getStations() {
    ArrayList<Station> stations = new ArrayList<>();
    for (Route route : routes) {
      stations.addAll(route.getStations());
    }
    return stations;
  }

  /**
   * Bundle stations as adjacent stops.
   */
  public void createAdjacency(ArrayList<Station> stations) {
    for (Station station1 : stations) {
      for (Station station2 : stations) {
        if (!station1.equals(station2)) {
          station1.addAdjacentStation(station2);
        }
      }
    }
  }

  /**
   * Bundle stations as adjacent stops.
   */
  public void removeAdjacency(ArrayList<Station> stations) {
    for (Station station1 : stations) {
      for (Station station2 : stations) {
        station1.removeAdjacentStation(station2);
      }
    }
  }
}
