package transit;

import util.FormattedDate;

/**
 * An interface for objects that can change doubles -> specifically prices
 */
public interface PriceModifiable {

  /**
   * Returns a double representing the modified price of a price.
   * @param rawPrice the given double representing the price
   * @param dateUsed the date this modifier is used
   * @return the modified price, as a double
   */
  double modifyPrice(double rawPrice, FormattedDate dateUsed);


}
