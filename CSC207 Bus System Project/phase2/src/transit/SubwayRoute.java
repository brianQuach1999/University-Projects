package transit;/* Loic */

/**
 * Class modeling a Subway Route.
 *
 * @author group 0136
 */
public class SubwayRoute extends Route {

  public SubwayRoute(String name) {
    super(name);
  }

  /**
   * Get the generic identifier of a Subway Route.
   */
  @Override
  public String getGenericIdentifier() {
    return "Subway Line";
  }

  /**
   * Add a station by its name.
   */
  @Override
  public void addStationByName(String name) {
    this.addStation(new SubwayStation(name, this));

  }
}
