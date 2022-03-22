package server;
//LCR
//StockQuote Object Class
import java.io.Serializable;

@SuppressWarnings("serial")
//create stockquote object
public class StockQuote implements Serializable{
	//constructor
	public StockQuote() {

	}
	//strings
	public String header;
	public String currentPrice;
	public String priceChange;
	public String dailyHigh;
	public String dailyLow;

}
