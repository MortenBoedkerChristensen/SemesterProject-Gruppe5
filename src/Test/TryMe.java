package Test;

import Database.RecipeDB;
import Model.Recipes;
import Connection.DataAccessException;

public class TryMe {

    public static void main(String[] args) {
    	 
        TryMe tester = new TryMe();

        
        tester.loadAndPrintRecipe(1); 
    }

    
    public void loadAndPrintRecipe(int candyID) {
        try {
            RecipeDB recipeDB = new RecipeDB();
            Recipes recipe = recipeDB.getRecipeByCandyId(candyID);

            if (recipe != null) {
                System.out.println("CandyID: " + recipe.getCandyID());
                System.out.println("Name: " + recipe.getName());
                System.out.println("Difficulty: " + recipe.getDifficulty());
                System.out.println("Ingredients:");
                recipe.printIngredients(); // kalder metoden i Recipes
            } else {
                System.out.println("No recipe found for CandyID " + candyID);
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
