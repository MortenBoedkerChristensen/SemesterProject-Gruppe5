package Model;

public class Bolcher extends Candy {
	public Bolcher(int id, String type, int price, int stock, int minStock, int maxStock, java.sql.Date date) {
		super(id, type, price, stock, minStock, maxStock, date);
		
	}
	public Bolcher(Candy candy, Recipes recipe) {
	    super(
	        recipe.getCandyID(),      // id
	        recipe.getName(),           // type
	        candy.getPrice(),           // price
	        candy.getStock(),           // stock — brug evt. candy's stock
	        candy.getMinStock(),        // minStock
	        candy.getMaxStock(),        // maxStock
	        new java.sql.Date(System.currentTimeMillis())
	    );
	}
}
