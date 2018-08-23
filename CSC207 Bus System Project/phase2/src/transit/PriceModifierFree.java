package transit;

import java.util.Date;
import util.FormattedDate;

public class PriceModifierFree extends PriceModifier {

  public PriceModifierFree() {
    super(new FormattedDate(new Date(0)), new FormattedDate(new Date(0)).addTime(Integer.MAX_VALUE),
        Integer.MAX_VALUE, "Free");
  }

  @Override
  public double modifyPrice(double rawPrice, FormattedDate dateUsed) {
    return 0;
  }

  @Override
  public String toString() {
    return "Wow! The Free Forever Pass";
  }
}
