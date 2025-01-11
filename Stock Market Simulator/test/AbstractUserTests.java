import org.junit.Test;

import java.io.IOException;
import java.util.List;

import model.IUser;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * An Abstract JUnit test class for the IUser implementation
 * with tests that should pass on a User and Better User.
 */
public abstract class AbstractUserTests {
  protected IUser u1;

  @Test
  public void testAddPortfolio() {
    u1.addPortfolio("greg");
    String result = u1.getPortfolios().get(0);
    assertEquals("greg", result);
  }

  @Test
  public void testAddPortfolioToPortoflio() {
    u1.addPortfolio("greg");
    u1.addPortfolio("geg");
    String result = u1.getPortfolios().get(0);
    assertEquals("greg", result);
    result = u1.getPortfolios().get(1);
    assertEquals("geg", result);
  }

  @Test
  public void testPortfolioStartsEmpty() {
    List<String> result = u1.getPortfolios();
    assertTrue(result.isEmpty());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIncorrectProfileName() throws IllegalArgumentException, IOException {
    u1.addPortfolio("greg");
    u1.updatePortfolio("greg", "GOOG", 34);
    float result = u1.getValueOfPortfolio("grfg", "2021-05-26");
    assertEquals(532.43, result, 0.2);
  }

  @Test
  public void testValueAtStart() {
    u1.addPortfolio("greg");
    float result = u1.getValueOfPortfolio("greg", "2023-05-26");
    assertEquals(0, result, 0.02);
  }

  @Test
  public void testGetPortfolioAtStart() {

    assertTrue(u1.getPortfolios().isEmpty());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIncorrectDateForValue() throws IllegalArgumentException, IOException {
    u1.addPortfolio("greg");
    u1.updatePortfolio("greg", "GOOG", 34);
    float result = u1.getValueOfPortfolio("grfg", "2221-05-26");
    assertEquals(532.43, result, 0.2);
  }


}
