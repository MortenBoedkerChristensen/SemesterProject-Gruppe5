package Controller;

import com.sun.tools.javac.util.List;

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
	
	public void savePlan() {
		//planDB save plan
	}
	
	public List<Recipes> getLowStockRecipes() {
		List<Recipes> recipes = new List<>();
		return recipes;
	}
	
	
	
	
	

}