package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class MarketDataProcessor {
	//assume 5 windows(0-4) in total and default is -1
	private final int windowSize = 5;
	private final int defaultWindow = -1;
	private final int throttle = 100;
	private final int volumeThrottle = 10000;
	private ConcurrentHashMap<Integer, Integer> windowCount;
	private LinkedHashMap<String, MarketData> marketDataStore;
	//startTime for this class 
	private LocalDateTime startTime;
	private Integer currentWindow;
	
	public MarketDataProcessor() {
		marketDataStore = new LinkedHashMap<String, MarketData>();
		windowCount = new ConcurrentHashMap<Integer, Integer>();
		setCurrentWindow(defaultWindow);
		startTime = LocalDateTime.now();		
	}
//	
	
	// Receive incoming market data // being call per second
	public void onMessage(MarketData data) {
	// Please implement
		updateWindow();
		int window = getCurrentWindow();
		int count = windowCount.get(window);
		// run only when it's before throttle
		if(count <= volumeThrottle){
			//update more than once
			marketDataStore.put(data.getSymbol(), data);
//			System.out.println("Successful: "+data.getSymbol()+" at window "+window+", price = "+data.getPrice()+", count = "+count);
		}else {
//			System.out.println("throttle: "+data.getSymbol()+", count = "+count);
		}
		windowCount.put(window, count + 1);	
		updateWindow();
	}
	
	
	// Publish aggregated and throttled market data
	public void publishAggregatedMarketData(MarketData data) {
	// Do Nothing, assume implemented.
	}
	
	//publish
	public void updateWindow() {
		Duration duration = Duration.between(this.startTime, LocalDateTime.now());
		Integer window = (int)duration.getSeconds()%windowSize;
		//clean datamap
		if(window != currentWindow) {
			int count = 0;
			List<String> latestFirst = new ArrayList<String>(marketDataStore.keySet());
			if(latestFirst.size() > 0) {
				Collections.reverse(latestFirst);
				for (String key : latestFirst) {
					publishAggregatedMarketData(marketDataStore.get(key));
					System.out.println("Published key: "+key+" at price"+marketDataStore.get(key).getPrice() );
					count++;
					if(count >= throttle) break;
				}
			}	
			windowCount.put(window, 0);
			setCurrentWindow(window);
		}
		//every time when window = 0, clear the queue
		if(window == 0)
			marketDataStore.clear();
	}

	public void printCurrentWindow() {
		updateWindow();
		System.out.print(getCurrentWindow());
	}

	public Integer getCurrentWindow() {
		return currentWindow;
	}

	public void setCurrentWindow(int currentWindow) {
		this.currentWindow = currentWindow;
	}

}


