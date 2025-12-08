package Model;

import java.sql.Date;

public class Bolcher extends Candy {

    public Bolcher(int id, String type, int price, int stock, int minStock, int maxStock, Date date, String name) {
        super(id, type, price, minStock, maxStock, date, stock, name);
    }

    public Bolcher(Candy candy, Recipes recipe) {
        super(
            recipe.getCandyID(),           // candyID
            candy.getType(),               // type
            candy.getPrice(),              // price
            candy.getMinStock(),           // minStock
            candy.getMaxStock(),           // maxStock
            new Date(System.currentTimeMillis()), // date
            candy.getStock(),              // stock
            recipe.getName()               // name
        );
    }
}
