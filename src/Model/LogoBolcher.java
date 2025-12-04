package Model;

public class LogoBolcher extends Candy {
	
	
	private String firma;
	public LogoBolcher(int id, String type, int price, int stock, int minStock, int maxStock, java.sql.Date date) {
		super(id, type, price, stock, minStock, maxStock, date);
		
	}
	
	public LogoBolcher(Candy candy, Recipes recipe) {
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
	
	public void setFirma(String firma) {
		this.firma = firma;
	}
	
	public String getFirma() {
		return firma;
	}
	

}
