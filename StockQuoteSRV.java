package server;
//LCR
//Main Server Program


import java.io.*;
import java.net.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.DecimalFormat;
import common.StockQuoteIF;

public class StockQuoteSRV implements StockQuoteIF {
	//Define getQuote method
	@Override
	public StockQuote getQuote(String ticker) throws IOException {
		//Initial formatting information
	      String api_key = "MVGFHOZG426RG9Y9";
	      DecimalFormat dollars = new DecimalFormat("$#,##0.00;-$#,##0.00");
	      DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
	      //Get information from API
	          URL url = new URL("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + ticker + "&apikey=" + api_key + "&datatype=csv");
	          URLConnection conn = url.openConnection();
	          BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	          //Copy information from API
	          in.readLine();
	          String quoteString = in.readLine();
	          in.close();
	          
	          if (quoteString == null) {
	              System.out.println("Requested data is not available.");
	              System.exit(0);
	          }
	          //split information into pieces
	          String[] data = quoteString.split(",");
	          if (data.length != 10) {
	              System.out.println("Bad output: " + quoteString);
	              System.exit(0);
	          }

	          //copy needed information to variables
	          double high = Double.parseDouble(data[2]);
	          double low = Double.parseDouble(data[3]);
	          double price = Double.parseDouble(data[4]);
	          double previousClose = Double.parseDouble(data[7]);

	          //format strings for new object
	        String Sym = data[0];
	          String CurrentPrice = ("Current price: " + dollars.format(price));
		         String Change = ("Change: " + dollars.format(price - previousClose));
		          String DailyHigh = ("Daily High: " + dollars.format(high));
		          String DailyLow = ("Daily Low: " + dollars.format(low));
		          //create object
		          StockQuote obj = new StockQuote();
		          //assign values to object
		          obj.header = (Sym + " as of " + df.format(new java.util.Date()));;
		          obj.currentPrice = CurrentPrice;
		          obj.priceChange = Change;
		          obj.dailyHigh = DailyHigh;
		          obj.dailyLow = DailyLow;
		          //return object
	          return obj;
	      }




	public static void main(String[] args) throws Exception {
		try {
		StockQuoteIF server = new StockQuoteSRV();
		StockQuoteIF stub = (StockQuoteIF) UnicastRemoteObject.exportObject(server, 0);
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("Server", stub);
		System.out.println("Stock Quote Server Running.");
		} catch (Exception e) {
		e.printStackTrace();
		}
	}
	      
	}
	

