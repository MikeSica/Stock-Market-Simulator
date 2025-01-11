import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import controller.IController;
import view.IView;

/**
 * A MockBetterController is an instance of a IController which is
 * used to see if code paths are correct.
 */
class MockBetterController extends MockController implements IController {
  private Readable readable;
  private MockBetterUser user;
  private MockBetterStock stock;
  private String log;

  /**
   * Initializes a MockController with a user, readable and view.
   *
   * @param user     the user being altered
   * @param readable the input
   * @param view     the view where messages are displayed
   */
  public MockBetterController(MockUser user, Readable readable, IView view) throws IOException {
    super(user, readable, view);
    this.readable = readable;
    this.user = new MockBetterUser();
    this.stock = new MockBetterStock("GOOG");
    this.log = "";
  }

  @Override
  public void goNow() throws IOException {
    Scanner sc = new Scanner(readable);
    boolean quit = false;

    while (!quit) {
      String userInstruction = sc.next();
      switch (userInstruction) {
        case "stock-chart":
          this.log += "stockChartController-";
          stock.performanceOverTime(",", "");
          this.log += stock.getLog() + "-";
          break;
        case "portfolio-chart":
          this.log += "portfolioChartController-";
          user.getPortfolioPerformance("", "", "");
          this.log += user.getLog() + "-";
          break;
        case "rebalance":
          this.log += "rebalanceCalledController-";
          ArrayList<Float> parameters = new ArrayList<>();
          user.rebalance("", parameters, "");
          this.log += user.getLog() + "-";
          break;
        case "sell":
          this.log += "sellCalledController-";
          this.user.sell("portName", "stockName", 3, "date");
          this.log += user.getLog() + "-";
          break;
        case "buy":
          this.log += "buyCalledController-";
          this.user.buy("portName", "stockName", 3, "date");
          this.log += user.getLog() + "-";
          break;
        case "composition":
          this.log += "compositionCalledController-";
          user.getValueOfPortfolio("portName", "date");
          user.getCompAtDate("portName", "date");
          this.log += user.getLog() + "-";
          break;
        case "portfolio-value":
          this.log += "portfoliovalueCalled-";
          user.getValueOfPortfolio("portName", "date");
          this.log += user.getLog() + "-";
          break;
        case "save":
          this.log += "saveCalledController-";
          user.save("portName");
          this.log += user.getLog() + "-";
          break;
        case "retrieve":
          this.log += "retrieveCalledController-";
          user.load("portName");
          this.log += user.getLog() + "-";
          break;
        case "menu":
          this.log += "menuCalled-";
          break;
        case "distribution":
          this.log += "distributionCalledController-";
          user.getDistribution("", "");
          this.log += user.getLog() + "-";
          break;
        case "q":
        case "quit":
          this.log += "quitChanged";
          quit = true;
          break;
        default:
          this.log += "badInput-";
      }
    }
  }

  /**
   * Retrieves the log which gets updated when a user inputs an instruction as a string.
   *
   * @return the log as a string
   */
  public String getLog() {
    return this.log;
  }
}
