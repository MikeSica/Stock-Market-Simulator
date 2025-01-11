import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.io.IOException;

import model.IBetterUser;

import static org.junit.Assert.assertTrue;

/**
 * A Junit test class for the Mock Gui  View class.
 */
public class GuiMockViewTests {
  private GuiMockView mockView;

  @Before
  public void setUp() throws IOException, IOException {
    IBetterUser mockUser = new MockBetterUser();
    mockView = new GuiMockView(mockUser);
  }

  @Test
  public void testNewPortfolioAction() throws IOException {
    ActionEvent mockEvent = new ActionEvent(mockView, ActionEvent.ACTION_PERFORMED,
            "New Portfolio");
    mockView.actionPerformed(mockEvent);

    assertTrue(mockView.getLog().contains("actionPerformed called with action command:"
            + " New Portfolio"));
    assertTrue(mockView.getLog().contains("Simulating new portfolio creation"));
  }

  @Test
  public void testBuyAction() throws IOException {
    ActionEvent mockEvent = new ActionEvent(mockView, ActionEvent.ACTION_PERFORMED, "Buy");
    mockView.actionPerformed(mockEvent);

    assertTrue(mockView.getLog().contains("actionPerformed called with action command: Buy"));
    assertTrue(mockView.getLog().contains("Simulating buy action"));
  }

  @Test
  public void testSellAction() throws IOException {
    ActionEvent mockEvent = new ActionEvent(mockView, ActionEvent.ACTION_PERFORMED, "sell");
    mockView.actionPerformed(mockEvent);

    assertTrue(mockView.getLog().contains("actionPerformed called with action command: sell"));
    assertTrue(mockView.getLog().contains("Simulating sell action"));
  }

  @Test
  public void testGetCompAction() throws IOException {
    ActionEvent mockEvent = new ActionEvent(mockView, ActionEvent.ACTION_PERFORMED, "Comp");
    mockView.actionPerformed(mockEvent);

    assertTrue(mockView.getLog().contains("actionPerformed called with action command: Comp"));
    assertTrue(mockView.getLog().contains("Simulating portfolio composition retrieval"));
  }

  @Test
  public void testSaveAction() throws IOException {
    ActionEvent mockEvent = new ActionEvent(mockView, ActionEvent.ACTION_PERFORMED, "Save");
    mockView.actionPerformed(mockEvent);

    assertTrue(mockView.getLog().contains("actionPerformed called with action command: Save"));
    assertTrue(mockView.getLog().contains("Simulating save action"));
  }

  @Test
  public void testRetrieveAction() throws IOException {
    ActionEvent mockEvent = new ActionEvent(mockView, ActionEvent.ACTION_PERFORMED, "Retrieve");
    mockView.actionPerformed(mockEvent);

    assertTrue(mockView.getLog().contains("actionPerformed called with action command: Retrieve"));
    assertTrue(mockView.getLog().contains("Simulating retrieve action"));
  }
}
