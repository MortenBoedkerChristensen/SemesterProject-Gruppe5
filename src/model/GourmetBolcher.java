package model;

import java.sql.Date;

public class GourmetBolcher extends Candy {

    public GourmetBolcher(int id, String type, int price, int stock, int minStock, int maxStock,  String name) {
        super(id, type, price, minStock, maxStock,  stock, name);
    }

    public GourmetBolcher(Candy candy) {
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
