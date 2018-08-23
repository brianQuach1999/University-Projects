package transit;/* Brian */

import java.io.Serializable;
import java.util.ArrayList;
import util.FormattedDate;

/**
 * A Tap at a <code>BusStation</code> or <code>SubwayStation</code>.
 */
public abstract class TapEvent implements Serializable {

  private FormattedDate date;
  private Station station;
  private Card card;
  private boolean flagged = false; // weather the card has triggered the system

  protected ArrayList<TapEvent> unnaturalTapEvents = new ArrayList<>();
  /** A constructor for the TapEvent class */
  public TapEvent(Station station, FormattedDate date, Card card) {
    this.station = station;
    this.date = date;
    this.card = card;
  }

  /**
   * Returns the <code>Date</code> this <code>TapEvent</code> was made.
   *
   * @return the <code>Date</code> this <code>TapEvent</code> was made.
   */
  public FormattedDate getTransitDate() {
    return date;
  }
  /**
   * A method that returns card of tap event
   *
   * @return card of tap event
   */
  public Card getCard() {
    return this.card;
  }

  /**
   * Returns the <code>Station</code> at which this <code>TapEvent</code> was made.
   *
   * @return the <code>Station</code> at which this <code>TapEvent</code> was made.
   */
  public Station getStation() {
    return station;
  }
  /**
   * A method that flags taps as unnatural tap events
   **/
  public void flagAsUnnatural() {
    this.flagged = true;
    unnaturalTapEvents.add(this);
  }
  /**
   * A method that returns if tap was flagged
   *
   * @return if card tap was flagged
   */
  public boolean isFlagged() {
    return this.flagged;
  }
  /**
   * A method that returns if tap event has card identified
   *
   * @return tap event has card identified
   */
  public boolean hasCard(Card card) {
    return this.card.equals(card);
  }
}
