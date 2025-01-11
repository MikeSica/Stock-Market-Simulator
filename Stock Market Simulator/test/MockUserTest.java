import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

/**
 * A Junit test class for the MockUser class.
 */
public class MockUserTest {

  @Test
  public void addPortfolioTest() throws IOException {
    MockUser user = new MockUser();
    user.addPortfolio("e");
    assertEquals("addPortfolio",
            user.getLog().toString());
  }

  @Test
  public void updatePortfolioTest() throws IOException {
    MockUser user = new MockUser();
    user.updatePortfolio("e", "s", 2);
    assertEquals("updatePortfolio",
            user.getLog().toString());
  }

  @Test
  public void getPortfoliosTest() throws IOException {
    MockUser user = new MockUser();
    user.getPortfolios();
    assertEquals("getPortfolios",
            user.getLog().toString());
  }

  @Test
  public void getValueOfPortfolioTest() throws IOException {
    MockUser user = new MockUser();
    user.getValueOfPortfolio("pol", "s");
    assertEquals("getValueOfPortfoliovalueOfPortfoliogetStockPricegetPriceAtClosing",
            user.getLog().toString());
  }


}
