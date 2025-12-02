package Model;

import java.sql.Date;

public class Candy {
	private int price;
	private String type;
	private int id;
	private int stock;
	private int minStock;
	private int maxStock;
	private Date date;
	
	public Candy(int id, String type, int price, int stock, int minStock, int maxStock, Date date) {
		this.id = id;
		this.type = type;
		this.price = price;
		this.stock = stock;
		this.minStock = minStock;
		this.maxStock = maxStock;
		this.date = date;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
		
	public int getPrice() {
		return price;
	}
	
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setPriceID(int id) {
		this.id = id;
	}
	
	public int getPriceID() {
		return id;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public int getStock() {
		return stock;
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
