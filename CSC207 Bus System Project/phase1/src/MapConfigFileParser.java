/* Danya */

import java.util.ArrayList;

/**
 * Class for parsing this transit system's map configuration.
 */
public class MapConfigFileParser extends ConfigFileParser {

  /**
   * Map object to which the parsed configuration pertains.
   */
  private Map map;

  public MapConfigFileParser(String filename, Map map) {
    super(filename);
    this.map = map;
  }

  /**
   * Parses a line this object's configuration file.
   *
   * @param line line in configuration file to be parsed
   */
  @Override
  protected void parseLine(String line) {
    String[] data = line.split(": ");
    switch (data[0]) {
      case "Route":
        addRouteData(data[1].split(", "));
        break;
      case "Station":
        addStationData(data[1].split(", "));
        break;
      case "Hub":
        addHubData(data[1]);
        break;
      default:
        System.out.println("Warning: "
            + "Could not parse the following map configuration: " + line);
    }
  }

  /**
   * Adds route data to this object's map.
   *
   * @param data parameters of new route
   */
  private void addRouteData(String[] data) {
    String type = data[0];
    String name = data[1];
    this.map.addRouteByNameAndType(name, type);
  }

  /**
   * Adds station data to this object's map.
   *
   * @param data parameters of new station
   */
  private void addStationData(String[] data) {
    String routeType = data[0];
    String routeName = data[1];
    String stationName = data[2];
    Route route = map.getRouteByNameAndType(routeName, routeType);
    route.addStationByName(stationName);
  }

  /**
   * Adds hub data to this object's map. In effect, this just means that the stations in the hub are
   * deemed adjacent and thus it is legal to transfer between them.
   *
   * @param data string of pipe-separated stations to be made adjacent
   */
  private void addHubData(String data) {
    ArrayList<Station> stations = new ArrayList<>();
    String[] stationStrings = data.split(" \\| ");
    for (String stationString : stationStrings) {
      String[] stationData = stationString.split(", ");
      String routeType = stationData[0];
      String routeName = stationData[1];
      String stationName = stationData[2];
      Station station = map.getStationByNameAndRoute(stationName, routeName, routeType);
      stations.add(station);
    }
    Map.makeAdjacent(stations);
  }
}
