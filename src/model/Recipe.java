package model;

import java.util.HashMap;

public class Recipe {
	private int recipeID;
	private Candy candy;
	//private String name;
	private int difficulty;
	private HashMap<String, Integer> ingridients;
	/*
	 * #TODO
	 * Refactor metoder til at bruge Candy objekt og recipeID
	 * Tilføj description
	 */
	
	public Recipe(int recipeID, String name, int difficulty) {
		this.recipeID = recipeID;
		//this.name = name;
		this.difficulty = difficulty;
		ingridients = new HashMap<>();
	}

	
	public int getRecipeID() {
		return recipeID;
	}


	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}
	
	public Candy getCandy() {
		return candy;
	}
	
	public void setCandy(Candy candy) {
		this.candy = candy;
	}

/*
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

*/
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
	
	/*
	 * #TODO
	 * Hardcoded ingridient stock
	 */
	public void addIngridient(String ingridient, int qty) {
		ingridients.put(ingridient, qty);
	}
	
	public void removeIngridient(String ingridient) {
		ingridients.remove(ingridient);
	}
	
	public int getIngridientQty(String ingridient) {
		return ingridients.get(ingridient);
	}
	
	public void printIngredients() {
	    for (String ingredient : ingridients.keySet()) {
	        int qty = ingridients.get(ingredient);
	        System.out.println(ingredient + " " + qty);
	    }
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CandyID: ").append(candy.getCandyID()).append("\n");
		//sb.append("Name: ").append(name).append("\n");
		sb.append("Difficulty: ").append(difficulty).append("\n");
		sb.append("Ingredients:\n");
		for (String ingredient : ingridients.keySet()) {
	        int qty = ingridients.get(ingredient);
	        sb.append(ingredient).append(" ").append(qty).append("\n");
	    }
	return sb.toString();
	}
}
	
