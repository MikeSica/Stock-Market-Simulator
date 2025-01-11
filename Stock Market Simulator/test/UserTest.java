import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.User;

import static junit.framework.TestCase.assertEquals;

/**
 * A Junit test class for the User class.
 */
public class UserTest extends AbstractUserTests {

  @Before
  public void setup() {
    u1 = new User();
  }

  @Test
  public void testUpdatePortfolio() throws IOException {
    u1.addPortfolio("greg");
    u1.updatePortfolio("greg", "GOOG", 34);
    float result = u1.getValueOfPortfolio("greg", "2021-05-26");
    assertEquals(82740.02, result, 0.2);
  }
}
