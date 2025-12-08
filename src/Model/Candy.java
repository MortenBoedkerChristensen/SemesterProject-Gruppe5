package Model;

import java.sql.Date;

public class Candy {

    private int candyID;
    private String type;
    private int price;
    private int minStock;
    private int maxStock;
    private Date date;
    private int stock;

    public Candy(int candyID, String type, int price, int minStock, int maxStock, Date date, int stock, String name) {
		this.stock = stock;
		this.candyID = candyID;
        this.type = type;
        this.price = price;
        this.minStock = minStock;
        this.maxStock = maxStock;
        this.date = date;
    }

    // Getters
    public int getCandyID() { return candyID; }
    public String getType() { return type; }
    public int getPrice() { return price; }
    public int getStock() { return stock; }
    public int getMinStock() { return minStock; }
    public int getMaxStock() { return maxStock; }
    public Date getDate() { return date; }
    public String getName() { return type; }

    // Setters
    public void setType(String type) { this.type = type; }
    public void setPrice(int price) { this.price = price; }
    public void setMinStock(int minStock) { this.minStock = minStock; }
    public void setMaxStock(int maxStock) { this.maxStock = maxStock; }
    public void setDate(Date date) { this.date = date; }
    public void setStock(int stock) { this.stock = stock; }
    public void setCandyID(int candyID) { this.candyID = candyID; }
    public void setName(String name) { this.type = name; }
}
