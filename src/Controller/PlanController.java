package Controller;

import Database.CandyDB;
import Database.EmployeeDB;
import Database.PlanDB;
import Database.RecipeDB;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Connection.DataAccessException;
import Model.Candy;
import Model.Employee;
import Model.Plan;
import Model.Recipes;

public class PlanController {
	
    private CandyDB candyDB = new CandyDB();
    private EmployeeDB employeeDB = new EmployeeDB();
    private PlanDB planDB = new PlanDB(); // or whatever your PlanDB is called
    private RecipeDB recipeDB = new RecipeDB();

    public PlanController() throws DataAccessException {
        planDB = new PlanDB();
    }
    
    // Meget juicy funktion
    public void createPlannedProduction(int maxCandies) throws DataAccessException {
        // Step 1: Får fat i lowstock fra candyDB
        List<Candy> lowStock = candyDB.getLowStockCandy();
        if (lowStock.isEmpty()) {
            System.out.println("No candies need production.");
            return;
        }

        List<Candy> candiesToPlan;

        if (lowStock.size() > maxCandies) {
            candiesToPlan = lowStock.subList(0, maxCandies);
        } else {
            candiesToPlan = lowStock;
        }

        // Step 2: Får fat i alle employes
        List<Employee> allEmployees = employeeDB.getAllEmployees();

        // Step 3: Laver en arrayliste med planerne
        List<Plan> plansToInsert = new ArrayList<>();

         // det skal måske flyttes til planDB men får bare fat i recipe her
        for (Candy candy : candiesToPlan) {
            Recipes recipe = recipeDB.getRecipeByCandyId(candy.getCandyID());
            if (recipe == null) {
                System.out.println("No recipe found for candy: " + candy.getName());
                continue;
            }

            // Checker om employee kan lave det
            List<Employee> eligible = new ArrayList<>();
            for (Employee e : allEmployees) {
                if (e.getNiveau() >= recipe.getDifficulty()) {
                    eligible.add(e);
                }
            }

            if (eligible.isEmpty()) {
                System.out.println("No eligible employees for candy: " + candy.getName());
                continue;
            }

            // tilføjer employee
            Employee assigned = eligible.get(0);

            // laver en plan
            Plan plan = new Plan();
            plan.setCandyID(candy.getCandyID());
            plan.setLocationID(assigned.getEmployeeId()); // or whatever your assignment logic is
            plan.setDate(new Date(System.currentTimeMillis()));
            plan.setRecipe(recipe); // store recipe in plan if needed

            plansToInsert.add(plan);

            // printer produktionen ( der skal nok laves en funktion til det her senere ))
            System.out.println("Candy: " + candy.getName() +
                               ", Employee: " + assigned.getName() +
                               ", Recipe: " + recipe.getName() +
                               ", Difficulty: " + recipe.getDifficulty());
            System.out.println("Ingredients:");
            recipe.printIngredients();
            System.out.println("---------------------------");
        }

        // Step 4: Insert all plans into DB
        if (!plansToInsert.isEmpty()) {
            planDB.insertPlans(plansToInsert);
        }
    }
}
