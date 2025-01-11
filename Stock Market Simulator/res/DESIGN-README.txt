Changes -- The following is modifications or additions to existing code for the purpose of design enhancement(Assignment5.0)  --

(Assignment 6.0 changes are detailed later on)

Controller
-- IController(Interface) --
Added a method goNowHelp which contains the switch case functionality for goNow(refactor)


--- Basic Controller(class) ---
Used composition(created Validate object) in order to cut down on private helpers that helped to identify if a date was a valid trading day and if a quantity was valid when a user inputted a quantity or date.
Changed the goNow so that it now sends the userInstruction to goNowHelp which allows for only one command to pass through if its extended.
Changed the getValidDate helper so that now when dates are inputted from the user end it is more friendly and easy. Also we refactored try/catch to keep the command design pattern as clean and readable as possible. 

Model
-- Basic Stock(Interface) ---
Added a method to get the name of a stock(ticker symbol) which allowed for us being able to print the names of the stocks owned in the portfolio(used in composition method as an example). We also changed BaseStock(class) so that this was implemented and added a field tickerSymbol(String) containing it which is initialized in the constructor to the tickerSymbol.

View
--- BasicView(class) ---
Changed to use appendable rather than being highly coupled to System.out. Also changed StockProgram to account for appendable and pass it through the view.


The above modifications were all with intent to enhance the design and testing for the modifications was done.

----

The following were additions to the program

Controller

-- BetterController(class) --
This class extends BasicController and has all its functionality but also provides functionality to allow a user to
Buy stock
Sell stock
Get a stock or portfolio performance chart
Get the distribution of a portfolio
Rebalance a portfolio
Get the composition of a portfolio
Save a portfolio
Retrieve a previously saved portfolio.

It's constructor takes in a IBetterUser, readable and IView to set its fields and also initializes the validater(composition) to allow us to avoid duplicate code and use the validate to help check if dates and inputs are valid.

Model

-- IBetterStock(Interface) extends BasicStock but also contains methods for --
Finding all available trading days between a given range
Finding the amount of a stock can be purchased given an amount of dollars and a date
Seeing a chart that shows the performance of a stock over a given time frame

This functionality is implemented in BetterStock(class) which extends BaseStock(class) retaining all its functionality.
It has fields ticker(String), financeHistory(FinancialHistory), historyData(List<List<String>>) and validate(object to stop code reuse in validating trading days/dates and valid quantities) that are initialized in the constructor.

-- IBetterPortfolio(Interface) extends IPortfolio but also contains methods for --
Getting a portfolios composition at a date
Buying a whole number amount of stock at a date
Selling a float number amount of stock at a date
Saving a portfolio to a directory
Loading a saved portfolio
Rebalancing a portfolio given float(Percent) parameters for each stock to rebalance them to at a date
Seeing a chart that shows the performance of a portfolio over a given time frame
Seeing the distribution of a portfolio at a given date

It's implemented in BetterPortfolio(class) which extends PersonalPortfolio(class) but overrides the valueOfPortfolio method because in BetterPortfolio there is a purchase/selling history so the method is different.

Has a constructor that sets its fields(name of portfolio(string), the stocks(List<List<String>>) and a validate object(avoids code reuse to determine valid dates/quantities)

-- IBetterUser(Interface) extends IUser but also contains methods for --
Buying a given amount of a stock to a given portfolio given a date
Selling a given amount of a stock to a given portfolio given a date
Rebalancing a portfolios stocks given percentage parameters and a date
Getting the composition of one of its portfolios on a given date
Saving a portfolio as a .csv file
Loading a previously saved portfolio given a name
Retrieving the names of all previously saved portfolios
Getting the performance chart over a given time frame of one of its portfolios
Getting the distribution of one of its portfolios at a given date

It's implemented in BetterUser(class) which extends User(class) and retains all its functionality.
BetterUser also has a constructor which initializes the fields portfolios(ArrayList<IPortfolio>()) and retrieveablePortfolios(ArrayList<String>).

-- IValidate(Interface) contains methods for --
Validating a given date is a trading day
Validating a given ticker is an existing stock
Validating that a given float input is a positive integer
Validating that a start date doesn't come after an end date
Getting the stepper amount given a Range(amount to increment dates for the portfolio/stock charts)

This functionality implemented in Validate(class)

-- Range(enum) --
Can be assigned to either DAY, MONTH, WEEK or YEAR
It's used to calculate the stepper in Validate

View
-- BetterView(class) --
It extends BasicView but has a different menu display(more options now) and a different noPortfolio display(to account for retrieving possibilities.
It has an appendable(Field) which is initialized in its constructor.

------

The following remained largely the same as before,

The IHistory interface (FinancialHistory which contains the following functionality):

getting the information from a file on your computer
creating a new file on the computer that contains stock information from a website
Sorting this information into an List of List of string
Getting the date of a set of information
Getting the opening value of a stock at a date
Getting the high of a stock at a date
Getting the low of a stock at a date
Getting the closing value of a stock at a date
Getting the trading volume of a stock at a date
Get the place of a date of a stock in the list of data

The functionality of BaseStock(slight addition), User(no change), BasicView(no change), PersonalPortfolio(slight addition), BasicView(no change), and basic controller(slight addition) remained mostly the same

We changed the ArrayList functionality into the List interface functionality for easier access.
We changed the Hashmap in PersonalPortfolio to be an item of Map and allowed it to store floats.
Changed the FinancialHistory to delete the file if the data doesn't exist because it was bugged earlier.

----- More Changes done to existing code for design enchancement (Assignment 6.0): -----

We fixed a bug of the user seeing the non-sellable stocks when they want to sell something.
We did this by adding Sellable to the user and portfolio to get a list of sellable stocks.
Added IllegalArgumentException to valueOfPortfolio in IPortfolio(Interface) to account for invalid
dates and did the same in the classes that implement it (BetterPortfolio and PersonalPortfolio)
Did the same to CompAtDate for invalid portfolio names and invalid trading days. We enhanced
getPortfolios to be separated with line separators to allow easier access. We changed the return
type of rebalanceHelperMessage in BetterController to be a List<String instead of an
ArrayList<String>.

In short -

IBetterPortfolio(Interface) - made getSellables method public and debugged it
                            - added IllegalArgumentException to valueOfPortfolio
                            - enhanced getPortfolios method
                            - added IllegalArgumentException to compAtDate(changed implementations accordingly)

IBetterUser(Interface) - added a getSellables method(changed implementations accordingly)
                       - added IllegalArgumentException to getValueOfPortfolio
                       - added IllegalArgumentException to getCompAtDate(changed implementations accordingly)

BetterController(Class) - changed return type of rebalanceHelperMessage

All other functionality remained untouched from assignment 5.0 and works the same.

---- The following are additions to the program(Completely new additions - assignment 6.0) ----

View

We added a GuiView class that implements our IView interface
that shows a graphical user interface to the user and is compatible with our
previous BetterController.

We also wanted to note there are some methods in GuiView that are left blank.
We chose to leave it like this because there are use cases in the future where they
could be implemented and it wouldn't be logical to go back and change all our
existing code(IView interface and more) to be able to remove them.

Also, some tests may run incorrectly if you have stock files that are more up to date than what are not
expected or if there are saved portfolios that are not expected to be there.



