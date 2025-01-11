package model;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;

import controller.BetterController;
import controller.IController;
import view.GuiView;

/**
 * Represents an example on how to initialize the Gui Stock program from the main.
 */
public class GuiStockProgram {

  /**
   * Main method that tests the program runs correctly.
   * @param args contains all package functions
   */
  public static void main(String[] args) throws IOException {

    IBetterUser model = new BetterUser();

    GuiView.setDefaultLookAndFeelDecorated(false);
    GuiView frame = new GuiView(model);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    Readable rd = new InputStreamReader(System.in);
    IController controller = new BetterController(model, rd, frame);
  }

}
