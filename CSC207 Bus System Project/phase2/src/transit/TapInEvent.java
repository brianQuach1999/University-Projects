package transit;/* Brian */

import util.FormattedDate;

/**
 * A tap in at a <code>BusStation</code> or <code>SubwayStation</code>.
 */
public class TapInEvent extends TapEvent {

  public TapInEvent(Station station, FormattedDate date, Card card) {
    super(station, date, card);
  }
  /**
   * returns string representation of class.
   *
   * @return - string representation of class
   */
  @Override
  public String toString() {
    return "Tap In at " + getStation().toString()
        + " (" + getTransitDate().toDateTimeString() + ")";
  }
}
