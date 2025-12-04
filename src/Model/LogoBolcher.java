package Model;

import java.sql.Date;

public class LogoBolcher extends Candy {
	
	
	private String firma;
	public LogoBolcher(int candyID, String type, int price, int stock, int minStock, int maxStock, java.sql.Date date) {
		super(candyID, type, price, stock, minStock, date, maxStock);
		
	}
	
	public LogoBolcher(Candy candy, Recipes recipe) {
	    super(
	    		recipe.getCandyID(),
                recipe.getType(),
                candy.getPrice(),
                candy.getStock(),
                candy.getMinStock(),
                new Date(System.currentTimeMillis()),
                candy.getMaxStock()     
                );
	    
	}
	
	public void setFirma(String firma) {
		this.firma = firma;
	}
	
	public String getFirma() {
		return firma;
	}
	

}
