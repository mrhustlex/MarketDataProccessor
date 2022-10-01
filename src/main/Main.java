package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
	final static int minTestEach = 10;
	
	public static void main(String[] args) throws InterruptedException{
		Helper helper = new Helper();
		//Ensure that the number of calls of publishAggregatedMarketData method < 100/s 
//		limitCallPerSec(helper);
		//Ensure that each symbol does not update more than once per sliding window.
//		updateOnlyOncePerSymbol(helper);
//		Ensure that each symbol always has the latest market data published.
//		Ensure the latest market data on each symbol will be published.
		alwaysLatestData(helper);
	}
	
	public static void limitCallPerSec(Helper helper) throws InterruptedException {
		//Ensure that the number of calls of publishAggregatedMarketData method < 100/s  
//        for(int i = 0; i<minTestEach; i++) {
        	List<MarketData> publishedData = new ArrayList<MarketData>();
    		MarketDataProcessor mdp = new MarketDataProcessor(){
                @Override
                public void publishAggregatedMarketData(MarketData data) {
                	super.publishAggregatedMarketData(data);
                	publishedData.add(data);
                }
            };
            // 5 to 10 s per test for now
            int duration = helper.getRandomInt(10, 20);
//        	helper.generateMarketDataWithDuration(mdp, duration, helper.getRandomInt(10, 30));
//			no sleep time(highest throughput)
            helper.generateMarketDataWithDuration(mdp, duration, 0,100);
        	String result = (publishedData.size()/duration<= 100)?"Pass":"Failed";
        	System.out.println("limitCallPerSec");
        	System.out.println("Case:"+result+", rate:(publish/second)"+publishedData.size()/duration);
//        	System.out.println("Data: "+publishedData);
        	System.out.println("Total published: "+publishedData.size());
//        	assert publishedData.size()/duration <= 100;	
        
//        }
	}
	
	public static void updateOnlyOncePerSymbol(Helper helper) throws InterruptedException {
        //Ensure that each symbol does not update more than once per sliding window.
        for(int i = 0; i<minTestEach; i++) {
    		//Ensure that the number of calls of publishAggregatedMarketData method < 100/s       
    		//publishedData as output
        	HashMap<String, Boolean> publishCount = new HashMap<String, Boolean>();
    		MarketDataProcessor mdp = new MarketDataProcessor(){
                @Override
                public void publishAggregatedMarketData(MarketData data) {
                	super.publishAggregatedMarketData(data);
                	if(!publishCount.containsKey(data.getSymbol()))
                		publishCount.put(data.getSymbol(), true);
                	else
                		publishCount.put("duplicated", true);
                }
            };
            // only call for 1 second with as many symbol as possible
            //random duration
        	int duration = 1;	
        	helper.generateMarketDataWithDuration(mdp, duration, 0,100);
        	String result = (!publishCount.containsKey("duplicated"))?"Pass":"Failed";
        	System.out.println("updateOnlyOncePerSymbol");
        	System.out.println("Published:"+ publishCount.size()+" symbols in one second window");
        	System.out.println("Case:"+i+": "+result);
        }
	}
	
	public static void alwaysLatestData(Helper helper) throws InterruptedException {
        //Ensure that each symbol always has the latest market data published.
    	HashMap<String, MarketData> publishedrecord = new HashMap<String, MarketData>();
		MarketDataProcessor mdp = new MarketDataProcessor(){
            @Override
            public void publishAggregatedMarketData(MarketData data) {
            	super.publishAggregatedMarketData(data);
            	publishedrecord.put(data.getSymbol(), data);
            }
        };
        //total 30 symbols
    	helper.generateMarketData(mdp, 30);

//    	String res = result.size() == 0? "Pass":"Failed";
    	System.out.println("alwaysLatestData");
    	//The print result is also the fourth(after window change)
    	System.out.println("Case:(total symbols) " +publishedrecord.size());
    	
	}
	
	
}