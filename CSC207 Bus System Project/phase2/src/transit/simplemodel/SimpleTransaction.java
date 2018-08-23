package transit.simplemodel;

import javafx.beans.property.SimpleStringProperty;
import transit.Transaction;

public class SimpleTransaction {

  private SimpleStringProperty card;
  private SimpleStringProperty price;
  private SimpleStringProperty date;

  public SimpleTransaction(Transaction transaction) {
    card = new SimpleStringProperty(transaction.getCard().getNickname());
    price = new SimpleStringProperty(formatPrice(transaction.getAmount()));
    date = new SimpleStringProperty(transaction.getDate().toDateTimeString());
  }

  public String getCard() {
    return card.get();
  }

  public String getPrice() {
    return price.get();
  }

  public String getDate() {
    return date.get();
  }

  private String formatPrice(double cost) {
    if (cost < 0) {
      return "- $" + String.format("%.2f", -cost);
    } else {
      return "$" + String.format("%.2f", cost);
    }
  }

}
