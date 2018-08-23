package transit.simplemodel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import transit.TapEvent;

public class SimpleTap {

  private SimpleStringProperty date;
  private SimpleStringProperty station;
  private SimpleStringProperty card;
  private SimpleBooleanProperty flagged;

  public SimpleTap(TapEvent tap) {
    date = new SimpleStringProperty(tap.getTransitDate().toDateTimeString());
    station = new SimpleStringProperty(tap.getStation().toString());
    card = new SimpleStringProperty(tap.getCard().getNickname());
    flagged = new SimpleBooleanProperty(tap.isFlagged());
  }

  public String getDate() {
    return date.get();
  }

  public String getStation() {
    return station.get();
  }

  public String getCard() {
    return card.get();
  }

  public boolean getFlagged() {
    return flagged.get();
  }

}
