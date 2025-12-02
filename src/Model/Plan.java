package Model;

import java.sql.Date;
import java.util.List;

public class Plan {
	
	private int planID;
	private Date date;
	private int status;
	private List<Recipes> recipe;
	
	public Plan(List<Recipes> recipe) {
		this.recipe = recipe;

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
	
	public int setStatus(int status) {
		return this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setRecipe(List<Recipes> recipe) {
		this.recipe = recipe;
	}
	
	public List<Recipes> getRecipe() {
		return recipe;
	}
	
}
	
