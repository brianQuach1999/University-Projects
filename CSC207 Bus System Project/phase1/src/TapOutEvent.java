/* Brian */

/**
 * A tap out at a <code>BusStation</code> or <code>SubwayStation</code>.
 */
public class TapOutEvent extends TapEvent {

  public TapOutEvent(Station station, TransitDate date) {
    super(station, date);
  }

  @Override
  public String toString() {
    return "Tap Out at " + getStation().toString()
        + " (" + getTransitDate().toDateTimeString() + ")";
  }
}
