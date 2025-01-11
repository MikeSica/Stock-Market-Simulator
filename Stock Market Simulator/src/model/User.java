package model;

import java.io.IOException;
import java.util.ArrayList;

/**
 * f
 * This class represents a user. A user has an ArrayList of Portfolios and can efficiently
 * alter them.
 */
public class User implements IUser {

  ArrayList<IPortfolio> portfolios;

  /**
   * Constructs a new User with an arrayList of IPortfolio objects.
   */
  public User() {
    portfolios = new ArrayList<IPortfolio>();
  }

  @Override
  public float getValueOfPortfolio(String name, String date) throws IllegalArgumentException {
    IPortfolio currentPortfolio = this.getPortfolio(name);
    return currentPortfolio.valueOfPortfolio(date);
  }

  @Override
  public void updatePortfolio(String name, String tickerSymbol, int quantity) throws IOException {
    IPortfolio currentPortfolio = this.getPortfolio(name);
    BasicStock currStock = new BaseStock(tickerSymbol);
    currentPortfolio.addStock(currStock, quantity);
  }

  @Override
  public void addPortfolio(String name) {
    portfolios.add(new PersonalPortfolio(name));
  }

  @Override
  public ArrayList<String> getPortfolios() {
    ArrayList<String> portfolioNames = new ArrayList<>();
    for (int i = 0; i < portfolios.size(); i++) {
      portfolioNames.add(portfolios.get(i).getName());
    }
    return portfolioNames;
  }

  /**
   * Gets the portfolio with the given name from the portfolio list.
   *
   * @param name the name of the portfolio
   * @return the portfolio
   * @throws IllegalArgumentException if the portfolio doesn't exist
   */
  private IPortfolio getPortfolio(String name) throws IllegalArgumentException {
    IPortfolio result = null;
    for (int i = 0; i < portfolios.size(); i++) {
      if (name.equals(portfolios.get(i).getName())) {
        result = portfolios.get(i);
      }
    }
    if (result == null) {
      throw new IllegalArgumentException("There are no portfolios with this name");
    }
    return result;
  }
}
