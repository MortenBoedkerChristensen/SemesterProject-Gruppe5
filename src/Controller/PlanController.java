package Controller;



import java.util.List;

import Connection.DataAccessException;
import Database.CandyDB;
import Database.RecipeDB;
import Model.Candy;
import Model.Plan;
import Model.Recipes;

public class PlanController {
	Plan tempPlan;
	
	public PlanController() {
		
	}
	
	public void newPlan() throws DataAccessException  {
		CandyDB cDB = new CandyDB();
		
		RecipeDB rDB = new RecipeDB();
		
		
		List<Candy> lowStockCandy = cDB.getLowStockCandy();
		List<Recipes> recipes = new java.util.ArrayList<>();
		for(Candy c : lowStockCandy) {
			recipes.add(rDB.getRecipeByCandyId(c.getCandyID()));
		}
		
		Plan p = assemblePlan(recipes);
		
		//confirm Plan
		
		savePlan(p);
			
			
			
		
		
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
	
	/* public void addRecipeToPlan(Recipes recipe) {
		tempPlan.addRecipe(recipe);
	} */
	
	public void savePlan(Plan p) {
		//planDB save plan
		//PlanDB.savePlan(p);
	}
	
	public Plan assemblePlan(List<Recipes> recipes) {
		Plan plan = new Plan();
		for(Recipes r : recipes) {
			plan.addRecipe(r);
		}
		
		return plan;
	}
	
	
	
	
	
	
	

}