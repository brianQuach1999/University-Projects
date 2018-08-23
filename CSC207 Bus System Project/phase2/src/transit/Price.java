package transit;/* brian */

import java.io.Serializable;
import util.FormattedDate;

public class Price implements Serializable {

  private double rawPrice;
  private double finalPrice;
  private double maxFinalPrice;
  private PriceModifier priceModifier;
  private final PriceModifier ONTARIO_TAX;
  private boolean taxOn;
  private boolean hasFinalPrice = false;

  public Price() {
    this.rawPrice = 0;
    ONTARIO_TAX = new PriceModifierOntarioTax();
    priceModifier = null;
    maxFinalPrice = Double.MAX_VALUE;
    taxOn = false;
  }

  public Price(
          FormattedDate today, double rawPrice, PriceModifier priceModifier, double maxFinalPrice, boolean taxOn) {
    this.rawPrice = rawPrice;
    ONTARIO_TAX = new PriceModifierOntarioTax();
    this.priceModifier = priceModifier;
    this.maxFinalPrice = maxFinalPrice;
    this.taxOn = taxOn;
  }

  /**
   * Sets the final price of this Price by applying the price modifiers. The final price will
   * adjusted downward to be the maxFinalPrice if need be.
   *
   * @param date the date the price modifiers are applied
   */
  public void applyPriceModifiers(FormattedDate date) {
    if (rawPrice != 0) {
      finalPrice = rawPrice;
      if (priceModifier != null) {
        if (priceModifier.isValid(date)) {
          finalPrice = priceModifier.modifyPrice(finalPrice, date);
        }
      }
      // apply ontario tax
      if (taxOn & ONTARIO_TAX.isValid(date)) {
        finalPrice = ONTARIO_TAX.modifyPrice(finalPrice, date);
      }
      finalPrice = Math.min(finalPrice, maxFinalPrice);
      hasFinalPrice = true;
    }
  }

  /***
   * Returns the final price of this <code>Price</code>.
   * @return the final price of this <code>Price</code>
   */
  public double getFinalPrice() {
    return finalPrice;
  }

  /**
   * Returns whether or not the finalPrice of this object has been purposefully initialized (not the default value).
   * @return whether or not the finalPrice of this object has been purposefully initialized (not the default value)
   */
  public boolean hasFinalPrice() {
    return hasFinalPrice;
  }

  /**
   * Sets the final price of this <code>Price</code>.
   * @param finalPrice the final price of this <code>Price</code>.
   */
  public void setFinalPrice(double finalPrice) {
    this.finalPrice = finalPrice;
    hasFinalPrice = true;
  }
  /***
   * Returns the initial price of this <code>Price</code>.
   * @return the initial price of this <code>Price</code>
   */
  public double getRawPrice() {
    return rawPrice;
  }

  /**
   * Sets the <code>PriceModifier</code> of this Price.
   * @param priceModifier the <code>PriceModifier</code> of this Price
   */
  public void setPriceModifier(PriceModifier priceModifier) {
    this.priceModifier = priceModifier;
  }
}
