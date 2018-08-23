/* Danya */

import java.util.ArrayList;

/**
 * Main class of the transit system.
 */
public class TransitFareManager {

  /**
   * List of customers that have accounts with this transit system.
   */
  private ArrayList<CustomerAccount> customers = new ArrayList<>();
  /**
   * List of cards that have been issued.
   */
  private ArrayList<Card> cards = new ArrayList<>();
  /**
   * Map of this transit system.
   */
  private Map map;

  public TransitFareManager(Map map) {
    this.map = map;
  }

  /**
   * Main method of the app. Creates a map using the map configuration at map.txt, creates a transit
   * fare manager with that map, and then applying the events in events.txt to the new transit fare
   * manager.
   */
  public static void main(String[] args) {
    // Create map
    Map thisMap = new Map();
    MapConfigFileParser mapData = new MapConfigFileParser("map.txt", thisMap);
    mapData.parse();

    // Create transit fare manager
    TransitFareManager ttc = new TransitFareManager(thisMap);

    // Process events from events.txt
    EventConfigFileParser eventData = new EventConfigFileParser("events.txt", ttc);
    eventData.parse();
  }


  public Map getMap() {
    return this.map;
  }

  public ArrayList<CustomerAccount> getCustomers() {
    return customers;
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  /**
   * Creates and returns a new customer account. Generates ID for new customer based on number of
   * existing customers.
   *
   * @param name customer name
   * @param email customer email
   * @return new customer account object
   */
  public CustomerAccount createCustomerAccount(String name, String email) {
    // Increment customer id by one for every new customer
    CustomerAccount customer = new CustomerAccount(name, email, this.customers.size());
    this.customers.add(customer);
    return customer;
  }

  /**
   * Creates and returns a new card object. Generates ID for new card based in the number of
   * existing cards. Adds this new card to customer.
   *
   * @param customer customer to which the card is issued
   * @return new card object
   */
  public Card issueCard(CustomerAccount customer) {
    // Increment card id by one for every new card
    Card card = new Card(this.cards.size());
    this.cards.add(card);
    customer.addCard(card);
    return card;
  }

  /**
   * Returns a customer based on the customer's ID. Because the IDs were generated incrementally, a
   * customer's ID is also the customer object's index in the list this.customers.
   *
   * @param id id of customer
   */
  public CustomerAccount getCustomerById(int id) {
    return this.customers.get(id);
  }

  /**
   * Returns a card based on the card's ID. Because the IDs were generated incrementally, a card's
   * ID is also the card object's index in the list this.cards.
   *
   * @param id id of card
   */
  public Card getCardById(int id) {
    return this.cards.get(id);
  }

}
