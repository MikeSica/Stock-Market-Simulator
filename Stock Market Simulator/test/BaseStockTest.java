import org.junit.Before;

import java.io.IOException;

import model.BaseStock;



/**
 * A Junit test class for the BaseStock class.
 */
public class BaseStockTest extends AbstractStockTest {

  @Before
  public void setUp() throws IOException {
    stock = new BaseStock("GOOG");
    stock2 = new BaseStock("AMZN");
    stock3 = new BaseStock("AMD");
  }


}