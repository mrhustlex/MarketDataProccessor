package main;
import java.time.LocalDateTime;

public class MarketData {
    private String symbol;
    private double price;
    private LocalDateTime updateTime;
    
    public MarketData() {
    	this.symbol = "";
    	this.price = 0;
    	this.updateTime = LocalDateTime.now();
    }
    
    public MarketData(String symbol, double price, LocalDateTime updateTime) {
    	this.symbol = symbol;
    	this.price = price;
    	this.updateTime = updateTime;
    }
    
    public void copy(MarketData data) {
    	if(data != null) {
    	    setSymbol(data.getSymbol());
    	    setPrice(data.getPrice());
    	    setUpdateTime(data.getUpdateTime());
    	}
    }

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}
	

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public void print() {
		System.out.println("Symbol: "+this.symbol+", Price: "+this.price+ ", Time: "+ this.updateTime);
	}
}
