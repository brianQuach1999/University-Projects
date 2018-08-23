package transit;/* Brian */

import util.FormattedDate;

/**
 * A tap out at a <code>BusStation</code> or <code>SubwayStation</code>.
 */
public class TapOutEvent extends TapEvent {

  public TapOutEvent(Station station, FormattedDate date, Card card) {
    super(station, date, card);
  }
  /**
   * returns string representation of class.
   *
   * @return - string representation of class
   */
  @Override
  public String toString() {
    return "Tap Out at " + getStation().toString()
        + " (" + getTransitDate().toDateTimeString() + ")";
  }
}
