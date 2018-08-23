package transit;

import util.FormattedDate;

/**
 * A price modifier that increases the price by $1 for 30 days
 */
public class PriceModifierCharityPass extends PriceModifier {

  public PriceModifierCharityPass(FormattedDate date) {
    super(date, date.addTime(30), Integer.MAX_VALUE, "Charity Pass");
  }


  @Override
  public double modifyPrice(double rawPrice, FormattedDate dateUsed) {
    if (isValid(dateUsed)) {
      numberOfTimesUsed++;
      rawPrice += 1;
      this.addDateUsed(dateUsed);
    }
    return rawPrice;
  }

  @Override
  public String toString() {
    return "The Do-Good Pass ($1 to Charity)";
  }
}
