The file contains features that allow the user to use a text interface with the following functionality:

Create new portfolios
Add to existing portfolios
Get the value of the portfolios
Add and portfolios to a portfolio list.
All of the features above work as intended
Find the composition, value and distribution of value of a portfolio on a specified date.
Save an existing portfolio and retrieve a previously saved portfolio.
Inspect whether a stock lost or gained on a specified day.
Compute the x-day moving average for a given stock.
Compute and report the x-day crossover days for a given stock within a specified period.
Do all of the above for any stock that is available in the AlphaVantage API.
Allow balancing of an existing portfolio with the given weights on a specified date .
Plot a bar chart that illustrates the performance of a given stock or existing portfolio over a given time frame.
All of the above features work.

This file also contains features that allow the user to use a Graphical User Interface with the following functionality:

The ability to create a new portfolio
The ability to buy/sell stocks by specifying the stock, number of shares, and date
The ability to query the value and composition of a portfolio at a certain date
The ability to save and retrieve portfolios from files


It contains interfaces that are related to stocks, financial history, portfolios, validating, users, controllers and views.

Model ----
The IPortfolio interface contains methods for (PersonalPortfolio contains the following functionality) :
getting a portfolios value
adding a stock to the portfolio
getting the name of this portfolio

The IBetterPortfolio interface contains the methods from IPortfolio but also contains methods for (Better Portfolio contains the following functionality) :

Getting a portfolios composition at a date
Buying a whole number amount of stock at a date
Selling a float number amount of stock at a date
Saving a portfolio to a directory
Loading a saved portfolio
Rebalancing a portfolio given float(Percent) parameters for each stock to rebalance them to at a date
Seeing a chart that shows the performance of a portfolio over a given time frame
Seeing the distribution of a portfolio at a given date

The IHistory interface contains methods for (FinancialHistory contains the following functionality):

getting the information from a file on your computer
creating a new file on the computer that contains stock information from a website
Sorting this information into an arraylist of arraylist of string
NOTICE: Historical data only goes up till June 6 2024 so iffy funtionality may occur with more recent dates
Getting the date of a set of information
Getting the opening value of a stock at a date
Getting the high of a stock at a date
Getting the low of a stock at a date
Getting the closing value of a stock at a date
Getting the trading volume of a stock at a date
Get the place of a date of a stock in the arrayList of data

The BasicStock interface contains methods for (BaseStock contains the following functionality):

Calculating the change of a stock over a specified range
Calculating the moving average of a stock on a date
Calculating which days are x-day crossover days over a certain period of time.
getting the stock price at a certain date
Getting the stock historical data of a date
Getting the name of the stock being operated on

The IBetterStock interface contains the methods from BasicStock but also contains methods for (BetterStock contains the following functionality):

Finding all available trading days between a given range
Finding the amount of a stock can be purchased given an amount of dollars and a date
Seeing a chart that shows the performance of a stock over a given time frame

The IUser interface contains methods for (User contains the following functionality):

Adding a new portfolio
Adding to a portfolio by adding a given amount of a given stock
Getting all active portfolios
Getting the value of a portfolio at a given date

The IBetterUser interface contains methods for (BetterUser contains the following functionality):
Buying a given amount of a stock to a given portfolio given a date
Selling a given amount of a stock to a given portfolio given a date
Rebalancing a portfolios stocks given percentage parameters and a date
Getting the composition of one of its portfolios on a given date
Saving a portfolio as a .csv file
Loading a previously saved portfolio given a name
Retrieving the names of all previously saved portfolios
Getting the performance chart over a given time frame of one of its portfolios
Getting the distribution of one of its portfolios at a given date

The IValidate interface contains methods for (Validate contains the following functionality):
Validating a given date is a trading day
Validating a given ticker is an existing stock
Validating that a given float input is a positive integer
Validating that a start date doesn't come after an end date
Getting the stepper amount given a Range(amount to increment dates for the portfolio/stock charts)

Controller ----
The BasicController serves to let the user
create a portfolio
add portfolios
add stock to existing portfolios
find the stock change over a range
find the average of a stock on a day
find the x-day crossovers over a range
find the x-day moving average on a date

The BetterController contains the above functionality but also lets a user
Buy stock
Sell stock
Get a stock or portfolio performance chart
Get the distribution of a portfolio
Rebalance a portfolio
Get the composition of a portfolio
Save a portfolio
Retrieve a previously saved portfolio

View ----

The BasicView
The view contains methods for the user to see what they need to input
display messages
error messages
prompts
menu
ending message

The BetterView contains the above functionality but has a different menu and 'no portfolio' message

The GuiView(Implements IView) contains functionality for -

The ability to create a new portfolio
The ability to buy/sell stocks by specifying the stock, number of shares, and date
The ability to query the value and composition of a portfolio at a certain date
The ability to save and retrieve portfolios from files


If you do testing, run each test separately and in between tests, delete anything in the savedPortfolios directory.
If not, the computer will take the saved data of the previous test and apply it to the next test.
