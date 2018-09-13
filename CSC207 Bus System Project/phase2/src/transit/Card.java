package transit;/* Dan */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import util.EasyLogger;
import util.FormattedDate;

/**
 * Class that models the functionality of a transit Card.
 *
 * @author group 0136
 */
public class Card implements Serializable {

  // Instance variables storing information on the card.
  private double balance = 19;
  private ArrayList<Trip> trips = new ArrayList<>(); // Array list of trips recorded onto card.
  private final ArrayList<Transaction> transactions = new ArrayList<>();
  private Trip activeTrip = null; // Trip Card is actively going through; null if no trip is active.
  private boolean isActive = true;
  private int cardId;
  private TransitFareManager transitFareManager;
  private PriceModifier priceModifier;
  private String nickname;
  private boolean taxOn;

  private transient EasyLogger logger = new EasyLogger("card");

  /**
   * A constructor for the card class that sets the id.
   */
  public Card(int id, TransitFareManager transitFareManager) {
    this(id, transitFareManager, null);
  }

  public Card(String nickname, int id, TransitFareManager transitFareManager) {
    this(nickname, id, transitFareManager, null);
  }

  public Card(int id, TransitFareManager transitFareManager, PriceModifier priceModifier) {
    this("Card " + id, id, transitFareManager, priceModifier);
  }

  public Card(String nickname, int id, TransitFareManager transitFareManager,
      PriceModifier priceModifier) {
    this.cardId = id;
    this.transitFareManager = transitFareManager;
    this.priceModifier = priceModifier;
    this.nickname = nickname;
  }

  /**
   * Returns if taxes are applied to taps on this card.
   * @return if taxes are applied to taps on this card
   */
    public boolean isTaxOn() {
        return taxOn;
    }

  /**
   * Sets if taxes are applied on the succeeding taps of this card.
   * @param taxOn if taxes are applied on the succeeding taps of this card.
   */
  public void setTaxOn(boolean taxOn) {
        this.taxOn = taxOn;
    }

  /**
   * Returns the nickname of this card.
   * @return he nickname of this card
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * Sets the nickname of this card.
   * @param nickname the nickname of this card.
   */
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  /**
   * A getter which returns the id of the card.
   *
   * @return - id of the card.
   */
  public int getCardId() {
    return cardId;
  }

  /**
   * A getter which returns an array list of trips registered on card.
   *
   * @return - Array list of trips registered on card.
   */
  public ArrayList<Trip> getTrips() {
    return trips;
  }

  /**
   * Gets the price modifier of this card.
   * @return the price modifier of this card
   */
  public PriceModifier getPriceModifier() {
    return priceModifier;
  }

  /**
   * Deactivate card so it cannot be used to Tap.
   */
  public void deactivate() {
    this.isActive = false;
  }

  /**
   * Activate card so it can be used to Tap.
   */
  public void activate() {
    this.isActive = true;
  }

  /**
   * Returns if this card is active.
   * @return if this card is active
   */
  public boolean isActive() {
    return isActive;
  }

  /**
   * Sets the activity of this card.
   * @param active the activity of this card.
   */
  public void setActive(boolean active) {
    this.isActive = active;
  }

  /**
   * A getter which returns the balance of the card.
   *
   * @return - balance of the card.
   */
  public double getBalance() {
    return this.balance;
  }

  /**
   * Returns the balance of this card as a string.
   * @return the balance of this card as a string.
   */
  public String getBalanceString() {
    if (balance < 0) {
      return "- $" + String.format("%.2f", -balance);
    } else {
      return "$" + String.format("%.2f", balance);
    }
  }

  /**
   * Increases the card balance by the given amount.
   *
   * @param amount the amount to increase the card balance by
   */
  public void addAmount(double amount) {
    this.balance += amount;
  }

  /**
   * Decrease the balance of this card by `amount`.
   *
   * @param amount amount to be removed from balance
   */
  protected void removeFunds(double amount) {
    this.balance -= amount;
  }

  /**
   * Create transaction with the end goal of decreasing the balance of this card by `amount`.
   *
   * @param price the <code>Price</code>
   * @param date date of transaction
   */
  private void createTransaction(Price price, FormattedDate date) {
    this.transitFareManager.createTransaction(this, price, date);
  }

  /**
   * Sets the price modifier of this card.
   * @param priceModifier the price modifier of this card
   */
  public void setPriceModifier(PriceModifier priceModifier) {
    this.priceModifier = priceModifier;
  }

  /**
   * Returns the list of transactions of this card.
   * @return the list of transactions of this card
   */
  public ArrayList<Transaction> getTransactions() {
    return this.transactions;
  }

  /**
   * Registers and records an invalid tap.
   *
   * <p>In the case of an invalid tap, active
   * trip is turned null and MAX_Charge is taken off balance.
   *
   * @param tapEvent - tap event which was invalid.
   */
  private void addInvalidTap(TapEvent tapEvent) {
    tapEvent.flagAsUnnatural();
    Price price = new Price();
    price.setFinalPrice(transitFareManager.MAX_CHARGE);
    createTransaction(price, tapEvent.getTransitDate());
    this.activeTrip = null;
  }

  /**
   * Registers a tap in event onto card.
   *
   * <p>When registering a tap in event fist balance and card activity are checked. Then if they
   * are legal a new tapInEvent is created and the tap date and location are stored. If card not yet
   * part of an active trip then a new trip is created and fare is deducted. If tap is found to be
   * invalid then it is recorded and tap active trip is set to null and a new tapIn is called.
   *
   * @param station - Location of tap
   * @param date - date of the tap.
   * @throws InsufficientFundsException - Insufficient funds on card
   * @throws TapDeactivatedCardsException - deactivated tap is tapped
   * @throws IllegalTapLocationException - tap location of the tap is not legal
   */
  public void tapIn(Station station, FormattedDate date)
      throws InsufficientFundsException, TapDeactivatedCardsException, IllegalTapLocationException {

    if (logger == null) {
      logger = new EasyLogger("card");
    }

    // check if card is active
    if (!this.isActive) {
      logger.log.warning("Cannot tap in with deactivated card.");
      throw new TapDeactivatedCardsException();
    }
    // check if card has sufficient funds
    if (this.balance < 0) {
      logger.log.warning("Cannot tap in due to insufficient funds.");
      throw new InsufficientFundsException();
    }

    // create tap in event
    TapInEvent tapInEvent = new TapInEvent(station, date, this);

    // create new trip, if there isn't a currently active trip
    if (this.activeTrip == null) {
      logger.log.fine("Creating new trip for tap in.");
      this.activeTrip = transitFareManager.createTrip();
      this.trips.add(this.activeTrip);
    }

    // register the tap to the active trip
    Price price = new Price();
    try {
      price = activeTrip.registerTapInEvent(tapInEvent, this);
    } catch (TripUnnaturalTapSequenceException e) {
      // this tap took place at a nonsensical location
      logger.log.warning("This tap sequence is not permitted.");
      addInvalidTap(tapInEvent);
      throw new IllegalTapLocationException();
    } catch (TripInvalidTapEventException f) {
      // this tap is not contiguous (in time and space) to the previous tap
      logger.log
          .warning("This tap in event is not contiguous (in time and space) to the previous tap.");
      this.activeTrip = null;
      tapIn(station, date);
    }

    // charge the card the price of this tap
    if (price.hasFinalPrice()) {
      logger.log.fine("Charging card a final price of " + price.getFinalPrice()
          + " (raw price of " + price.getRawPrice() + ")");
      createTransaction(price, date);
    }
  }

  /**
   * Registers a tap out event onto card.
   *
   * <p>When registering a tap out event fist card activity are checked. Then if they are legal a
   * new tapOutEvent is created and the tap date and location are stored. If card apart of trip
   * which is concluded then fare is deducted from balance. if no trip is active then an illegal tap
   * is recoded.
   *
   * @param station - Location of tap
   * @param date - date of the tap.
   * @throws TapDeactivatedCardsException - deactivated tap is tapped
   * @throws IllegalTapLocationException - tap location of the tap is not legal
   */
  public void tapOut(Station station, FormattedDate date)
      throws TapDeactivatedCardsException, IllegalTapLocationException {

    if (logger == null) {
      logger = new EasyLogger("card");
    }

    // check if card is active
    if (!this.isActive) {
      logger.log.warning("Cannot tap out with deactivated card.");
      throw new TapDeactivatedCardsException();
    }

    // create tap out event
    TapOutEvent tapOutEvent = new TapOutEvent(station, date, this);

    // check if there is a currently active trip
    if (this.activeTrip == null) {
      // cannot tap out without a currently active trip
      logger.log.warning("Cannot tap out without an active trip.");
      addInvalidTap(tapOutEvent);
      throw new IllegalTapLocationException();
    }

    // register tap out event with current trip
    Price price;
    try {
      price = activeTrip.registerTapOutEvent(tapOutEvent, this);
    } catch (TripUnnaturalTapSequenceException e) {
      logger.log.warning("This tap sequence is not permitted.");
      addInvalidTap(tapOutEvent);
      throw new IllegalTapLocationException();
    }

    // charge the card the price of this tap
    if (price.hasFinalPrice()) {
      logger.log.fine("Charging card a final price of " + price.getFinalPrice()
          + " (raw price of " + price.getRawPrice() + ")");
      createTransaction(price, date);
    }
  }

  /**
   * Returns an array list of the last 3 trips taken by user account.
   *
   * @return - array list of the last 3 trips taken by user account.
   */
  public ArrayList<Trip> calculateRecentTrips() {
    Collections.sort(trips);
    if (trips.size() <= 3) {
      return trips;
    } else {
      return new ArrayList<>(trips.subList(trips.size() - 3, trips.size()));
    }
  }

  /**
   * Returns string representation of userAccountClass.
   *
   * @return - string representation of userAccountClass.
   */
  @Override
  public String toString() {
    String ret = this.nickname;
    if (!this.isActive) {
      ret += " (deactivated)";
    }
    return ret;
  }
}
