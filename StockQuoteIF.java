package common;
//LCR
//Interface
import java.io.IOException;
import java.rmi.*;
import server.StockQuote;

public interface StockQuoteIF extends Remote {
	//Define getQuote, throw exceptions
	public StockQuote getQuote (String ticker) throws IOException, RemoteException;

}
