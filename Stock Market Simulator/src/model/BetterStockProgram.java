package model;

import java.io.IOException;
import java.io.InputStreamReader;

import controller.BetterController;
import controller.IController;
import view.BetterView;
import view.IView;

/**
 * Represents an example on how to initialize the Better Stock program from the main.
 */
public class BetterStockProgram {

  /**
   * Main method that tests the program runs correctly.
   * @param args contains all package functions
   */
  public static void main(String[] args) throws IOException {
    IBetterUser model = new BetterUser();
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    IView view = new BetterView(ap);
    IController controller = new BetterController(model, rd, view);
    controller.goNow();
  }
}
