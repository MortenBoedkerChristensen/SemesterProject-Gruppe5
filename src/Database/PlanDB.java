package Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.DBConnection;
import Connection.DataAccessException;
import Model.Candy;
import Model.Employee;
import Model.Plan;
import Model.Recipes;

public class PlanDB implements PlanDAO {

    private DBConnection dbConn;
    private  CandyDB candyDB = new CandyDB();
    private  EmployeeDB employeeDB = new EmployeeDB();
    private  RecipeDB recipeDB = new RecipeDB();

    public PlanDB() throws DataAccessException {
        dbConn = DBConnection.getInstance();
    }

    // Laver en plan
    @Override
    public Plan create(Plan plan) throws DataAccessException {
        String sql = "INSERT INTO [Plan] (date, status, locationID, candyID) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = dbConn.getConnection()
                .prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, plan.getDate());
            stmt.setString(2, plan.getStatus().name());
            stmt.setInt(3, plan.getLocationID());
            stmt.setInt(4, plan.getCandyID());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                plan.setPlanID(rs.getInt(1));
            }

            return plan;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to create plan", e);
        }
    }

    // Laver flere planer med list
    public void insertPlans(List<Plan> plans) throws DataAccessException {
        String sql = "INSERT INTO [Plan] (date, status, locationID, candyID) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = dbConn.getConnection().prepareStatement(sql)) {
            for (Plan plan : plans) {
                stmt.setDate(1, plan.getDate());
                stmt.setString(2, plan.getStatus() != null ? plan.getStatus().name() : Plan.Status.STARTED.name());
                stmt.setInt(3, plan.getLocationID());
                stmt.setInt(4, plan.getCandyID());
                stmt.addBatch();
            }
            stmt.executeBatch();

        } catch (SQLException e) {
            throw new DataAccessException("Failed to insert plans", e);
        }
    }

    // Finder på ID
    @Override
    public Plan findById(int id) throws DataAccessException {
        Plan plan = null;
        String sql = "SELECT planID, date, status, locationID, candyID FROM [Plan] WHERE planID = ?";

        try (PreparedStatement stmt = dbConn.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                plan = buildPlan(rs);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve plan with ID = " + id, e);
        }

        return plan;
    }
    
    public String createPlannedProduction(int maxCandies) throws DataAccessException {

        StringBuilder output = new StringBuilder();

        // Step 1: Get low-stock candies
        List<Candy> lowStock = candyDB.getLowStockCandy();
        if (lowStock.size() == 0) {
            output.append("No candies need production.\n");
            return output.toString();
        }

        List<Candy> candiesToPlan;
        if (lowStock.size() > maxCandies) {
            candiesToPlan = lowStock.subList(0, maxCandies);
        } else {
            candiesToPlan = lowStock;
        }

        // Step 2: Get all employees
        List<Employee> allEmployees = employeeDB.getAllEmployees();

        // Step 3: Create plans
        List<Plan> plansToInsert = new ArrayList<Plan>();

        for (int i = 0; i < candiesToPlan.size(); i++) {
            Candy candy = candiesToPlan.get(i);

            Recipes recipe = recipeDB.getRecipeByCandyId(candy.getCandyID());
            if (recipe == null) {
                output.append("No recipe found for candy: ").append(candy.getName()).append("\n");
                continue;
            }

            // Find eligible employees
            Employee assigned = null;
            for (int j = 0; j < allEmployees.size(); j++) {
                Employee e = allEmployees.get(j);
                if (e.getNiveau() >= recipe.getDifficulty()) {
                    assigned = e;
                    break;
                }
            }

            if (assigned == null) {
                output.append("No eligible employees for candy: ").append(candy.getName()).append("\n");
                continue;
            }

            // Create plan
            Plan plan = new Plan();
            plan.setCandyID(candy.getCandyID());
            plan.setLocationID(assigned.getEmployeeId());
            plan.setDate(new java.sql.Date(System.currentTimeMillis()));
            plan.setRecipe(recipe);

            plansToInsert.add(plan);

            // Append info using recipe.toString()
            output.append("Candy: ").append(candy.getName())
                  .append(", Employee: ").append(assigned.getName()).append("\n");
            output.append(recipe.toString());
            output.append("---------------------------\n");
        }

        // Step 4: Insert plans
        if (plansToInsert.size() > 0) {
            insertPlans(plansToInsert);
        }

        return output.toString();
    }



    // Nem måde at bygge en plan med ResultSet
    public Plan buildPlan(ResultSet rs) throws SQLException {
        Plan plan = new Plan();
        plan.setPlanID(rs.getInt("planID"));
        plan.setDate(rs.getDate("date"));

        String statusString = rs.getString("status");
        plan.setStatus(statusString != null ? Plan.Status.valueOf(statusString) : Plan.Status.STARTED);

        plan.setLocationID(rs.getInt("locationID"));
        plan.setCandyID(rs.getInt("candyID"));

        return plan;
    }
    
    
    public String createPlannedProductionForEmployee(String employeeName, int maxCandies) throws DataAccessException {

        StringBuilder output = new StringBuilder();

        // Step 1: Find employee by name
        Employee employee = employeeDB.FindByName(employeeName);
        if (employee == null) {
            return "Employee not found: " + employeeName;
        }

        int employeeLevel = employee.getNiveau();

        // Step 2: Get low-stock candies
        List<Candy> lowStock = candyDB.getLowStockCandy();
        if (lowStock.isEmpty()) {
            return "No candies need production.\n";
        }

        // Step 3: Filter candies by employee skill
        List<Candy> eligibleCandies = new ArrayList<>();

        for (Candy candy : lowStock) {
            Recipes recipe = recipeDB.getRecipeByCandyId(candy.getCandyID());
            if (recipe == null) {
                output.append("No recipe found for candy: ").append(candy.getName()).append("\n");
                continue;
            }

            if (employeeLevel >= recipe.getDifficulty()) {
                eligibleCandies.add(candy);
            }
        }

        if (eligibleCandies.isEmpty()) {
            return employeeName + " is not qualified to make any low-stock candies.\n";
        }

        // Limit amount
        if (eligibleCandies.size() > maxCandies) {
            eligibleCandies = eligibleCandies.subList(0, maxCandies);
        }

        // Step 4: Create plans
        List<Plan> plansToInsert = new ArrayList<>();

        for (Candy candy : eligibleCandies) {

            Recipes recipe = recipeDB.getRecipeByCandyId(candy.getCandyID());

            // Create plan
            Plan plan = new Plan();
            plan.setCandyID(candy.getCandyID());
            plan.setLocationID(employee.getEmployeeId());
            plan.setDate(new java.sql.Date(System.currentTimeMillis()));
            plan.setRecipe(recipe);

            plansToInsert.add(plan);

            output.append("Assigned to ").append(employeeName)
                  .append(": ").append(candy.getName()).append("\n")
                  .append(recipe.toString())
                  .append("---------------------------\n");
        }

        // Insert into DB
        insertPlans(plansToInsert);

        return output.toString();
    }

    
    
    
}
