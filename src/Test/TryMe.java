package Test;

import Database.CandyDB;
import Database.RecipeDB;  // hvis du vil teste recipes også
import Model.Candy;
import Model.Recipes;
import Connection.DataAccessException;

import java.util.List;

public class TryMe {

    public static void main(String[] args) {
        TryMe tester = new TryMe();
        tester.runSimpleTests();
        // tester.loadAndPrintRecipe(1); // kan kaldes hvis du vil teste recipes
    }

    public void runSimpleTests() {
        CandyDB db = new CandyDB();

        try {
            System.out.println("=== SIMPLE TRYME START ===");

            // FIND BY ID
            System.out.println("\n--- FIND BY ID (ID=1) ---");
            Candy found = db.findById(1);
            if (found != null) {
                System.out.println(found);
            } else {
                System.out.println("Candy with ID=1 not found.");
            }

            // GET LOW STOCK
            System.out.println("\n--- GET LOW STOCK ---");
            List<Candy> lowStock = db.getLowStockCandy();
            if (lowStock.isEmpty()) {
                System.out.println("No candies with low stock.");
            } else {
                for (Candy c : lowStock) {
                    System.out.println(c);
                }
            }

            System.out.println("\n=== SIMPLE TRYME END ===");

        } catch (DataAccessException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper-metode til RecipeDB
    public void loadAndPrintRecipe(int candyID) {
        try {
            RecipeDB recipeDB = new RecipeDB();
            Recipes recipe = recipeDB.getRecipeByCandyId(candyID);

            if (recipe != null) {
                System.out.println("CandyID: " + recipe.getCandyID());
                System.out.println("Name: " + recipe.getName());
                System.out.println("Difficulty: " + recipe.getDifficulty());
                System.out.println("Ingredients:");
                recipe.printIngredients();
            } else {
                System.out.println("No recipe found for CandyID " + candyID);
            }

        } catch (DataAccessException e) {
            System.out.println("Error loading recipe: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
