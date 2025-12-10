package model;

public class PlanItem {
	private Recipe recipe;
	private int qty;
	private Status status;
	
	public PlanItem(Recipe recipe, int qty) {
		this.recipe = recipe;
		this.qty = qty;
		
		
	}
	public enum Status {
        STARTED,
        COOKING,
        READY
    }
	
	
	public Recipe getRecipe() {
		return recipe;
	}
	
	public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
	
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	public int getQty() {
		return qty;
	}
	
	public void setQty(int qty) {
		this.qty = qty;
	}

}
