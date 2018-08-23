package api;

import transit.Card;
import transit.Station;
import transit.StatisticsManager;
import transit.TransitFareManager;
import transit.UserAccount;
import util.EasyLogger;
import util.FormattedDate;

/**
 * Class to handle card-related API queries.
 */
public class CardApi extends ChildApi {

  public CardApi(TransitFareManager transitFareManager, StatisticsManager statisticsManager,
      EasyLogger logger) {
    super(transitFareManager, statisticsManager, logger);
  }

  /**
   * Issue card to user. Give nickname to card.
   *
   * @param user user to which card is issued
   * @param cardNickname nickname of card
   * @return instance of card
   */
  public Card create(UserAccount user, String cardNickname) {
    Card card = transitFareManager.issueCard(user, cardNickname);
    logger.log.fine("Issuing card " + card + " to user " + user);
    return card;
  }

  /**
   * Load money onto card.
   *
   * @param card card on which to load money
   * @param amount money to add to card
   */
  public void load(Card card, double amount) {
    logger.log.fine("Loading funds onto card " + card + " - adding " + amount);
    card.addAmount(amount);
  }

  /**
   * Get balance of card.
   *
   * @param card card whose balance we wish to get
   * @return string representation of card's balance
   */
  public String getBalanceString(Card card) {
    logger.log.fine("Getting balance for card " + card);
    return card.getBalanceString();
  }

  /**
   * Update nickname and active state of card.
   *
   * @param card card to update
   * @param name new name of card
   * @param isActive whether card is active or not
   */
  public void update(Card card, String name, boolean isActive) {
    card.setNickname(name);
    card.setActive(isActive);
    logger.log.fine("Updated card " + card.getNickname());
  }

  /**
   * Request a tap in event with card at station, at date dateString and time timeString.
   *
   * @param station station where tap is located
   * @param card card to tap
   * @param dateString string representation of date (DD/MM/YYYY)
   * @param timeString string representation of time (HH:MM)
   */
  public void tapIn(Station station, Card card, String dateString, String timeString)
      throws TapFailedException {
    FormattedDate date = new FormattedDate(dateString, timeString);
    logger.log.fine("Tapping into " + station + " on " + dateString + " with card " + card);
    try {
      card.tapIn(station, date);
    } catch (Exception e) {
      logger.log.warning("Tap in failed.");
      throw new TapFailedException();
    }
  }

  /**
   * Request a tap in event with card at station, at date dateString and time timeString.
   *
   * @param station station where tap is located
   * @param card card to tap
   * @param dateString string representation of date (DD/MM/YYYY)
   * @param timeString string representation of time (HH:MM)
   */
  public void tapOut(Station station, Card card, String dateString, String timeString)
      throws TapFailedException {
    FormattedDate date = new FormattedDate(dateString, timeString);
    logger.log.fine("Tapping out of " + station + " on " + dateString + " with card " + card);
    try {
      card.tapOut(station, date);
    } catch (Exception e) {
      logger.log.warning("Tap out failed.");
      throw new TapFailedException();
    }
  }

}
