package main;

import java.time.LocalDateTime;
//import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Helper {
//	final List<String> symbols = Arrays.asList("AMZN", "GOOG", "AAPL");
//	List<List<String>> marketData;
	List<String> symbols;
	
	public Helper() {
		CSVReader reader = new CSVReader();
		symbols = reader.readLine("nasdaq-listed-symbols.csv");
//		Log insert
//		for(String symbol: symbols) {
//			System.out.println(symbol);
//		}
//		System.out.print("Inserted size of symbol:"+symbols.size());
	}
	
	public void generateMarketDataWithDuration(MarketDataProcessor mdp, int duration, int sleep, int offset) throws InterruptedException {
		long nowMillis = System.currentTimeMillis();
		int elapsedTime = 0;
        while(true && duration * 1000 >= elapsedTime) { 
        	int price = elapsedTime/100 + offset;
        	MarketData md = new MarketData(symbols.get(getRandomInt(0, symbols.size()-1)), price, LocalDateTime.now());
        	mdp.onMessage(md);
        	//random sleep
        	Thread.sleep(sleep);
        	elapsedTime = (int)((System.currentTimeMillis() - nowMillis));
        }
    }
	
	public void generateMarketData(MarketDataProcessor mdp, int numOfItemPerSymbol) throws InterruptedException {
		for(int i = 0; i< numOfItemPerSymbol; i++) {
			for(String symbol: symbols) {
//				non-random price(price equal to iteration
				MarketData md = new MarketData(symbol, i , LocalDateTime.now());
	        	mdp.onMessage(md);
	        	//random sleep
//	        	Thread.sleep(getRandomInt(1, 20));
			}
		}
    }
	
	public int getRandomInt(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}
	
	public double getRandomDouble(double min, double max) {
		return min+(new Random().nextDouble()*(max-min));
	}
}
