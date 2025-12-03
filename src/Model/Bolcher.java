package Model;

public class Bolcher extends Candy {
	public Bolcher(int id, String type, int price, int stock, int minStock, int maxStock, java.sql.Date date) {
		super(id, type, price, stock, minStock, maxStock, date);
		
	}
	
	public Bolcher(Recipes recipe) {
		super(
				recipe.getProductID(),  // id
				recipe.getName(),       // type / navn
				recipe.getDifficulty(), // pris? hvis vi vil have difficulty med skal vi lave om på constructoren i Candy
				0,                      // stock – vælg startlager
				0,                      // minStock – definer evt.
				100,                    // maxStock – eller anden værdi
				new java.sql.Date(System.currentTimeMillis()) // dato
				);
	}
}
