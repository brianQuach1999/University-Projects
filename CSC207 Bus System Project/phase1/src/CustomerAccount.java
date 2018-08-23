/*  Dan */

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that models the functionality of a transit Customer account.
 *
 * @author group 0136
 */
public class CustomerAccount {

  // Instance variables storing information on customer.
  private String name;
  private String email;
  private ArrayList<Card> cards = new ArrayList<>();
  private int id;

  /**
   * A constructor for the CustomerAccount class that sets a name, email and id.
   */
  public CustomerAccount(String name, String email, int id) {
    this.name = name;
    this.email = email;
    this.id = id;
  }

  /**
   * A getter which returns the id of the Customer account.
   *
   * @return - id of the Customer account.
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the Card of Customer account given the cards id.
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

  /**
   * A getter which returns an array list of cards belonging to the Customers account.
   *
   * @return - Array list of cards belonging to the Customers account.
   */
  public ArrayList<Card> getCards() {
    return cards;
  }

  /**
   * A getter which returns the name of the Customer account.
   *
   * @return - name of the Customer account.
   */
  public String getName() {
    return name;
  }

  /**
   * A setter which changes the name of the Customer account.
   *
   * @param name - name of the Customer account.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * A getter which returns the email of the Customer account.
   *
   * @return - email of the Customer account.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns an array list of trips taken by the customer.
   *
   * @return - array list of trips taken by the customer.
   */
  public ArrayList<Trip> listAllTrips() {
    ArrayList<Trip> trips = new ArrayList<>();
    for (Card card : cards) {
      trips.addAll(card.getTrips());
    }
    return trips;
  }

  /**
   * Returns an array list of days that an invalid tap occurred.
   *
   * @return - array list of days that an invalid tap occurred.
   */
  public ArrayList<TransitDate> listAllInvalidTapDates() {
    ArrayList<TransitDate> invalidTapDates = new ArrayList<>();
    for (Card card : cards) {
      invalidTapDates.addAll(card.getInvalidTapEventDates());
    }
    return invalidTapDates;
  }

  /**
   * Adds new card to the customers account array list.
   *
   * @param card - Card of which to add to customers accounts array list of cards.
   */
  public void addCard(Card card) {
    this.cards.add(card);
  }

  /**
   * Removes new card to the customers account array list.
   *
   * @param card - Card of which to remove to customers accounts array list of cards.
   */
  public void removeCard(Card card) {
    this.cards.remove(card);
  }

  /**
   * Returns an array list of the last 3 trips taken by customer account.
   *
   * @return - array list of the last 3 trips taken by customer account.
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
   * Returns the average monthly cost of customer.
   *
   * @return - average monthly cost of customer.
   */
  public double calculateAverageMonthlyCost() {
    // array lists needed to track customers data.
    ArrayList<TransitDate> months = new ArrayList<>();
    ArrayList<Double> costs = new ArrayList<>();
    ArrayList<Trip> trips = listAllTrips();
    ArrayList<TransitDate> invalidTapDates = listAllInvalidTapDates();

    if ((trips.size() == 0) && invalidTapDates.size() == 0) {
      return 0;
    }

    // account for trip costs
    for (Trip trip : trips) {
      boolean tripCostAdded = false;
      for (int j = 0; j < months.size(); j++) {
        if (trip.getStartDate().inSameMonth(months.get(j))) {
          costs.set(j, costs.get(j) + trip.getCost());
          tripCostAdded = true;
          break;
        }
      }
      if (!tripCostAdded) {
        months.add(trip.getStartDate());
        costs.add(trip.getCost());
      }
    }
    // account for pathological tap costs
    for (TransitDate date : invalidTapDates) {
      boolean tapCostAdded = false;
      for (int j = 0; j < months.size(); j++) {
        if (date.inSameMonth(months.get(j))) {
          costs.set(j, costs.get(j) + 6);
          tapCostAdded = true;
          break;
        }
      }
      if (!tapCostAdded) {
        months.add(date);
        costs.add(6.0);
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
   * Returns string representation of customerAccountClass.
   *
   * @return - string representation of customerAccountClass.
   */
  @Override
  public String toString() {

    return "Name: "
        + this.name
        + " | Email: "
        + this.email
        + " | ID: "
        + Integer.toString(this.id)
        + " | Cards: "
        + ConfigFileParser.getStringFromList(cards, "Cards");
  }
}
