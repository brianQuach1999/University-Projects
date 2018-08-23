/* Brian */

/**
 * Exception thrown to indicate when a <code>TapEvent</code> is an invalid tap for a specific
 * <code>Trip</code>.
 */
public class TripInvalidTapEventException extends Exception {

  public TripInvalidTapEventException() {
    super("The tap event is an invalid input for this specific trip.");

  }
}
