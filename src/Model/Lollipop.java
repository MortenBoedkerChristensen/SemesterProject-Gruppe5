package Model;

import java.sql.Date;

public class Lollipop extends Candy {

    public Lollipop(int candyID, String type, int stock, int minStock, int maxStock, int price, Date date) {
        super(candyID, type, price, minStock, maxStock, date, stock);
    }

    public Lollipop(Candy candy, Recipes recipe) {
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
