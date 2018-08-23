/* Danya */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Class for date of transit system event. This class is basically a wrapper for java.util.Date,
 * with some added functionality.
 */
public class TransitDate implements Comparable<TransitDate> {

  private Date date;

  public TransitDate(Date date) {
    this.date = date;
  }

  public Date getDate() {
    return date;
  }

  /**
   * Creates and returns an instance of TransitDate using the dateString formatted as DD/MM/YYYY.
   *
   * @param dateString string representing date as DD/MM/YYYY
   */
  public static TransitDate createFromDateString(String dateString) {
    String[] dateParts = dateString.split("/");
    int day = Integer.parseInt(dateParts[0]);
    int month = Integer.parseInt(dateParts[1]) - 1;
    int year = Integer.parseInt(dateParts[2]);
    Calendar calendar = new GregorianCalendar(year, month, day);
    return new TransitDate(calendar.getTime());
  }

  /**
   * Creates and returns an instance of TransitDate using the datetimeString formatted as DD/MM/YYYY
   * HH:MM.
   *
   * @param datetimeString string representing datetime as DD/MM/YYYY HH:MM
   */
  public static TransitDate createFromDatetimeString(String datetimeString) {
    String[] datetimeParts = datetimeString.split(" ");
    String[] dateParts = datetimeParts[0].split("/");
    String[] timeParts = datetimeParts[1].split(":");
    int day = Integer.parseInt(dateParts[0]);
    int month = Integer.parseInt(dateParts[1]) - 1;
    int year = Integer.parseInt(dateParts[2]);
    int hour = Integer.parseInt(timeParts[0]);
    int minute = Integer.parseInt(timeParts[1]);
    Calendar calendar = new GregorianCalendar(year, month, day, hour, minute, 0);
    return new TransitDate(calendar.getTime());
  }

  /**
   * Returns whether or not the TransitDate date2 is on the same day as this instance of
   * TransitDate.
   */
  public boolean onSameDay(TransitDate date2) {

    // Code adapted from https://stackoverflow.com/a/2517824/3200577
    // (User: Michael Borgwardt)

    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(date);
    cal2.setTime(date2.getDate());
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
        && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
  }

  /**
   * Returns whether or not the TransitDate date2 is in the same month as this instance of
   * TransitDate.
   */
  public boolean inSameMonth(TransitDate date2) {

    // Code adapted from https://stackoverflow.com/a/2517824/3200577
    // (User: Michael Borgwardt)

    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(date);
    cal2.setTime(date2.getDate());
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
        && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
  }

  /**
   * Returns a string representation of this TransitDate formatted as DD/MM/YYYY HH:MM.
   *
   * @return string formatted as DD/MM/YYYY HH:MM
   */
  public String toDateTimeString() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    return sdf.format(date);
  }

  /**
   * Returns a string representation of this TransitDate formatted as DD/MM/YYYY.
   *
   * @return string formatted as DD/MM/YYYY
   */
  @Override
  public String toString() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    return sdf.format(date);
  }

  /**
   * Calls the compareTo method of this instance's java.util.Date object on td's java.util.Date
   * object.
   *
   * @param td instance of TransitDate
   */
  @Override
  public int compareTo(TransitDate td) {
    if (getDate() == null || td.getDate() == null) {
      return 0;
    }
    return getDate().compareTo(td.getDate());
  }
}
