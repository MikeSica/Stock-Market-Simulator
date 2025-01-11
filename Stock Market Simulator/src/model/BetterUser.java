package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * A Better User class to represent the user of the program.
 * Implements functions for buying, selling, saving, and loading.
 * Rebalancing and performance of stock and portfolios.
 * As well as all containing all functionality in User.
 */
public class BetterUser extends User implements IBetterUser {
  private List<String> retrievablePortfolios;

  /**
   * Initializes the fields retrieveablePortfolios and portfolios.
   */
  public BetterUser() {
    this.portfolios = new ArrayList<IPortfolio>();
    this.getRetrievablePortfolios();
    this.loadRetrievablePortfolios();
  }

  /**
   * Loads the saved portfolios so the user has them.
   */
  private void loadRetrievablePortfolios() {
    try {
      for (int i = 0; i < this.retrievablePortfolios.size(); i++) {
        this.addPortfolio(this.retrievablePortfolios.get(i));
        IBetterPortfolio p = this.find(this.retrievablePortfolios.get(i));
        p.load();
      }
    } catch (FileNotFoundException e) {
      this.portfolios = new ArrayList<IPortfolio>();
    }
  }

  @Override
  public String buy(String portfolioName, String stockName, int quantity, String date)
          throws IllegalArgumentException {
    IBetterPortfolio p = this.find(portfolioName);
    try {
      p.buyStock(stockName, quantity, date);
      return "Bought " + Integer.toString(quantity) + "of " + stockName;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Could not buy that");
    }
  }

  @Override
  public String sell(String portfolioName, String stockName, float quantity, String date)
          throws IllegalArgumentException {
    IBetterPortfolio p = this.find(portfolioName);
    try {
      p.sellStock(stockName, quantity, date);
      return "Bought " + Float.toString(quantity) + "of " + stockName;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Could not sell stock");
    }
  }

  @Override
  public String rebalance(String portfolioName, ArrayList<Float> parameters, String date)
          throws IllegalArgumentException {
    IBetterPortfolio p = this.find(portfolioName);
    try {
      p.rebalance(parameters, date);
      List<List<String>> composition = p.getCompAtDate(date);
      return this.getDataFromComp(composition);
    } catch (IOException e) {
      throw new IllegalArgumentException("Rebalance Failed");
    }
  }

  /**
   * From the lists in composition, changes that into a readable string.
   * Each line the string has the stock and the quantity.
   *
   * @param composition the composition of the stock
   * @return a string of the data
   */
  private String getDataFromComp(List<List<String>> composition) {
    String result = "";
    for (int i = 0; i < composition.size(); i++) {
      String ticker = composition.get(i).get(0);
      String quantity = composition.get(i).get(1);
      result = result + ticker + " " + quantity + "\n";
    }
    return result;
  }

  @Override
  public String getCompAtDate(String portfolioName, String date) throws IllegalArgumentException {
    IBetterPortfolio p = this.find(portfolioName);
    return this.getDataFromComp(p.getCompAtDate(date));
  }

  @Override
  public String save(String portfolioName)
          throws IllegalArgumentException {
    IBetterPortfolio p = this.find(portfolioName);
    try {
      p.save();
      String adder = p.getName();
      if (!this.retrievablePortfolios.contains(adder)) {
        this.retrievablePortfolios.add(adder);
      }
      return portfolioName + " saved as " + p.getName();
    } catch (IOException e) {
      throw new IllegalArgumentException("Save failed");
    }
  }

  @Override
  public String load(String portfolioName)
          throws IllegalArgumentException {
    IBetterPortfolio p = this.find(portfolioName);
    try {
      p.load();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Could not load file");
    }
    return "Loaded portfolio  " + p.getName();
  }

  @Override
  public String getSavedPortfolios() {
    String result = "";
    for (int i = 0; i < this.retrievablePortfolios.size(); i++) {
      result += this.retrievablePortfolios.get(i) + "\n";
    }
    return result;
  }

  @Override
  public String getPortfolioPerformance(String portfolioName, String startDate, String endDate)
          throws IllegalArgumentException {
    IBetterPortfolio p = this.find(portfolioName);
    try {
      return p.performanceOverTime(startDate, endDate);
    } catch (IOException e) {
      throw new IllegalArgumentException("You have no data in these dates");
    }
  }

  /**
   * Finds the portfolio from the list of portfolios into a usable betterPortfolio.
   *
   * @param name the name of the portfolio
   * @return the IBetterPortfolio
   * @throws IllegalArgumentException if no portfolios with that name exist.
   */
  private IBetterPortfolio find(String name) throws IllegalArgumentException {
    for (int i = 0; i < this.portfolios.size(); i++) {
      String tempName = this.portfolios.get(i).getName();
      if (tempName.equals(name)) {
        return (IBetterPortfolio) this.portfolios.get(i);
      }
    }
    throw new IllegalArgumentException("there are no portfolios with this name");
  }

  /**
   * Adds a portfolio to the current list of portfolios if the user can.
   *
   * @param name the name of the portfolio
   * @throws IllegalArgumentException if a portfolio exists with that name.
   */
  @Override
  public void addPortfolio(String name) {
    IBetterPortfolio newPort = new BetterPortfolio(name);
    portfolios.add(newPort);
  }


  @Override
  public String getDistribution(String portfolioName, String date) throws IOException {
    IBetterPortfolio p = this.find(portfolioName);
    return p.distribution(date);
  }

  @Override
  public String getSellableAtDate(String portfolioName, String date)
          throws IllegalArgumentException {
    IBetterPortfolio p = this.find(portfolioName);
    List<List<String>> sellable = p.getSellable(date);
    return this.getDataFromComp(sellable);
  }

  /**
   * Gets a list of the files that are saved in the savedPortfolios in the res.
   * Mutates this.retreivable portfolios to have the files.
   */
  private void getRetrievablePortfolios() {
    String directoryPath = "./res/savedPortfolios/";
    Path directory = Paths.get(directoryPath);
    ArrayList<String> result = new ArrayList<String>();
    if (directory.toFile().exists()) {
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {

        for (Path file : stream) {
          String name = file.getFileName().toString();
          result.add(name.substring(0, name.length() - 4));
        }
      } catch (IOException e) {
        result.remove(result.size() - 1);
      }
    }
    this.retrievablePortfolios = result;
  }
}