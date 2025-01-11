import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.IBetterUser;
import view.GuiView;
import view.IView;

/**
 * A MockGuiView is an instance of a GuiView which is
 * used to see if code paths are correct.
 */
class GuiMockView extends GuiView implements IView, ActionListener, ItemListener,
        ListSelectionListener {
  private Appendable ap;

  /**
   * Constructor that creates a GuiView object and initiates the MockBetterUser and sets the
   * appendable to a new StringBuilder in order to use it as a log.
   *
   * @param newUser the mock user being used
   * @throws IOException when the Mock User is composed of invalid stocks in mock portfolios.
   */
  public GuiMockView(IBetterUser newUser) throws IOException {
    super(newUser);
    MockBetterUser user = new MockBetterUser();
    ap = new StringBuilder();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      ap.append("actionPerformed called with action command: ")
              .append(e.getActionCommand()).append("\n");
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    switch (e.getActionCommand()) {
      case "New Portfolio":
        try {
          ap.append("Simulating new portfolio creation\n");
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Buy":
        try {
          ap.append("Simulating buy action\n");
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "sell":
        try {
          ap.append("Simulating sell action\n");
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Comp":
        try {
          ap.append("Simulating portfolio composition retrieval\n");
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Save":
        try {
          ap.append("Simulating save action\n");
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Retrieve":
        try {
          ap.append("Simulating retrieve action\n");
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      default:
        try {
          ap.append("Unknown action command\n");
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    try {
      ap.append("valueChanged called with source: ").append(e.getSource().toString()).append("\n");
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void welcome() throws IOException {
    ap.append("welcome method called\n");
  }

  @Override
  public void farewellMessage() throws IOException {
    ap.append("farewellMessage method called\n");
  }

  @Override
  public void promptUser() throws IOException {
    ap.append("promptUser method called\n");
  }

  @Override
  public void displayMessage(String message) throws IOException {
    ap.append("displayMessage method called with message: ").append(message).append("\n");
  }

  @Override
  public void askTicker() throws IOException {
    ap.append("askTicker method called\n");
  }

  @Override
  public void askStartDate() throws IOException {
    ap.append("askStartDate method called\n");
  }

  @Override
  public void askDate() throws IOException {
    ap.append("askDate method called\n");
  }

  @Override
  public void askEndDate() throws IOException {
    ap.append("askEndDate method called\n");
  }

  @Override
  public void movingAverage() throws IOException {
    ap.append("movingAverage method called\n");
  }

  @Override
  public void portfolioSelect() throws IOException {
    ap.append("portfolioSelect method called\n");
  }

  @Override
  public void displayPortfolio(String portfolio) throws IOException {
    ap.append("displayPortfolio method called with portfolio: ").append(portfolio).append("\n");
  }

  @Override
  public void noPortfolios() throws IOException {
    ap.append("noPortfolios method called\n");
  }

  @Override
  public void newPortfolioMessage() throws IOException {
    ap.append("newPortfolioMessage method called\n");
  }

  @Override
  public void addingMessage() throws IOException {
    ap.append("addingMessage method called\n");
  }

  @Override
  public void errorMessage(String e) throws IOException {
    ap.append("errorMessage method called with error: ").append(e).append("\n");
  }

  /**
   * Getter for the log to verify logs in the test.
   *
   * @return the log as a string
   */
  public String getLog() {
    return ap.toString();
  }
}

