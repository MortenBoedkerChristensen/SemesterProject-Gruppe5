package model;

import java.sql.Date;

public class Bolcher extends Candy {

    public Bolcher(int id, String type, int price, int stock, int minStock, int maxStock,  String name) {
        super(id, type, price, minStock, maxStock, stock, name);
    }
/*
 * #TODO
 * Remove recipe in subclass constructors
 */
    public Bolcher(Candy candy) {
        super(
            candy.getCandyID(),           // candyID
            candy.getType(),               // type
            candy.getPrice(),              // price
            candy.getMinStock(),           // minStock
            candy.getMaxStock(),           // maxStock
          
            candy.getStock(),              // stock
            candy.getName()               // name
        );
    }
}
