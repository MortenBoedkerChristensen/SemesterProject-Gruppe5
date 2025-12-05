package Controller;



import java.util.List;

import Model.Plan;
import Model.Recipes;

public class PlanController {
	Plan tempPlan;
	
	public PlanController() {
		
	}
	
	public void newPlan() {
		tempPlan = new Plan();
		/*#TODO
		 * EVT. binde en eller flere Employees bundet op på en plan,
		 * Check om der er employee med nok skill level
		 * 
		 * List<Recipes> = getLowStockRecipes(); + Check for enough ingredients
		 * 
		 * 
		 * Plan p = assemblePlan(recipes);
		 * 
		 * confirm plan();
		 * 
		 * p.savePlan();
		 * 
		 */
	}
	
	public void addRecipeToPlan(Recipes recipe) {
		tempPlan.addRecipe(recipe);
	}
	
	public void savePlan() {
		//planDB save plan
	}
	
	public Plan assemblePlan(List<Recipes> recipes) {
		Plan plan = new Plan();
		for(Recipes r : recipes) {
			plan.addRecipe(r);
		}
		
		return plan;
	}
	
	
	
	
	
	
	

}