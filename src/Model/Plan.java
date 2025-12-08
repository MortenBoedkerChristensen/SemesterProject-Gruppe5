package Model;

import java.sql.Date;
import java.util.List;

public class Plan {
	
	private int planID;
	private Date date;
	private Status status;
	private enum Status { //Flere status + skift i mellem
		STARTED,
		COOKING,
		READY,
		
	}
	private List<Recipes> recipe;
	
	public Plan(List<Recipes> recipe) {
		this.recipe = recipe;

	}
	
	public Plan() {
		
	}
	public int setPlanID(int planID) {
		return this.planID = planID;
	}
	
	public int getPlanID() {
		return planID;
	}
	
	public Date setDate(Date date) {
		return this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setStatus(Status status) {
		this.status = Status.STARTED;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setRecipe(List<Recipes> recipe) {
		this.recipe = recipe;
	}
	
	
	public void addRecipe(Recipes recipe) {
		this.recipe.add(recipe);
	}
	
	public List<Recipes> getRecipe() {
		return recipe;
	}
	
}
	
