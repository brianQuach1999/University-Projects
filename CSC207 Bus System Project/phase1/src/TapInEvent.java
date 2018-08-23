/* Brian */

/**
 * A tap in at a <code>BusStation</code> or <code>SubwayStation</code>.
 */
public class TapInEvent extends TapEvent {

  public TapInEvent(Station station, TransitDate date) {
    super(station, date);
  }

  @Override
  public String toString() {
    return "Tap In at " + getStation().toString()
        + " (" + getTransitDate().toDateTimeString() + ")";
  }
}
