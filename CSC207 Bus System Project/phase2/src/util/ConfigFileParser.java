package util;/* Danya */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class for parsing a configuration file.
 */
public abstract class ConfigFileParser {

  /**
   * Name of the file containing the configuration to be parsed.
   */
  private String fileName;
  protected transient EasyLogger logger;

  public ConfigFileParser(String fileName, EasyLogger logger) {
    this.fileName = fileName;
    this.logger = logger;
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
  public String indentString(String str) {
    return "             " + str
        .replace(System.lineSeparator(), System.lineSeparator() + "             ");
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
      logger.log.severe("Cannot read data file: " + fileName);
    }
  }
}
