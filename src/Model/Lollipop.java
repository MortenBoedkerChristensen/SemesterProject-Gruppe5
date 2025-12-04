package Model;

public class Lollipop extends Candy {
	public Lollipop(int id, String type, int price, int stock, int minStock, int maxStock, java.sql.Date date) {
		super(id, type, price, stock, minStock, date, maxStock);
		
	}
	
	public Lollipop(Candy candy, Recipes recipe) {
	    super(
	        recipe.getProductID(),      // id
	        recipe.getName(),           // type
	        candy.getPrice(),           // ← DU KALDER DEN SÅDAN HER
	        candy.getStock(),           // stock — brug evt. candy's stock
	        candy.getMinStock(),        // minStock
	        new java.sql.Date(System.currentTimeMillis()),        // maxStock
	        candy.getMaxStock()
	    );
	}


}
