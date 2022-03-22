package client;
//LCR
//Main Client Program
import java.io.IOException;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.Scanner;

import common.StockQuoteIF;
import server.StockQuote;

public class StockQuoteCL {
	private StockQuoteIF server;
	
	//Constructor
	public StockQuoteCL() {
		
	}

	//Start Client
	public void CLstart(){
		//Find Server
		try {
		Registry registry = LocateRegistry.getRegistry("localhost");
		server = (StockQuoteIF) registry.lookup("Server");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	public String retrieve(String header, String cPrice, String pChange, String dHigh, String dLow) {
		String info = header;
		info = info.concat(" " + cPrice);
		info = info.concat(" " + pChange);
		info = info.concat(" " + dHigh);
		info = info.concat(" " + dLow);
		return null;
		
	}
	//Define getQuote in client class
	public StockQuote getQuote(String symbol) throws IOException {
		return server.getQuote(symbol);	
	}
	//Main Method
	public static void main(String[] args) throws NotBoundException, IOException {
		//Start new instance of StockQuoteCL
		StockQuoteCL cl = new StockQuoteCL();
		//Find Server
		cl.CLstart();
		//Ask for ticker symbol
		Scanner in = new Scanner(System.in);
			System.out.println("Enter Ticker Symbol");
			String s = in.nextLine();
		//Option to quit
		if(!s.equalsIgnoreCase("q")) {
		//Send symbol to server
		StockQuote SQ = cl.getQuote(s);
		//Get and print elements of StockQuote
		System.out.println(SQ.header);
		System.out.println(SQ.currentPrice);
		System.out.println(SQ.priceChange);
		System.out.println(SQ.dailyHigh);
		System.out.println(SQ.dailyLow);
		}
		//Exit program if q is typed
		else {
			System.out.println("Program Exited");
		}
		in.close();
	}
}
    