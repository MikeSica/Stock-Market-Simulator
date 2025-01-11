import org.junit.Test;

import model.IPortfolio;

import static junit.framework.TestCase.assertEquals;

/**
 * An Abstract JUnit test class for the IPortfolio implementation
 * with tests that should pass on a Personal and Better portfolio.
 */
public abstract class AbstractPortfolioTest {
  protected IPortfolio normPortfolioTester;

  @Test
  public void testGetName() {
    String testName = normPortfolioTester.getName();
    assertEquals("norm", testName);
  }

  @Test
  public void testNameCantMutateOutsideOfTest() {
    String testName = normPortfolioTester.getName();
    testName = "nah";
    assertEquals("norm", normPortfolioTester.getName());
  }

  @Test
  public void testValueOfEmpty() {
    float testvalue = normPortfolioTester.valueOfPortfolio("2023-03-06");
    assertEquals(0.0, testvalue, 0.02);
  }

}
