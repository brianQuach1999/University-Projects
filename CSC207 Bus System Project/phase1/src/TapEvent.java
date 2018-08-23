/* Brian */

/**
 * A Tap at a <code>BusStation</code> or <code>SubwayStation</code>.
 */
public abstract class TapEvent {

  private TransitDate date;
  private Station station;

  public TapEvent(Station station, TransitDate date) {
    this.station = station;
    this.date = date;
  }

  /**
   * Returns the <code>Date</code> this <code>TapEvent</code> was made.
   *
   * @return the <code>Date</code> this <code>TapEvent</code> was made.
   */
  public TransitDate getTransitDate() {
    return date;
  }

  /**
   * Returns the <code>Station</code> at which this <code>TapEvent</code> was made.
   *
   * @return the <code>Station</code> at which this <code>TapEvent</code> was made.
   */
  public Station getStation() {
    return station;
  }
}
