package Model;

public class LogoBolcherCopy extends LogoBolcher {
	
	private int copyNo;
	public LogoBolcherCopy(int id, String type, int price, int stock, int minStock, int maxStock, java.sql.Date date) {
		super(id, type, price, stock, minStock, maxStock, date);
		
	}
	
	public LogoBolcherCopy(Candy candy, Recipes recipe) {
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
	
	public void setCopyNo(int copyNo) {
		this.copyNo = copyNo;
	}
	
	public int getCopyNo() {
		return copyNo;
	}

}
