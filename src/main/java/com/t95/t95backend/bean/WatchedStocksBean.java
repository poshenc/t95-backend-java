package com.t95.t95backend.bean;

import com.t95.t95backend.entity.Stock;

public class WatchedStocksBean {
	
	String name;
	String symbol;
	String price;
	String movementPrice;
	String movementPercentage;
	
	
	public WatchedStocksBean(Stock stock) {
		super();
		this.name = stock.getName();
		this.symbol = stock.getStockCode();
		this.price = stock.getPrice();
		this.movementPrice = stock.getMovementPoints();
		this.movementPercentage = stock.getMovementPercentage();
	}


	public WatchedStocksBean() {
		super();
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMovementPrice() {
		return movementPrice;
	}
	public void setMovementPrice(String movementPrice) {
		this.movementPrice = movementPrice;
	}
	public String getMovementPercentage() {
		return movementPercentage;
	}
	public void setMovementPercentage(String movementPercentage) {
		this.movementPercentage = movementPercentage;
	}
	
	
	
}
