package transit;/* Danya */

import java.util.ArrayList;
import util.ConfigFileParser;
import util.EasyLogger;
import util.FormattedDate;
import util.PrettyList;

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
  private StatisticsManager stats;


  public EventConfigFileParser(String filename,
      TransitFareManager transitSystem,
      StatisticsManager stats,
      EasyLogger logger) {

    super(filename, logger);
    this.transitSystem = transitSystem;
    this.stats = stats;
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
    logger.log.fine(System.lineSeparator() + "INPUT    :   " + line + System.lineSeparator());

    // Parse according to command type
    String[] lineData = line.split(": ");
    switch (lineData[0]) {
      case "Admin":
        parseAdminCommand(lineData);
        break;
      case "User":
        parseUserCommand(lineData);
        break;
      case "Card":
        parseCardCommand(lineData);
        break;
      default:
        message = "That is not a valid command type.";
    }

    // Print output message
    logger.log.fine(System.lineSeparator() + "OUTPUT   :   " + message + System.lineSeparator());

  }

  /**
   * Parses admin commands i.e. lines beginning with "Admin:" in the configuration file.
   *
   * @param data parameters of the admin command
   */
  private void parseAdminCommand(String[] data) {

    String[] parameters = data[1].split(", ");
    PrettyList<Trip> prettyTrips = new PrettyList<>(transitSystem.getTrips(), "Trips");

    switch (parameters[0]) {
      case "Revenue":
        message = "$";
        switch (parameters[1]) {
          case "Total":
            message += stats.calculateRevenue();
            break;
          default:
            FormattedDate date = new FormattedDate(parameters[1]);
            message += stats.calculateRevenueOnDate(date);
        }
        break;
      case "Trips":
        message = "Trips: " + System.lineSeparator();
        switch (parameters[1]) {
          case "Total":
            message += indentString(prettyTrips.toStringMultiline());
            break;
          default:
            FormattedDate date = new FormattedDate(parameters[1]);
            PrettyList<Trip> prettyTripsOnDate = new PrettyList<>(stats.getTripsOnDate(date),
                "Trips");
            message += indentString(prettyTripsOnDate.toStringMultiline());
        }
        break;
      case "Stations":
        FormattedDate date = new FormattedDate(parameters[1]);
        ArrayList<Station> stations = stats.getStationsReachedOnDate(date);
        PrettyList<Station> prettyStations = new PrettyList<>(stations, "Stations");

        message = "Stations: " + System.lineSeparator()
            + indentString(prettyStations.toStringMultiline());
        break;
      case "Routes":
        PrettyList<Route> prettyRoute = new PrettyList<>(transitSystem.getMap().getRoutes(),
            "Routes");
        message = "Routes:" + System.lineSeparator()
            + indentString(prettyRoute.toStringMultiline());
        break;
      case "Users":
        switch (parameters[1]) {
          case "Total":
            PrettyList<UserAccount> prettyUsers = new PrettyList<>(transitSystem.getUsers(),
                "Users");
            message = "Users: " + System.lineSeparator()
                + indentString(prettyUsers.toStringMultiline());
            break;
          case "Create":
            UserAccount user = transitSystem
                .createUserAccount(parameters[2], parameters[3], (parameters[4].equals("admin")));
            message = "Successfully created user account: "
                + System.lineSeparator() + indentString(user.toString());
            break;
          default:
            message = "That is not a valid admin user command.";
        }
        break;
      case "Cards":
        PrettyList<Card> prettyCards = new PrettyList<>(transitSystem.getCards(), "Cards");
        message = "Cards: " + System.lineSeparator()
            + indentString(prettyCards.toStringMultiline());
        break;
      default:
        message = "That is not a valid admin command.";
    }
  }

  /**
   * Parses user commands i.e. lines beginning with "User:" in the configuration file.
   *
   * @param data parameters of the user command
   */
  private void parseUserCommand(String[] data) {

    UserAccount user = transitSystem.getUserByEmail(data[1]);
    if (user == null) {
      message = "That user does not exist.";
      return;
    }

    String[] parameters = data[2].split(", ");
    PrettyList<Card> prettyCards = new PrettyList<>(user.getCards(), "Cards");

    switch (parameters[0]) {
      case "Details":
        message = user.toString();
        break;
      case "Update Name":
        user.setName(parameters[1]);
        message = "Successfully updated user's name to " + parameters[1] + ".";
        break;
      case "Cards":
        switch (parameters[1]) {
          case "New":
            Card card = transitSystem.issueCard(user);
            message = "Successfully added a card to this user: " + card.toString();
            break;
          case "View":
            message = "Cards: " + System.lineSeparator()
                + indentString(prettyCards.toString());
            break;
          default:
            message = "That is not a valid user card command.";
        }
        break;
      case "Average Cost":
        message = "$" + user.calculateAverageMonthlyCost();
        break;
      case "Recent Trips":
        PrettyList<Trip> prettyTrips = new PrettyList<>(user.calculateRecentTrips(), "Trips");
        message = "Recent Trips:" + System.lineSeparator()
            + indentString(prettyTrips.toStringMultiline());
        break;
      default:
        message = "That is not a valid user command.";
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
        PrettyList<Trip> prettyTrips = new PrettyList<>(card.calculateRecentTrips(), "Trips");
        message = "Recent Trips: " + System.lineSeparator()
            + indentString(prettyTrips.toStringMultiline());
        break;
      case "Add Funds":
        switch (parameters[1]) {
          case "10":
            card.addAmount(10);
            message = "Successfully added $10 to card.";
            break;
          case "20":
            card.addAmount(20);
            message = "Successfully added $20 to card.";
            break;
          case "50":
            card.addAmount(50);
            message = "Successfully added $50 to card.";
            break;
          default:
            message = "That is not a valid amount.";
        }
        break;
      case "Balance":
        message = "$" + String.valueOf(card.getBalanceString());
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
   * Parses card tap in commands, i.e. commands starting with "Card: `cardId`: Tap In," int the
   * configuration file.
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
    FormattedDate date;
    try {
      String[] datetimeParts = parameters[4].split(" ");
      date = new FormattedDate(datetimeParts[0], datetimeParts[1]);
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
   * Parses card tap out commands, i.e. commands starting with "Card: `cardId`: Tap Out," int the
   * configuration file.
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
    FormattedDate date;
    try {
      String[] datetimeParts = parameters[4].split(" ");
      date = new FormattedDate(datetimeParts[0], datetimeParts[1]);
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
