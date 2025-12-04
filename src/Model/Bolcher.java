package Model;

import java.sql.Date;

public class Bolcher extends Candy {

	public Bolcher(int candyID, String type, int price, int stock, int minStock, int maxStock, java.sql.Date date) {
		super(candyID, type, price, stock, minStock, date, maxStock);
    }

    public Bolcher(Candy candy, Recipes recipe) {
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
}
