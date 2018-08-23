package transit.simplemodel;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import transit.TapEvent;
import transit.Trip;

public class SimpleTrip {

  private SimpleStringProperty startDate;
  private SimpleStringProperty endDate;
  private SimpleStringProperty cost;
  private SimpleListProperty<TapEvent> taps;

  public SimpleTrip(Trip trip) {
    startDate = new SimpleStringProperty(trip.getStartDate().toDateTimeString());
    endDate = new SimpleStringProperty(trip.getEndDate().toDateTimeString());
    cost = new SimpleStringProperty(formatCost(trip.getCost()));
    ObservableList<TapEvent> observableList = FXCollections
        .observableArrayList(trip.getTapEvents());
    taps = new SimpleListProperty<>(observableList);
  }

  public String getStartDate() {
    return startDate.get();
  }

  public String getEndDate() {
    return endDate.get();
  }

  public String getCost() {
    return cost.get();
  }

  public ObservableList<TapEvent> getTaps() {
    return taps.get();
  }

  private String formatCost(double cost) {
    if (cost < 0) {
      return "- $" + String.format("%.2f", -cost);
    } else {
      return "$" + String.format("%.2f", cost);
    }
  }


}
