package transit; /* Danya */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import util.FormattedDate;

/** Main class of the transit system. */
public class TransitFareManager implements Serializable {

  public static final double MAX_CHARGE = 6;
  /** Map of this transit system. */
  protected Map map;
  /** List of users that have accounts with this transit system. */
  private HashMap<String, UserAccount> users = new HashMap<>();
  /** List of cards, trips and Transactions that have occurred or that have been issued. */
  private ArrayList<Card> cards = new ArrayList<>();

  private ArrayList<Trip> trips = new ArrayList<>();
  private ArrayList<Transaction> transactions = new ArrayList<>();
  /** An constructor for the transitFareManager */
  public TransitFareManager(Map map) {
    this.map = map;
  }
  /**
   * A method that returns an array list of all users.
   *
   * @return array list of all users
   */
  public ArrayList<UserAccount> getUsers() {
    ArrayList<UserAccount> usersList = new ArrayList<>();
    usersList.addAll(users.values());
    return usersList;
  }
  /**
   * A method that returns an array list of all cards.
   *
   * @return array list of all cards
   */
  public ArrayList<Card> getCards() {
    return cards;
  }
  /**
   * A method that returns an array list of all trips.
   *
   * @return array list of all trips
   */
  public ArrayList<Trip> getTrips() {
    return this.trips;
  }
  /**
   * A method that returns an array list of all transactions.
   *
   * @return array list of all transactions
   */
  public ArrayList<Transaction> getTransactions() {
    return this.transactions;
  }
  /**
   * A method that returns the map of transit system
   *
   * @return map of transit system
   */
  public Map getMap() {
    return this.map;
  }

  /**
   * Creates and returns a new user account. Generates ID for new user based on number of existing
   * users.
   *
   * @param name user name
   * @param email user email
   * @param isAdmin a boolean if the user is admin
   * @return new user account object
   */
  public UserAccount createUserAccount(String name, String email, boolean isAdmin) {
    // Increment user id by one for every new user
    UserAccount user = new UserAccount(name, email, isAdmin);
    this.users.put(email, user);
    return user;
  }

  /**
   * Creates and returns a new card object. Generates ID for new card based in the number of
   * existing cards. Adds this new card to user.
   *
   * @param user user to which the card is issued
   * @param nickname - nickname chosen by user
   * @return new card object
   */
  public Card issueCard(UserAccount user, String nickname) {
    // Increment card id by one for every new card
    Card card = new Card(nickname, this.cards.size(), this);
    this.cards.add(card);
    user.addCard(card);
    return card;
  }
  /**
   * Creates and returns a new card object. Generates ID for new card based in the number of
   * existing cards. Adds this new card to user.
   *
   * @param user user to which the card is issued
   * @return new card object
   */
  public Card issueCard(UserAccount user) {
    // Increment card id by one for every new card
    Card card = new Card(this.cards.size(), this);
    this.cards.add(card);
    user.addCard(card);
    return card;
  }
  /**
   * A method that returns the user through the users email
   *
   * @return user of transit system
   */
  public UserAccount getUserByEmail(String email) {
    UserAccount user = this.users.get(email);
    return user;
  }
  /** A method that updates information on user */
  public void updateUser(
      String oldEmail, String name, String newEmail, String password, boolean isAdmin) {
    UserAccount user = this.users.get(oldEmail);
    if (user != null) {
      user.setName(name);
      user.setEmail(newEmail);
      user.setPassword(password);
      user.setAdmin(isAdmin);
    }
    users.remove(oldEmail);
    users.put(newEmail, user);
  }
  /** A method that deletes user */
  public void deleteUser(String email) {
    this.users.remove(email);
  }

  /**
   * Returns a card based on the card's ID. Because the IDs were generated incrementally, a card's
   * ID is also the card object's index in the list this.cards.
   *
   * @param id id of card
   * @return Card of user or null if user has no cards.
   */
  public Card getCardById(int id) {
    for (Card card : cards) {
      if (id == card.getCardId()) {
        return card;
      }
    }
    return null;
  }
  /**
   * A method that creates new trip
   *
   * @return - Trip object
   */
  public Trip createTrip() {
    Trip trip = new Trip(MAX_CHARGE);
    this.trips.add(trip);
    return trip;
  }
  /**
   * A method that creates new transaction
   *
   * @param date - date of transaction
   * @param card - Card that transaction occurs in
   * @param price - price of the transaction
   * @return - transaction object
   */
  public Transaction createTransaction(Card card, Price price, FormattedDate date) {
    Transaction transaction = new Transaction(card, price, date);
    this.transactions.add(transaction);
    return transaction;
  }
}
