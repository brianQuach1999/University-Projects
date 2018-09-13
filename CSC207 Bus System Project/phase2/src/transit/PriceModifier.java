package transit;

import java.io.Serializable;
import java.util.ArrayList;
import util.FormattedDate;

public abstract class PriceModifier implements PriceModifiable, Expirable, Serializable {

  private FormattedDate startDate;
  private FormattedDate endDate;
  private String name;
  int numberOfTimesUsed;
  // maximum number of times the price modifier can be used; -1 if there is no limit;
  private final int USAGE_LIMIT;
  private ArrayList<FormattedDate> datesUsed;

  public PriceModifier(FormattedDate startDate, FormattedDate endDate, int USAGE_LIMIT,
      String name) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.USAGE_LIMIT = USAGE_LIMIT;
    numberOfTimesUsed = 0;
    this.name = name;
    datesUsed = new ArrayList();
  }

  /**
   * Returns the list of <code>FormattedDate</code> this price modifier was used.
   * @return the list of <code>FormattedDate</code> this price modifier was used
   */
  public ArrayList<FormattedDate> getDatesUsed() {
    return datesUsed;
  }

  /**
   * Appends a <code>FormattedDate</code> representing  another date this modifier has been used, to the list of
   * dates used.
   * @param dateUsed a <code>FormattedDate</code> representing  another date this modifier has been used
   */
  public void addDateUsed(FormattedDate dateUsed) {
    this.datesUsed.add(dateUsed);
  }

  @Override
  public abstract double modifyPrice(double rawPrice, FormattedDate dateUsed);

  @Override
  public boolean isValid(FormattedDate dateUsed) {
    return (dateUsed.compareTo(startDate) > 0
        & dateUsed.compareTo(endDate) <= 0);
  }

  @Override
  public String toString() {
    String ret = name;
    ret += '\n' + "Start date: " + startDate;
    ret += '\n' + "Expiry date:" + endDate;
    ret += '\n' + "Number of Times Used" + numberOfTimesUsed;
    ret += '\n' + "Usage Limit" + USAGE_LIMIT;
    return ret;
  }
}
