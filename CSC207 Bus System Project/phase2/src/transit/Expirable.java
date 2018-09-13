package transit;

import util.FormattedDate;

/**
 * An interface representing the ability of an object to be valid or invalid, based on when it is used.
 */
public interface Expirable {
  /**
   * Returns whether an object is valid at the given time of use.
   * @param dateUsed the time the object is used/evaluated
   * @return true if the object is usable/valid; false otherwise
   */
  boolean isValid(FormattedDate dateUsed);
}
