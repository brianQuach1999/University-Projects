package transit.simplemodel;

import javafx.beans.property.SimpleStringProperty;
import transit.Station;
import util.PrettyList;

public class SimpleStation {

  private SimpleStringProperty name;
  private SimpleStringProperty type;
  private SimpleStringProperty route;
  private SimpleStringProperty adjacent;

  public SimpleStation(Station station) {
    name = new SimpleStringProperty(station.getName());
    route = new SimpleStringProperty(station.getRoute().getName());
    type = new SimpleStringProperty(station.getGenericIdentifier());
    PrettyList<Station> adjacentStations = new PrettyList<>(station.getAdjacent(), "Stations");
    adjacent = new SimpleStringProperty(adjacentStations.toString());
  }

  public String getName() {
    return name.get();
  }

  public String getType() {
    return type.get();
  }

  public String getRoute() {
    return route.get();
  }

  public String getAdjacent() {
    return adjacent.get();
  }
}
