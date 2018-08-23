package transit;

import java.util.Date;
import util.FormattedDate;

public class PriceModifierOntarioTax extends PriceModifier {

  private final double TAX_MULTIPLIER = 1.15;

  public PriceModifierOntarioTax() {
    super(new FormattedDate(new Date(0)), new FormattedDate(new Date(0)).addTime(Integer.MAX_VALUE),
        Integer.MAX_VALUE, "Ontario Tax");
  }

  @Override
  public double modifyPrice(double rawPrice, FormattedDate dateUsed) {
    if (isValid(dateUsed)) {
      numberOfTimesUsed++;
      rawPrice = rawPrice * TAX_MULTIPLIER;
      this.addDateUsed(dateUsed);
    }
    return rawPrice;
  }

  @Override
  public String toString() {
    return "Ontario Tax";
  }
}
