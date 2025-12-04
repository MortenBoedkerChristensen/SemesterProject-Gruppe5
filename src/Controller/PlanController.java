package Controller;

import Model.Plan;
import Model.Recipes;

public class PlanController {
	Plan tempPlan;
	
	public PlanController() {
		
	}
	
	public void newPlan() {
		tempPlan = new Plan();
	}
	
	public void addRecipeToPlan(Recipes recipe) {
		tempPlan.addRecipe(recipe);
	}
	
	
	
	
	
	

}