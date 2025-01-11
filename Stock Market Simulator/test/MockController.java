import java.io.IOException;
import java.util.Scanner;

import controller.BasicController;

import view.IView;

/**
 * A MockController is an instance of a controller which is
 * used to see if code paths are correct.
 */
class MockController extends BasicController {
  private MockView view;
  private Readable readable;
  private MockUser user;
  private String log;

  /**
   * Initializes a MockController with a user, readable and a view.
   *
   * @param user     the user being altered
   * @param readable the input
   * @param view     the view where messages are displayed
   */
  public MockController(MockUser user, Readable readable, IView view) {
    super(user, readable, view);
    this.readable = readable;
    this.user = user;
    this.log = "";
  }

  /**
   * The method that starts the mock controller.
   *
   * @throws IOException if a stock is an invalid ticker
   */
  @Override
  public void goNow() throws IOException {
    Boolean quit = false;
    Scanner sc = new Scanner(readable);
    while (!quit) { //continue until the user quits
      String userInstruction = sc.next();

      switch (userInstruction) {
        case "change":
          this.log += "changeCalled-";
          MockStock stock = new MockStock("GOOG");
          stock.stockChange("", "");
          this.log += stock.getLog();
          break;
        case "average":
          this.log += "averageCalled-";
          MockStock stock1 = new MockStock("GOOG");
          stock1.movingAverage("", 1);
          this.log += stock1.getLog();
          break;
        case "crossover":
          this.log += "crossedCalled-";
          MockStock stock2 = new MockStock("GOOG");
          stock2.crossOverDays("", "", 1);
          this.log += stock2.getLog();
          break;
        case "portfolio-value":
          this.log += "portfolio-valueCalled-";
          user.getValueOfPortfolio("s", "2");
          this.log += user.getLog();
          break;
        case "menu":
          this.log += "menuCalled-";
          break;
        case "new-portfolio":
          this.log += "new-portfolioCalled-";
          user.addPortfolio("s");
          this.log += user.getLog();
          break;
        case "add-portfolio":
          this.log += "add-portfolioCalled-";
          user.updatePortfolio("", "", 2);
          this.log += user.getLog();
          break;
        case "q": //quit
        case "quit": //quit
          this.log += "quitChanged-";
          quit = true;
          break;
        default: //error due to unrecognized instruction
          this.log += "badInput-";
      }

    }
    this.log += "quit";
  }


  /**
   * Retrieves the log which gets update when a user inputs an instruction as a string.
   *
   * @return the log as a string
   */
  public String getLog() {
    return this.log;
  }

}
