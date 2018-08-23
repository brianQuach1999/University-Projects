package util;

import java.util.ArrayList;

/**
 * Class for list which has a nice-looking string representation.
 *
 * @param <T> the type of the contents of the list
 */
public class PrettyList<T> {

  private ArrayList<T> list;
  private String categoryName;

  public PrettyList(ArrayList<T> list, String name) {
    this.list = list;
    this.categoryName = name;
  }

  /**
   * Turns an ArrayList of objects into a comma-separated string by calling toString on each of
   * them. If there are no objects, returns the string "No `categoryName`." using parameter
   * categoryName.
   */
  @Override
  public String toString() {
    if (list.size() == 0) {
      return "No " + categoryName + ".";
    }
    StringBuilder ret = new StringBuilder();
    for (Object item : list) {
      ret.append(item.toString());
      ret.append(", ");
    }
    return ret.substring(0, ret.length() - 2);
  }


  /**
   * Turns an ArrayList of objects into a newline-separated string by calling toString on each of
   * them. If there are no objects, returns the string "No `categoryName`." using parameter
   * categoryName.
   */
  public String toStringMultiline() {
    if (list.size() == 0) {
      return "No " + categoryName + ".";
    }
    StringBuilder ret = new StringBuilder();
    for (Object item : list) {
      ret.append(item.toString());
      ret.append(System.lineSeparator());
    }
    return ret.toString().trim();
  }

}
