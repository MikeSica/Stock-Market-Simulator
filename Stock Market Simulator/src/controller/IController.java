package controller;

import java.io.IOException;
import java.util.Scanner;

/**
 * The IController interface represents a controller and
 * provides a method to display various types of information to the user.
 */
public interface IController {

  /**
   * Gets information from the user, sends it to the model and then sends the models
   * output to the view to display a valid message.
   */
  void goNow() throws IOException;

  /**
   * Contains the switch case function for goNow.
   *
   * @param sc              the scanner
   * @param userInstruction the instruction of the user
   * @return the boolean of quit if the user wants to quit
   * @throws IOException if a stock doesn't exist after the user inputs one
   */
  boolean goNowHelp(String userInstruction, Scanner sc) throws IOException;
}
