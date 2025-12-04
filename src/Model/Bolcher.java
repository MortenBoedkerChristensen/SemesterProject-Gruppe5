package Model;

public class Bolcher extends Candy {
	public Bolcher(int id, String type, int price, int stock, int minStock, int maxStock, java.sql.Date date) {
		super(id, type, price, stock, minStock, date, maxStock);
		
	}
	public Bolcher(Candy candy, Recipes recipe) {
	    super(
	        recipe.getProductID(),      // id
	        recipe.getName(),           // type
	        candy.getPrice(),           // price
	        candy.getStock(),           // stock — brug evt. candy's stock
	        candy.getMinStock(),        // minStock
	        new java.sql.Date(System.currentTimeMillis()),        // maxStock
	        candy.getMaxStock()
	    );
	}
}
