/* Loic */

/**
 * Class modeling a Bus station.
 *
 * @author group 0136
 */
public class BusStation extends Station {

  /**
   * Returns the generic identifier of a Bus Stop.
   *
   * @return - the generic identifier
   */
  @Override
  public String getGenericIdentifier() {
    return "Bus Stop";
  }

  public BusStation(String name, Route route) {
    super(name, 2, 0, route);
  }
}
