package model;

import java.sql.Date;

public class Lollipop extends Candy {

    public Lollipop(int id, String type, int price, int stock, int minStock, int maxStock, Date date, String name) {
        super(id, type, price, minStock, maxStock, date, stock, name);
    }

    public Lollipop(Candy candy, Recipe recipe) {
        super(
            recipe.getRecipeID(),
            candy.getType(),
            candy.getPrice(),
            candy.getMinStock(),
            candy.getMaxStock(),
            new Date(System.currentTimeMillis()),
            candy.getStock(),
            recipe.getName()
        );
    }
}
