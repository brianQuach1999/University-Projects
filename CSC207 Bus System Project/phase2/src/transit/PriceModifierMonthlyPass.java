package transit;

import util.FormattedDate;

/**
 * A PriceModifier that allows 1 free trip a day, for 30 days
 */
public class PriceModifierMonthlyPass extends PriceModifier {

  public PriceModifierMonthlyPass(FormattedDate date) {
    super(date, date.addTime(30), Integer.MAX_VALUE, "Monthly Pass");
  }

  @Override
  public double modifyPrice(double rawPrice, FormattedDate dateUsed) {
    if (isValid(dateUsed)) {
      numberOfTimesUsed++;
      rawPrice = 0;
      this.addDateUsed(dateUsed);
    }
    return rawPrice;
  }
@Override
  public boolean isValid(FormattedDate date) {
    return !this.usedToday(date) && super.isValid(date);
  }

  /**
   * Returns if not this price modifier has been used today.
   * @param today the date today
   * @return if not this price modifier has been used today
   */
  private boolean usedToday(FormattedDate today) {
    for (FormattedDate dateUsed : this.getDatesUsed()) {
      if (dateUsed.onSameDay(today)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "The One-Way Commute, 1 Month (1 Free Per Day)";
  }
}
