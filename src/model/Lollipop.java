package model;

import java.sql.Date;

public class Lollipop extends Candy {

    public Lollipop(int id, String type, int price, int stock, int minStock, int maxStock,  String name) {
        super(id, type, price, minStock, maxStock,  stock, name);
    }

    public Lollipop(Candy candy) {
        super(
        	candy.getCandyID(),
            candy.getType(),
            candy.getPrice(),
            candy.getMinStock(),
            candy.getMaxStock(),
            
            candy.getStock(),
            candy.getName()
        );
    }
}
