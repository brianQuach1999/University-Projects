package util;/* Danya */

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Class for date of transit system event. This class is basically a wrapper for java.util.Date,
 * with some added functionality.
 */
public class FormattedDate implements Comparable<FormattedDate>, Serializable {

  private Date date;

  public FormattedDate() {
    this.date = new Date();
  }

  public FormattedDate(Date date) {
    this.date = date;
  }

  public FormattedDate(String dateString) {
    this.date = createDateFromDateString(dateString);
  }

  public FormattedDate(String dateString, String timeString) {
    this.date = createDateFromDateTimeString(dateString, timeString);
  }

  public Date getDate() {
    return date;
  }

  /**
   * Creates and returns an instance of Date using the dateString formatted as DD/MM/YYYY.
   *
   * @param dateString string representing date as DD/MM/YYYY
   */
  private Date createDateFromDateString(String dateString) {
    String[] dateParts = dateString.split("/");
    int day = Integer.parseInt(dateParts[0]);
    int month = Integer.parseInt(dateParts[1]) - 1;
    int year = Integer.parseInt(dateParts[2]);
    Calendar calendar = new GregorianCalendar(year, month, day);
    return calendar.getTime();
  }

  /**
   * Creates and returns an instance of Date using the dateString formatted as DD/MM/YYYY and
   * timeString as HH:MM.
   *
   * @param dateString string representing date as DD/MM/YYYY
   * @param timeString string representing time as HH:MM
   */
  private Date createDateFromDateTimeString(String dateString, String timeString) {
    String[] dateParts = dateString.split("/");
    String[] timeParts = timeString.split(":");
    int day = Integer.parseInt(dateParts[0]);
    int month = Integer.parseInt(dateParts[1]) - 1;
    int year = Integer.parseInt(dateParts[2]);
    int hour = Integer.parseInt(timeParts[0]);
    int minute = Integer.parseInt(timeParts[1]);
    Calendar calendar = new GregorianCalendar(year, month, day, hour, minute, 0);
    return calendar.getTime();
  }

  /**
   * Returns whether or not the FormattedDate date2 is on the same day as this instance of
   * FormattedDate.
   */
  public boolean onSameDay(FormattedDate date2) {

    // Code adapted from https://stackoverflow.com/a/2517824/3200577
    // (
    // User: Michael Borgwardt)

    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(date);
    cal2.setTime(date2.getDate());
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
        && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
  }

  /**
   * Returns whether or not the FormattedDate date2 is in the same month as this instance of
   * FormattedDate.
   */
  public boolean inSameMonth(FormattedDate date2) {

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
   * Returns a string representation of this FormattedDate formatted as DD/MM/YYYY HH:MM.
   *
   * @return string formatted as DD/MM/YYYY HH:MM
   */
  public String toDateTimeString() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    return sdf.format(date);
  }

  public String toTimeString() {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    return sdf.format(date);
  }

  public String toDateString() {
    return toString();
  }

  public FormattedDate addTime(int time) {
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, time);
    date = calendar.getTime();
    return new FormattedDate(date);
  }

  /**
   * Returns a string representation of this FormattedDate formatted as DD/MM/YYYY.
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
   * @param td instance of FormattedDate
   */
  @Override
  public int compareTo(FormattedDate td) {
    if (getDate() == null || td.getDate() == null) {
      return 0;
    }
    return getDate().compareTo(td.getDate());
  }
}
