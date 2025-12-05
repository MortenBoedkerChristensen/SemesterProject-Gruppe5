package Model;

public class GourmetBolcher extends Candy {
	public GourmetBolcher(int id, String type, int price, int stock, int minStock, int maxStock, java.sql.Date date) {
		super(id, type, price, stock, minStock, date, maxStock);
		
	}
	public GourmetBolcher(Candy candy, Recipes recipe) {
	    super(
	        recipe.getCandyID(),      // id
	        recipe.getName(),           // type
	        candy.getPrice(),           // price
	        candy.getStock(),           // stock — brug evt. candy's stock
	        candy.getMinStock(),        // minStock
	        new java.sql.Date(System.currentTimeMillis()),        // maxStock
	        candy.getMaxStock()
	    );
	}
}