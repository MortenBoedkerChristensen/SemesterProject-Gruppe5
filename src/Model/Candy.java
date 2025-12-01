package Model;

import java.sql.Date;

public class Candy {
	private int Price;
	private String Type;
	private int id;
	private int Stock;
	private int minStock;
	private int maxStock;
	private Date date;
	
	public Candy(int id, String type, int price, int stock, int minStock, int maxStock, Date date) {
		this.id = id;
		this.Type = type;
		this.Price = price;
		this.Stock = stock;
		this.minStock = minStock;
		this.maxStock = maxStock;
		this.date = date;
	}
	
	public void setPrice(int price) {
		this.Price = price;
	}
		
	public int getPrice() {
		return Price;
	}
	
	
	public void setType(String type) {
		this.Type = type;
	}
	
	public String getType() {
		return Type;
	}
	
	public void setPriceID(int id) {
		this.id = id;
	}
	
	public int getPriceID() {
		return id;
	}
	
	public void setStock(int stock) {
		this.Stock = stock;
	}
	
	public int getStock() {
		return Stock;
	}
	
	public void setMinStock(int minStock) {
		this.minStock = minStock;
	}
	
	public int getMinStock() {
		return minStock;
	}
	
	public void setMaxStock(int maxStock) {
		this.maxStock = maxStock;
	}
	
	public int getMaxStock() {
		return maxStock;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
}
