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
    
    public void createPlannedProduction(int maxCandies) throws DataAccessException {

        // Step 1: Get low-stock candies
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

        // Step 2: Get all employees
        List<Employee> allEmployees = employeeDB.getAllEmployees();

        // Step 3: Create plans
        List<Plan> plansToInsert = new ArrayList<>();

        for (Candy candy : candiesToPlan) {

            Recipes recipe = recipeDB.getRecipeByCandyId(candy.getCandyID());
            if (recipe == null) {
                System.out.println("No recipe found for candy: " + candy.getName());
                continue;
            }

            // Find eligible employees
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

            Employee assigned = eligible.get(0);

            // Create plan
            Plan plan = new Plan();
            plan.setCandyID(candy.getCandyID());
            plan.setLocationID(assigned.getEmployeeId());
            plan.setDate(new Date(System.currentTimeMillis()));
            plan.setRecipe(recipe);

            plansToInsert.add(plan);

            // Print info
            System.out.println("Candy: " + candy.getName() +
                               ", Employee: " + assigned.getName() +
                               ", Recipe: " + recipe.getName() +
                               ", Difficulty: " + recipe.getDifficulty());
            System.out.println("Ingredients:");
            recipe.printIngredients();
            System.out.println("---------------------------");
        }

        // Step 4: Insert plans
        if (!plansToInsert.isEmpty()) {
            insertPlans(plansToInsert);
        }
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
}
