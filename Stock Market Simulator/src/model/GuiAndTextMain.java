package model;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;

import controller.BetterController;
import controller.IController;
import view.BetterView;
import view.GuiView;
import view.IView;

/**
 * Represents an example on how to initialize the Gui stock program from the main and the
 * text stock program from the main.
 */
public class GuiAndTextMain {

  /**
   * Main method that tests the program runs correctly.
   * @param args contains all package functions
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      launchGUI();
    } else if (args.length == 1 && args[0].equals("-text")) {
      launchTextInterface();
    } else {
      System.out.println("Invalid arguments. Use -text for text-based interface.");
      System.exit(1);
    }
  }

  /**
   * Launches the GUI.
   */
  private static void launchGUI() {
    IBetterUser model = new BetterUser();
    GuiView.setDefaultLookAndFeelDecorated(false);
    GuiView frame = new GuiView(model);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    Readable rd = new InputStreamReader(System.in);
    IController controller = new BetterController(model, rd, frame);
  }

  /**
   * Launches the text interface.
   * @throws IOException if the controller malfunctions.
   */
  private static void launchTextInterface() throws IOException {
    IBetterUser model = new BetterUser();
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    IView view = new BetterView(ap);
    IController controller = new BetterController(model, rd, view);
    controller.goNow();
  }
}
