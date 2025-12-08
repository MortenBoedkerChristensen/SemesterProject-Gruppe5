package Controller;

import java.util.List;

import Connection.DataAccessException;
import Database.CandyDB;
import Model.Bolcher;
import Model.Candy;
import Model.GourmetBolcher;
import Model.LogoBolcher;
import Model.Lollipop;

public class CandyController {
    private CandyDB candydb;
    
    public CandyController() throws DataAccessException {
        candydb = new CandyDB();
    }
    
    public Candy addCandy(
            String type, 
            int price, 
            int stock, 
            int minStock, 
            int maxStock
    ) throws DataAccessException {

        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        Candy candy;

        switch (type.toLowerCase()) {

            case "bolcher":
                candy = new Bolcher(0, type, price, stock, minStock, maxStock, date);
                break;

            case "logobolcher":
                candy = new LogoBolcher(0, type, price, stock, minStock, maxStock, date);
                break;

            case "gourmetbolcher":
                candy = new GourmetBolcher(0, type, price, stock, minStock, maxStock, date);
                break;

            case "lollipop":
                candy = new Lollipop(0, type, price, stock, minStock, maxStock, date);
                break;

            default:
                // fallback
                candy = new Candy(0, type, price, minStock, maxStock, date, stock);
                break;
        }

        return candydb.insert(candy);
    }

    public List<Candy> getAllLowStockCandy() throws DataAccessException {
        List<Candy> lowStockList = candydb.getLowStockCandy();

      /*  // Henter alle candy fra databasen
        List<Candy> allCandy = candydb.getAllCandy();

        // Finder candy hvor stock < minStock
        for (Candy c : allCandy) {
            if (c.getStock() < c.getMinStock()) {
                lowStockList.add(c);
            }
        } */

        return lowStockList;
    }
}
