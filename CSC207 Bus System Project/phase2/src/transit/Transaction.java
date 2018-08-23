package transit;

import java.io.Serializable;
import util.FormattedDate;

public class Transaction implements Serializable {
  //information needed about the transaction
  private final Card card;
  private final Price price;
  private final FormattedDate date;
  /** An constructor for the transaction class */
  public Transaction(Card card, Price price, FormattedDate date) {
    this.card = card;
    this.date = date;
    this.price = price;
    card.removeFunds(price.getFinalPrice());
  }
  /**
   * A method that returns the date of the transaction
   *
   * @return date of transaction
   */
  public FormattedDate getDate() {
    return this.date;
  }
  /**
   * A method that returns the price of the transaction
   *
   * @return price of transaction
   */
  public double getAmount() {
    return this.price.getFinalPrice();
  }
  /**
   * A method that returns the card of the transaction
   *
   * @return card of transaction
   */
  public Card getCard() {
    return card;
  }
}
