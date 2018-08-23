package transit;/* Danya */

/**
 * Exception class to indicate when a card has attempted a tap in but the location of the tap is
 * pathological and thus the tap cannot be added to a trip.
 */
public class IllegalTapLocationException extends Exception {

}
