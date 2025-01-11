package model;

import java.io.IOException;
import java.io.InputStreamReader;

import controller.BasicController;
import view.BasicView;
import view.IView;

/**
 * Represents an example on how to initialize the Stock program from the main.
 */
public class StockProgram {

  /**
   * Main method that tests the program runs correctly.
   * @param args contains all package functions
   */
  public static void main(String[] args) throws IOException {
    User model = new User();
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    IView view = new BasicView(ap);
    BasicController controller = new BasicController(model, rd, view);
    controller.goNow();
  }
}
