package model;

import java.sql.Date;

public class LogoBolcher extends Candy {

    private String firma;

    public LogoBolcher(int id, String type, int price, int stock, int minStock, int maxStock,  String name) {
        super(id, type, price, minStock, maxStock, stock, name);
    }

    public LogoBolcher(Candy candy) {
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

    public void setFirma(String firma) { this.firma = firma; }
    public String getFirma() { return firma; }
}
