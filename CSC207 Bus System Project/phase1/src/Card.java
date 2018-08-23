/* Dan */

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that models the functionality of a transit Card.
 *
 * @author group 0136
 */
public class Card {

  // Instance variables storing information on the card.
  private static final int MAX_CHARGE = 6;
  private double balance = 19;
  private ArrayList<Trip> trips = new ArrayList<>(); // Array list of trips recorded onto card.
  // Array list of invalid tap dates recorded onto card.
  private ArrayList<TransitDate> invalidTapEventDates = new ArrayList<>();
  private Trip activeTrip = null; // Trip Card is actively going through; null if no trip is active.
  private boolean isActive = true;
  private int cardId;

  /**
   * A constructor for the card class that sets the id.
   */
  public Card(int id) {
    this.cardId = id;
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
   * Returns an array list of days that an invalid tap occurred.
   *
   * @return - array list of days that an invalid tap occurred.
   */
  public ArrayList<TransitDate> getInvalidTapEventDates() {
    return invalidTapEventDates;
  }

  /**
   * Deactivated card so it cannot be used to Tap.
   */
  public void deactivate() {
    this.isActive = false;
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
   * Increases card balance by 10 dollars.
   */
  public void addTenDollars() {
    this.balance += 10;
  }

  /**
   * Increases card balance by 20 dollars.
   */
  public void addTwentyDollars() {
    this.balance += 20;
  }

  /**
   * Increases card balance by 50 dollars.
   */
  public void addFiftyDollars() {
    this.balance += 50;
  }

  /**
   * Registers and records an invalid tap.
   *
   * <p>In the case of an invalid tap, the information is passed onto StatisticsManager, active
   * trip is turned null and MAX_Charge is taken off balance.
   *
   * @param tapEvent - tap event which was invalid.
   */
  private void addInvalidTap(TapEvent tapEvent) {
    this.invalidTapEventDates.add(tapEvent.getTransitDate());
    StatisticsManager.addInvalidTapEvent(tapEvent.getTransitDate());
    this.balance -= MAX_CHARGE;
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
  public void tapIn(Station station, TransitDate date)
      throws InsufficientFundsException, TapDeactivatedCardsException, IllegalTapLocationException {

    // check if card is active
    if (!this.isActive) {
      throw new TapDeactivatedCardsException();
    }
    // check if card has sufficient funds
    if (this.balance < 0) {
      throw new InsufficientFundsException();
    }

    // create tap in event
    TapInEvent tapInEvent = new TapInEvent(station, date);

    // create new trip, if there isn't a currently active trip
    if (this.activeTrip == null) {
      this.activeTrip = new Trip();
      StatisticsManager.addTrip(this.activeTrip);
      this.trips.add(this.activeTrip);
    }

    // register the tap to the active trip
    double price = 0;
    try {
      price = activeTrip.registerTapInEvent(tapInEvent);
    } catch (TripUnnaturalTapSequenceException e) {
      // this tap took a place at a nonsensical location
      addInvalidTap(tapInEvent);
      throw new IllegalTapLocationException();
    } catch (TripInvalidTapEventException f) {
      // this tap is not contiguous (in time and space) to the previous tap
      this.activeTrip = null;
      tapIn(station, date);
    }

    // charge the card the price of this tap
    balance -= price;
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
  public void tapOut(Station station, TransitDate date)
      throws TapDeactivatedCardsException, IllegalTapLocationException {

    // check if card is active
    if (!this.isActive) {
      throw new TapDeactivatedCardsException();
    }

    // create tap out event
    TapOutEvent tapOutEvent = new TapOutEvent(station, date);

    // check if there is a currently active trip
    if (this.activeTrip == null) {
      // cannot tap out without a currently active trip
      addInvalidTap(tapOutEvent);
      throw new IllegalTapLocationException();
    }

    // register tap out event with current trip
    double price;
    try {
      price = activeTrip.registerTapOutEvent(tapOutEvent);
    } catch (TripUnnaturalTapSequenceException e) {
      addInvalidTap(tapOutEvent);
      throw new IllegalTapLocationException();
    }

    // charge the card the price of this tap
    balance -= price;
  }

  /**
   * Returns an array list of the last 3 trips taken by customer account.
   *
   * @return - array list of the last 3 trips taken by customer account.
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
   * Returns string representation of customerAccountClass.
   *
   * @return - string representation of customerAccountClass.
   */
  @Override
  public String toString() {
    String ret = "Card " + Integer.toString(this.cardId) + " ($" + Double.toString(this.balance);
    if (this.isActive) {
      ret += ")";
    } else {
      ret += ", deactivated)";
    }
    return ret;
  }
}
