/* Danya */

import java.util.ArrayList;

/**
 * Class for parsing this transit system's events.txt configuration.
 */
public class EventConfigFileParser extends ConfigFileParser {

  /**
   * Current output message, generated from parsing a line of input from events.txt.
   */
  private String message;
  /**
   * TransitFareManager object to which the parsed configuration pertains.
   */
  private TransitFareManager transitSystem;

  public EventConfigFileParser(String filename, TransitFareManager transitSystem) {
    super(filename);
    this.transitSystem = transitSystem;
  }

  /**
   * Parses a line this object's configuration file.
   *
   * @param line line in configuration file to be parsed
   */
  protected void parseLine(String line) {

    // Ignore empty line
    if (line.isEmpty()) {
      return;
    }

    // Reset the message which will be printed after parsing this line
    message = "";

    // Print input message
    System.out.println("INPUT    :   " + line);

    // Parse according to command type
    String[] lineData = line.split(": ");
    switch (lineData[0]) {
      case "Admin":
        parseAdminCommand(lineData);
        break;
      case "Customer":
        parseCustomerCommand(lineData);
        break;
      case "Card":
        parseCardCommand(lineData);
        break;
      default:
        message = "That is not a valid command type.";
    }

    // Print output message
    System.out.println("OUTPUT   :   " + message + System.lineSeparator());
  }

  /**
   * Parses admin commands i.e. lines beginning with "Admin:" in the configuration file.
   *
   * @param data parameters of the admin command
   */
  private void parseAdminCommand(String[] data) {

    String[] parameters = data[1].split(", ");

    switch (parameters[0]) {
      case "Revenue":
        message = "$";
        switch (parameters[1]) {
          case "Total":
            message += StatisticsManager.calculateRevenue();
            break;
          default:
            TransitDate date = TransitDate.createFromDateString(parameters[1]);
            message += StatisticsManager.calculateRevenueOnDate(date);
        }
        break;
      case "Trips":
        message = "Trips: " + System.lineSeparator();
        switch (parameters[1]) {
          case "Total":
            message += indentString(
                getStringFromListMultiline(StatisticsManager.getTrips(), "Trips"));
            break;
          default:
            TransitDate date = TransitDate.createFromDateString(parameters[1]);
            ArrayList<Trip> trips = StatisticsManager.getTripsOnDate(date);
            message += indentString(getStringFromListMultiline(trips, "Trips"));
        }
        break;
      case "Stations":
        TransitDate date = TransitDate.createFromDateString(parameters[1]);
        ArrayList<Station> stations = StatisticsManager.getStationsReachedOnDate(date);
        message = "Stations: " + System.lineSeparator()
            + indentString(getStringFromListMultiline(stations, "Stations"));
        break;
      case "Routes":
        message = "Routes:" + System.lineSeparator()
            + indentString(
            getStringFromListMultiline(transitSystem.getMap().getRoutes(), "Routes"));
        break;
      case "Customers":
        switch (parameters[1]) {
          case "Total":
            message = "Customers: " + System.lineSeparator()
                + indentString(
                getStringFromListMultiline(transitSystem.getCustomers(), "Customers"));
            break;
          case "Create":
            CustomerAccount customer = transitSystem
                .createCustomerAccount(parameters[2], parameters[3]);
            message = "Successfully created customer account: "
                + System.lineSeparator() + indentString(customer.toString());
            break;
          default:
            message = "That is not a valid admin customer command.";
        }
        break;
      case "Cards":
        message = "Cards: " + System.lineSeparator()
            + indentString(getStringFromListMultiline(transitSystem.getCards(), "Cards"));
        break;
      default:
        message = "That is not a valid admin command.";
    }
  }

  /**
   * Parses customer commands i.e. lines beginning with "Customer:" in the configuration file.
   *
   * @param data parameters of the customer command
   */
  private void parseCustomerCommand(String[] data) {

    CustomerAccount customer = transitSystem.getCustomerById(Integer.parseInt(data[1]));
    if (customer == null) {
      message = "That customer does not exist.";
      return;
    }

    String[] parameters = data[2].split(", ");

    switch (parameters[0]) {
      case "Details":
        message = customer.toString();
        break;
      case "Update Name":
        customer.setName(parameters[1]);
        message = "Successfully updated customer's name to " + parameters[1] + ".";
        break;
      case "Cards":
        switch (parameters[1]) {
          case "New":
            Card card = transitSystem.issueCard(customer);
            message = "Successfully added a card to this customer: " + card.toString();
            break;
          case "View":
            message = "Cards: " + System.lineSeparator()
                + indentString(getStringFromList(customer.getCards(), "Cards"));
            break;
          default:
            message = "That is not a valid customer card command.";
        }
        break;
      case "Average Cost":
        message = "$" + customer.calculateAverageMonthlyCost();
        break;
      case "Recent Trips":
        message = "Recent Trips:" + System.lineSeparator()
            + indentString(getStringFromListMultiline(customer.calculateRecentTrips(), "Trips"));
        break;
      default:
        message = "That is not a valid customer command.";
    }
  }

  /**
   * Parses card commands i.e. lines beginning with "Card:" in the configuration file.
   *
   * @param data parameters of the card command
   */
  private void parseCardCommand(String[] data) {

    Card card = transitSystem.getCardById(Integer.parseInt(data[1]));
    if (card == null) {
      message = "That card does not exist.";
      return;
    }

    String[] parameters = data[2].split(", ");

    switch (parameters[0]) {
      case "Details":
        message = card.toString();
        break;
      case "Recent Trips":
        message = "Recent Trips: " + System.lineSeparator()
            + indentString(getStringFromListMultiline(card.calculateRecentTrips(), "Trips"));
        break;
      case "Add Funds":
        switch (parameters[1]) {
          case "10":
            card.addTenDollars();
            message = "Successfully added $10 to card.";
            break;
          case "20":
            card.addTwentyDollars();
            message = "Successfully added $20 to card.";
            break;
          case "50":
            card.addFiftyDollars();
            message = "Successfully added $50 to card.";
            break;
          default:
            message = "That is not a valid amount.";
        }
        break;
      case "Balance":
        message = "$" + String.valueOf(card.getBalance());
        break;
      case "Deactivate":
        card.deactivate();
        message = "Successfully deactivated card.";
        break;
      case "Tap In":
        parseCardTapIn(card, parameters);
        break;
      case "Tap Out":
        parseCardTapOut(card, parameters);
        break;
      default:
        message = "That is not a valid card command.";
    }
  }

  /**
   * Parses card tap in commands, i.e. commands starting with "Card: `cardId`: Tap In,"
   * int the configuration file.
   *
   * @param card card that is to be tapped
   * @param parameters parameters of the command
   */
  private void parseCardTapIn(Card card, String[] parameters) {
    Station station = transitSystem.getMap()
        .getStationByNameAndRoute(parameters[3], parameters[2], parameters[1]);
    if (station == null) {
      message = "That is not a valid station.";
      return;
    }
    TransitDate date;
    try {
      date = TransitDate.createFromDatetimeString(parameters[4]);
    } catch (ArrayIndexOutOfBoundsException e) {
      message = "Error: That datetime is incorrectly formatted.";
      return;
    }
    try {
      card.tapIn(station, date);
    } catch (TapDeactivatedCardsException e) {
      message = "Error: That card has been deactivated.";
      return;
    } catch (InsufficientFundsException e) {
      message = "Error: That card has insufficient funds.";
      return;
    } catch (IllegalTapLocationException e) {
      message = "Error: The location of this tap event is a bit odd. "
          + "You have been charged for a $6 trip.";
      return;
    }
    message = "You have successfully tapped in at "
        + station.toString() + " using " + card.toString() + ".";
  }

  /**
   * Parses card tap out commands, i.e. commands starting with "Card: `cardId`: Tap Out,"
   * int the configuration file.
   *
   * @param card card that is to be tapped
   * @param parameters parameters of the command
   */
  private void parseCardTapOut(Card card, String[] parameters) {
    Station station = transitSystem.getMap()
        .getStationByNameAndRoute(parameters[3], parameters[2], parameters[1]);
    if (station == null) {
      message = "That is not a valid station.";
      return;
    }
    TransitDate date;
    try {
      date = TransitDate.createFromDatetimeString(parameters[4]);
    } catch (ArrayIndexOutOfBoundsException e) {
      message = "Error: That datetime is incorrectly formatted.";
      return;
    }
    try {
      card.tapOut(station, date);
    } catch (TapDeactivatedCardsException e) {
      message = "Error: That card has been deactivated.";
      return;
    } catch (IllegalTapLocationException e) {
      message = "Error: The location of this tap event is a bit odd. "
          + "You have been charged for a $6 trip.";
      return;
    }
    message = "You have successfully tapped out at "
        + station.toString() + " using " + card.toString() + ".";
  }

}
