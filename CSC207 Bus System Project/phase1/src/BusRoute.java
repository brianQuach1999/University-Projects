/* Loic */

/**
 * Class modeling a Bus Route.
 *
 * @author group 0136
 */
public class BusRoute extends Route {

  /**
   * BusRoute constructor that does not require stations on its route.
   */
  public BusRoute(String name) {
    super(name);
  }

  /**
   * returns the generic identifier of a Bus Route.
   *
   * @return - the generic identifier
   */
  @Override
  public String getGenericIdentifier() {
    return "Bus Route";
  }

  /**
   * Add a station to the Bus Route.
   */
  @Override
  public void addStationByName(String name) {
    this.addStation(new BusStation(name, this));
  }
}
