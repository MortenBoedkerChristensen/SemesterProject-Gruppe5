package Test;

import Database.RecipeDB;
import Model.Recipes;
import Connection.DataAccessException;

public class TryMe {
    public static void main(String[] args) {
        try {
            // Create RecipeDB instance
            RecipeDB recipeDB = new RecipeDB();

            // Load a recipe by CandyID (e.g., 1)
            Recipes recipe = recipeDB.getRecipeByCandyId(1);

            if (recipe != null) {
                System.out.println("Recipe loaded successfully!");
                System.out.println("CandyID: " + recipe.getCandyID());
                System.out.println("Name: " + recipe.getName());
                System.out.println("Difficulty: " + recipe.getDifficulty());
                System.out.println("Ingredients:");

                recipe.getIngridients().forEach((ingredient, qty) -> {
                    System.out.println(" - " + ingredient + ": " + qty);
                });
            } else {
                System.out.println("Recipe not found for CandyID 1.");
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
