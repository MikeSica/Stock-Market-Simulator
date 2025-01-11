import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.BaseStock;
import model.BasicStock;
import model.PersonalPortfolio;

import static junit.framework.TestCase.assertEquals;

/**
 * A Junit test class for the PersonalPortfolio class.
 */
public class PersonalPortfolioTest extends AbstractPortfolioTest {

  @Before
  public void setUp() {
    normPortfolioTester = new PersonalPortfolio("norm");
  }

  @Test
  public void testAddStockValue() throws IOException {
    BasicStock google = new BaseStock("GOOG");
    normPortfolioTester.addStock(google, 239);
    float testValue = normPortfolioTester.valueOfPortfolio("2023-03-02");
    assertEquals(22062.08, testValue, 0.02);
  }

  @Test
  public void testAddMultipleStocks() throws IOException {
    BasicStock google = new BaseStock("GOOG");
    BasicStock amazon = new BaseStock("AMZN");
    normPortfolioTester.addStock(google, 239);
    normPortfolioTester.addStock(amazon, 200);
    float testValue = normPortfolioTester.valueOfPortfolio("2023-03-02");
    assertEquals(40488.08, testValue, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIncorrectDate() throws IOException {
    BasicStock google = new BaseStock("GOOG");
    BasicStock amazon = new BaseStock("AMZN");
    normPortfolioTester.addStock(google, 239);
    normPortfolioTester.addStock(amazon, 200);
    float testValue = normPortfolioTester.valueOfPortfolio("2025-03-02");
    assertEquals(0.0, testValue, 0.02);
  }
}