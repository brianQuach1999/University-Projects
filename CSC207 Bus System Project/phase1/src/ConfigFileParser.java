/* Danya */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for parsing a configuration file.
 */
public abstract class ConfigFileParser {

  /**
   * Name of the file containing the configuration to be parsed.
   */
  private String fileName;

  public ConfigFileParser(String fileName) {
    this.fileName = fileName;
  }

  /**
   * Parses a line this object's configuration file.
   *
   * @param line line in configuration file to be parsed
   */
  protected abstract void parseLine(String line);

  /**
   * Indents the given string by 13 spaces.
   *
   * @param str string to indent
   * @return indented string
   */
  public static String indentString(String str) {
    return "             " + str
        .replace(System.lineSeparator(), System.lineSeparator() + "             ");
  }

  /**
   * Turns an ArrayList of objects into a comma-separated string by calling toString on each of
   * them. If there are no objects, returns the string "No `categoryName`." using parameter
   * categoryName.
   *
   * @param list ArrayList of objects
   * @param categoryName name of this category of objects
   * @return comma-separated list of object strings
   */
  public static String getStringFromList(ArrayList list, String categoryName) {
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
   *
   * @param list ArrayList of objects
   * @param categoryName name of this category of objects
   * @return newline-separated list of object strings
   */
  public static String getStringFromListMultiline(ArrayList list, String categoryName) {
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

  /**
   * Parses this object's configuration file by calling the abstract method parseLine on each line
   * of file.
   */
  public void parse() {
    try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
      String line = fileReader.readLine();
      while (line != null) {
        this.parseLine(line);
        line = fileReader.readLine();
      }
    } catch (IOException e) {
      System.out.println("Cannot read data file: " + fileName);
    }
  }
}
