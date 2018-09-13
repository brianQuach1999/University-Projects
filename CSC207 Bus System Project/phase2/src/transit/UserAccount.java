package transit; /*  Dan */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import util.FormattedDate;
import util.PrettyList;

/**
 * Class that models the functionality of a transit User account.
 *
 * @author group 0136
 */
public class UserAccount implements Serializable {

  // Instance variables storing information on user.
  private String name;
  private String email;
  private String password;
  private ArrayList<Card> cards = new ArrayList<>();
  private boolean active = true;
  private boolean isAdmin;

  /** A constructor for the UserAccount class that sets a name, email. */
  public UserAccount(String name, String email, boolean isAdmin) {
    this.name = name;
    this.email = email;
    this.password = "root"; // start with default password
    this.isAdmin = isAdmin;
  }
  /**
   * A method that returns if user is admin
   *
   * @return boolean on if user is admin
   */
  public boolean isAdmin() {
    return isAdmin;
  }
  /**
   * A method that sets user as admin
   *
   * @return boolean on if user is admin
   */
  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }
  /** A method that deactivates an account */
  public void deactivateAccount() {
    this.active = false;
  }
  /** A method that activates an account */
  public void activateAccount() {
    this.active = true;
  }
  /**
   * A that checks if user has cards
   *
   * @return boolean on if user has cards.
   */
  public boolean hasCard() {
    return cards.size() != 0;
  }

  /**
   * Returns the Card of User account given the cards id.
   *
   * @param id - the id of the card which will be returned.
   * @return - Card with the id matching the given id or null if no such card is found.
   */
  public Card getCard(int id) {

    for (Card card : this.cards) {
      if (id == card.getCardId()) {
        return card;
      }
    }
    return null;
  }

  public boolean validatePassword(String password) {
    return this.password.equals(password);
  }

  /**
   * A getter which returns an array list of cards belonging to the Users account.
   *
   * @return - Array list of cards belonging to the Users account.
   */
  public ArrayList<Card> getCards() {
    return cards;
  }

  /**
   * A getter which returns the name of the User account.
   *
   * @return - name of the User account.
   */
  public String getName() {
    return name;
  }

  /**
   * A setter which changes the name of the User account.
   *
   * @param name - name of the User account.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * A getter which returns the email of the User account.
   *
   * @return - email of the User account.
   */
  public String getEmail() {
    return email;
  }
  /**
   * A setter which changes the email of the User account.
   *
   * @param email - email of the User account.
   */
  public void setEmail(String email) {
    this.email = email;
  }
  /**
   * A getter which returns the password of the User account.
   *
   * @return - password of the User account.
   */
  public String getPassword() {
    return password;
  }
  /**
   * A setter which changes the password of the User account.
   *
   * @param password - name of the User account.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns an array list of trips taken by the user.
   *
   * @return - array list of trips taken by the user.
   */
  public ArrayList<Trip> listAllTrips() {
    ArrayList<Trip> trips = new ArrayList<>();
    for (Card card : cards) {
      trips.addAll(card.getTrips());
    }
    return trips;
  }

  public ArrayList<Transaction> listAllTransactions() {
    ArrayList<Transaction> transactions = new ArrayList<>();
    for (Card card : cards) {
      transactions.addAll(card.getTransactions());
    }
    return transactions;
  }

  /**
   * Adds new card to the users account array list.
   *
   * @param card - Card of which to add to users accounts array list of cards.
   */
  public void addCard(Card card) {
    if (!this.cards.contains(card)) {
      this.cards.add(card);
    }
  }

  /**
   * Removes new card to the users account array list.
   *
   * @param card - Card of which to remove to users accounts array list of cards.
   */
  public void removeCard(Card card) {
    this.cards.remove(card);
  }

  /**
   * Returns an array list of the last 3 trips taken by user account.
   *
   * @return - array list of the last 3 trips taken by user account.
   */
  public ArrayList<Trip> calculateRecentTrips() {
    ArrayList<Trip> trips = listAllTrips();
    Collections.sort(trips);
    if (trips.size() <= 3) {
      return trips;
    } else {
      return new ArrayList<>(trips.subList(trips.size() - 3, trips.size()));
    }
  }

  /**
   * Returns the average monthly cost of user.
   *
   * @return - average monthly cost of user.
   */
  public double calculateAverageMonthlyCost() {
    // array lists needed to track users data.
    ArrayList<FormattedDate> months = new ArrayList<>();
    ArrayList<Double> costs = new ArrayList<>();
    ArrayList<Transaction> transactions = listAllTransactions();

    if (transactions.size() == 0) {
      return 0;
    }

    for (Transaction transaction : transactions) {
      boolean amountAdded = false;
      for (int j = 0; j < months.size(); j++) {
        if (transaction.getDate().inSameMonth(months.get(j))) {
          costs.set(j, costs.get(j) + transaction.getAmount());
          amountAdded = true;
          break;
        }
      }
      if (!amountAdded) {
        months.add(transaction.getDate());
        costs.add(transaction.getAmount());
      }
    }

    // return average
    double sum = 0;
    for (double cost : costs) {
      sum += cost;
    }
    if (sum == 0) {
      return 0;
    }
    return sum / costs.size();
  }

  /**
   * Returns string representation of userAccountClass.
   *
   * @return - string representation of userAccountClass.
   */
  @Override
  public String toString() {

    PrettyList<Card> prettyCards = new PrettyList<>(cards, "Cards");

    String st =
        "Name: " + this.name + " | Email: " + this.email + " | Cards: " + prettyCards.toString();

    if (isAdmin) {
      st += " (Admin)";
    }
    return st;
  }
}
