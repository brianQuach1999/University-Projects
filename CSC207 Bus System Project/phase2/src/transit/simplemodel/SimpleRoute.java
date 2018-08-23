package transit.simplemodel;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import transit.Route;
import transit.Station;

public class SimpleRoute {

  private SimpleStringProperty name;
  private SimpleStringProperty type;
  private SimpleListProperty<Station> stations;

  public SimpleRoute(Route route) {
    name = new SimpleStringProperty(route.getName());
    type = new SimpleStringProperty(route.getGenericIdentifier());
    ObservableList<Station> observableList = FXCollections.observableArrayList(route.getStations());
    stations = new SimpleListProperty<>(observableList);
  }

  public String getName() {
    return name.get();
  }

  public String getType() {
    return type.get();
  }

  public ObservableList getStations() {
    return stations.get();
  }

}
