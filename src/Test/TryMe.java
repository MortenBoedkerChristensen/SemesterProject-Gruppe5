package Test;

import Database.CandyDB;
import Database.RecipeDB;
import Database.EmployeeDB;  // Tilføjet EmployeeDB
import Model.Candy;
import Model.Recipes;
import Model.Employee;       // Tilføjet Employee
import Connection.DataAccessException;

import java.util.List;

public class TryMe {

    public static void main(String[] args) {
        TryMe tester = new TryMe();
        tester.runSimpleTests();
        tester.runEmployeeTest(); // Ny test for EmployeeDB
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

    // Ny funktion til EmployeeDB test
    public void runEmployeeTest() {
        try {
            EmployeeDB db = new EmployeeDB();

            System.out.println("\n=== EMPLOYEEDB FIND BY ID TEST ===");

            int testId = 1; // Ændr til et eksisterende ID i din Employee-tabel
            Employee emp = db.findById(testId);

            if (emp != null) {
                System.out.println("Employee found:");
                System.out.println("ID: " + emp.getEmployeeId());
                System.out.println("Name: " + emp.getName());
                System.out.println("Niveau: " + emp.getNiveau());
            } else {
                System.out.println("No employee found with ID = " + testId);
            }

            System.out.println("=== EMPLOYEEDB TEST END ===");

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
