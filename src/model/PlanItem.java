package model;

public class PlanItem {
	private Recipes recipe;
	private int qty;
	
	public PlanItem(Recipes recipe, int qty) {
		this.recipe = recipe;
		this.qty = qty;
	}
	
	
	public Recipes getRecipe() {
		return recipe;
	}
	
	public void setRecipe(Recipes recipe) {
		this.recipe = recipe;
	}
	
	public int getQty() {
		return qty;
	}
	
	public void setQty(int qty) {
		this.qty = qty;
	}

}
