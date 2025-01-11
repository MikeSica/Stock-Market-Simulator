For Set-up you need to have Java installed if you don't already and you should have an internet connection

You can do one of the following for the GUI interface
    - Navigate to the META-INF Folder in your finder(Stock Market Simulator/res/META-INF/ and just double-click the .jar file
    - Or navigate to the same path and run $ java -jar StockMarketSimulator.jar
For the text-Based interface do the following
    - Navigate to the jar file in your terminal and then run $ java -jar StockMarketSimulator.jar -text

You may have to also put files(stock.csv files) in the META-INF folder(same root as jar) by making duplicates.

There is stock data for past 1999-11-01 so don't use a prior date and if a date isn't a trading
day(when using text based UI) it will ask for a new date.

If you want to use the program without internet you can but you would have to drag the .csv files(stock info files) that
get created when you buy a stock out of the META-INF Folder and then out of the res folder (same source as res,src,test)

Also, note that since the jar is located in the META-INF its creating a res folder inside it that contains a
savedPortfolios folder which contains the saved Portfolios, if you want to change this try dragging the jar into the res
folder, I tried this but it didn't work one of my computers, either way it doesn't affect the user experience but I
just wanted to point it out.

NOTICE: Historical data only goes up till June 6 2024 so to avoid bugs pick dates!!!