package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the data for the given stock. Stores it in a file and an arraylist of
 * arraylist string. Contains methods to get the data from the file.
 */
public class FinancialHistory implements IHistory {
  private String apiKey = "PBBOCARQLUUHKQBA";
  protected String stockSymbol;
  private List<List<String>> stockData;
  private File file;

  /**
   * Constructor for financial history, gets the data from the file or creates the file.
   *
   * @param stockSymbol the symbol of the stock
   * @throws MalformedURLException if the URL doesn't work when creating the file
   * @throws IOException           when the URL does not have information on the file
   */
  public FinancialHistory(String stockSymbol) throws MalformedURLException, IOException {
    stockSymbol = stockSymbol.toUpperCase();
    stockData =  new ArrayList<>();
    this.stockSymbol = stockSymbol;
    String tempName = stockSymbol + ".csv";
    file = new File(tempName);
    if (file.exists()) {
      this.getDataFromFile();
    } else {
      this.createFile();
    }

  }

  /**
   * Gets the price at the opening for the given date.
   *
   * @param date the given date
   * @return the price at that time
   * @throws IllegalArgumentException if the price cannot be found
   */
  public float getPriceAtOpening(String date) throws IllegalArgumentException {
    return this.getData(date, 1);
  }

  /**
   * Gets the price at closing for the given date.
   *
   * @param date the given date
   * @return the price at that time
   * @throws IllegalArgumentException if the price cannot be found
   */
  public float getPriceAtClosing(String date) {
    return this.getData(date, 4);
  }

  /**
   * Gets the highest price for the given date.
   *
   * @param date the given date
   * @return the price at that time
   * @throws IllegalArgumentException if the price cannot be found
   */
  public float getHighestPrice(String date) {
    return this.getData(date, 2);
  }

  /**
   * Gets the lowest price for the given date.
   *
   * @param date the given date
   * @return the price at that time
   * @throws IllegalArgumentException if the price cannot be found
   */
  public float getLowestPrice(String date) {
    return this.getData(date, 3);
  }

  /**
   * Gets the volume of trades for the given date.
   *
   * @param date the given date
   * @return the volume for the given date
   * @throws IllegalArgumentException if the volume cannot be found
   */
  public float volumeofTrade(String date) {
    return this.getData(date, 5);
  }

  /**
   * Gets the arraylist of arraylist of string of the stock data.
   * @return an arraylist of arraylist of string
   */
  public List<List<String>> getStockHistory() {
    List<List<String>> result = new ArrayList<>();
    result.addAll(this.stockData);
    return result;
  }

  /**
   * Helper function to get data based on where it is located in the arrayList string.
   *
   * @param date the date
   * @param data index of the data
   * @return the outputted number.
   * @throws IllegalArgumentException if the data didn't give anything
   */
  private float getData(String date, int data)
          throws IllegalArgumentException {
    float price = 0;
    for (int i = 0; i < stockData.size(); i++) {
      if (date.equals(stockData.get(i).get(0))) {
        price = Float.parseFloat(stockData.get(i).get(data));
      }
    }
    if (price == 0) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    return price;
  }

  /**
   * Retrieves the index of the date in the stock data.
   *
   * @param date the date for which to retrieve the index.
   * @return the index of the date in the stock data.
   * @throws IllegalArgumentException if the date isn't valid
   */
  public int getDatePlace(String date) throws IllegalArgumentException {
    int index = -1;
    for (int i = 0; i < stockData.size(); i++) {
      if (this.stockData.get(i).get(0).equals(date)) {
        index = i;
      }
    }
    if (index == -1) {
      throw new IllegalArgumentException("Invalid date");
    }
    return index;
  }

  /**
   * Creates a file for a given stock if it exists, if it doesn't exist it throws
   * a FileNotFoundException.
   *
   * @throws FileNotFoundException when there is no data for the stock ticker.
   */
  private void createFile() throws FileNotFoundException {
    String name = stockSymbol + ".csv";
    URL url = null;
    InputStream in = null;
    BufferedReader rn = null;
    List<String> data;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    try {
      in = url.openStream();
      String line;
      FileWriter myWriter = new FileWriter(name);
      rn = new BufferedReader(new InputStreamReader(in));
      while ((line = rn.readLine()) != null) {
        myWriter.write(line + "\n");
        if (line.contains("Error Message") || line.contains("Information")
                || line.contains("API")) {
          Path path = Paths.get("./" + this.stockSymbol + ".csv");
          myWriter.close();
          Files.delete(path);
          throw new IOException("Stock doesn't exist");
        }
      }
      myWriter.close();

    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    this.getDataFromFile();
  }

  /**
   * Gets the data from a file that is in the computer.
   * Stores it in an arraylist of arraylist string.
   * Sets that list equal to this.stockData.
   *
   * @throws FileNotFoundException if there is no file in the computer
   */
  private void getDataFromFile() throws FileNotFoundException {
    List<String> data = new ArrayList<String>();
    Scanner myReader = new Scanner(this.file);
    while (myReader.hasNextLine()) {
      data.add(myReader.nextLine());
    }
    List<List<String>> finalData = new ArrayList<>();
    for (int i = 1; i < data.size(); i++) {
      String help = data.get(i);
      String[] arr = help.split(",");
      List<String> list = new ArrayList<>(Arrays.asList(arr));
      finalData.add(list);
    }
    this.stockData = finalData;
  }


}





