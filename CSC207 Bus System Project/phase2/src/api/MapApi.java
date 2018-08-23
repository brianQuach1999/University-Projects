package api;

import java.util.ArrayList;
import transit.BusRoute;
import transit.BusStation;
import transit.Route;
import transit.Station;
import transit.StatisticsManager;
import transit.SubwayRoute;
import transit.SubwayStation;
import transit.TransitFareManager;
import transit.simplemodel.SimpleRoute;
import transit.simplemodel.SimpleStation;
import util.EasyLogger;

/**
 * Class to handle map-related API queries.
 */
public class MapApi extends ChildApi {

  public MapApi(TransitFareManager transitFareManager, StatisticsManager statisticsManager,
      EasyLogger logger) {
    super(transitFareManager, statisticsManager, logger);
  }

  /**
   * Get Station object which corresponds to an instance of SimpleStation.
   *
   * @param station SimpleStation object
   * @return corresponding instance of Station
   */
  public Station getStation(SimpleStation station) {
    String routeType = station.getType().equals("Subway Station") ? "Subway" : "Bus";
    return transitFareManager.getMap()
        .getStationByNameAndRoute(station.getName(), station.getRoute(), routeType);
  }

  /**
   * Get complete list of SimpleStation objects.
   */
  public ArrayList<SimpleStation> getStationsSimple() {
    ArrayList<Station> stations = getStations();
    ArrayList<SimpleStation> result = new ArrayList<>();
    for (Station station : stations) {
      result.add(new SimpleStation(station));
    }
    return result;
  }

  /**
   * Get list of SimpleStation objects which are on the route corresponding to SimpleRoute.
   */
  public ArrayList<SimpleStation> getStationsSimple(SimpleRoute route) {
    ArrayList<Station> stations = getStations();
    ArrayList<SimpleStation> result = new ArrayList<>();
    for (Station station : stations) {
      if (station.getRoute().getName().equals(route.getName())
          && station.getRoute().getGenericIdentifier().equals(route.getType())) {
        result.add(new SimpleStation(station));
      }
    }
    return result;
  }

  /**
   * Get complete list of stations.
   */
  public ArrayList<Station> getStations() {
    return transitFareManager.getMap().getStations();
  }

  /**
   * Get list of SimpleRoute object corresponding to all routes.
   */
  public ArrayList<SimpleRoute> getRoutesSimple() {
    ArrayList<Route> routes = getRoutes();
    ArrayList<SimpleRoute> result = new ArrayList<>();
    for (Route route : routes) {
      result.add(new SimpleRoute(route));
    }
    return result;
  }

  /**
   * Get list of all routes.
   */
  public ArrayList<Route> getRoutes() {
    return transitFareManager.getMap().getRoutes();
  }

  /**
   * Get list of routes which are of a type that is compatible with the given station object.
   */
  public ArrayList<Route> getRoutes(Station station) {
    ArrayList<Route> routes = transitFareManager.getMap().getRoutes();
    ArrayList<Route> result = new ArrayList<>();
    for (Route route : routes) {
      if (station instanceof BusStation && route instanceof BusRoute) {
        result.add(route);
      }
      if (station instanceof SubwayStation && route instanceof SubwayRoute) {
        result.add(route);
      }
    }

    return result;
  }

  /**
   * Update station with new values.
   *
   * @param station station to update
   * @param name new name of station
   * @param route new route of station
   * @param adjacentStations new list of adjacent stations
   */
  public void saveStation(Station station, String name, Route route,
      ArrayList<Station> adjacentStations) {
    if (station != null) {
      station.setName(name);
      station.setRoute(route);
      // remove former adjacency
      ArrayList<Station> oldAdjacency = new ArrayList<>();
      oldAdjacency.addAll(station.getAdjacent());
      oldAdjacency.add(station);
      transitFareManager.getMap().removeAdjacency(oldAdjacency);
      // add new adjacency
      ArrayList<Station> newAdjacency = new ArrayList<>();
      newAdjacency.addAll(adjacentStations);
      newAdjacency.add(station);
      transitFareManager.getMap().createAdjacency(newAdjacency);
      logger.log.fine("Successfully updated station.");
    } else {
      logger.log.warning("Failed to update station.");
    }
  }
}
