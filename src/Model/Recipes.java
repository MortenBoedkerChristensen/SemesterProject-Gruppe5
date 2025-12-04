package Model;

import java.util.HashMap;

public class Recipes {
	private int candyID;
	private String name;
	private int difficulty;
	private HashMap<String, Integer> ingridients;
	
	
	public Recipes(int candyID, String name, int difficulty) {
		this.candyID = candyID;
		this.name = name;
		this.difficulty = difficulty;
		ingridients = new HashMap<>();
	}


	public int getCandyID() {
		return candyID;
	}


	public void setCandyID(int candyID) {
		this.candyID = candyID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}


	public HashMap<String, Integer> getIngridients() {
		return ingridients;
	}


	public void setIngridients(HashMap<String, Integer> ingridients) {
		this.ingridients = ingridients;
	}
	
	public void addIngridient(String ingridient, int qty) {
		ingridients.put(ingridient, qty);
	}
	
	public void removeIngridient(String ingridient) {
		ingridients.remove(ingridient);
	}
	
	public int getIngridientQty(String ingridient) {
		return ingridients.get(ingridient);
	}
	

}
