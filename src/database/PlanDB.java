package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import connection.DataAccessException;
import model.Candy;
import model.Employee;
import model.Plan;
import model.Recipe;

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

        Connection conn = null;
        
        try {
            conn = dbConn.getConnection();
            dbConn.startTransaction(); // Starter transaction

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (Plan plan : plans) {
                    stmt.setDate(1, plan.getDate());
                    stmt.setString(2, plan.getStatus() != null ? plan.getStatus().name() : Plan.Status.STARTED.name());
                    stmt.setInt(3, plan.getLocationID());
                    stmt.setInt(4, plan.getCandyID());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            dbConn.commitTransaction(); // Commit transaction

        } catch (SQLException e) {
            if (conn != null) {
                dbConn.rollbackTransaction(); // Rollback transaction
            }
            throw new DataAccessException("Failed to insert plans", e);
        } finally {
			// Ensure auto-commit is re-enabled
			if (conn != null) {
				dbConn.commitTransaction();
			}   
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
            Recipe recipe = recipeDB.getRecipeByCandyId(candy.getCandyID());
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
            Recipe recipe = recipeDB.getRecipeByCandyId(candy.getCandyID()); // already called before, could reuse
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
