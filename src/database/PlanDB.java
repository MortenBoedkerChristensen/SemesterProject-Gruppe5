package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import connection.DataAccessException;
import model.Candy;
import model.Employee;
import model.Plan;
import model.PlanItem;
import model.Recipe;

public class PlanDB implements PlanDAO {

    private DBConnection dbConn;
    private RecipeDB recipeDB = new RecipeDB();
    private CandyDB candyDB = new CandyDB();
    private EmployeeDB employeeDB = new EmployeeDB();

    public PlanDB() throws DataAccessException {
        dbConn = DBConnection.getInstance();
    }

    @Override
    public Plan create(Plan plan) throws DataAccessException {
        String sql = "INSERT INTO [Plan] ([Date]) VALUES (?)"; // escaped table and column
        try (PreparedStatement stmt = dbConn.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, plan.getDate());
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

    public Plan createPlannedProductionForEmployee(String employeeName, int maxItems) throws DataAccessException {
        Employee employee = employeeDB.FindByName(employeeName);
        if (employee == null) return null;

        int employeeLevel = employee.getNiveau();
        List<Candy> lowStock = candyDB.getLowStockCandy();
        if (lowStock.isEmpty()) return null;

        Plan plan = new Plan();
        plan.setDate(new java.sql.Date(System.currentTimeMillis()));

        int count = 0;
        for (Candy candy : lowStock) {
            Recipe recipe = recipeDB.getRecipeByCandyId(candy.getCandyID());
            if (recipe == null || employeeLevel < recipe.getDifficulty()) continue;

            PlanItem item = new PlanItem(recipe, candy, 1); // default qty = 1
            item.setStatus(PlanItem.Status.STARTED); // valid enum
            item.setEmployee(employee);
            plan.addItem(item);

            count++;
            if (count >= maxItems) break;
        }

        if (!plan.getItems().isEmpty()) savePlanWithItems(plan);

        return plan;
    }

    public void savePlanWithItems(Plan plan) throws DataAccessException {
        try {
            dbConn.startTransaction();

            create(plan); // insert Plan
            insertPlanItems(plan.getPlanID(), plan.getItems());

            dbConn.commitTransaction();
        } catch (SQLException e) {
            dbConn.rollbackTransaction();
            throw new DataAccessException("Failed saving plan with items", e);
        }
    }

    private void insertPlanItems(int planID, List<PlanItem> items) throws SQLException, DataAccessException {
        String sql = "INSERT INTO [PlanItem] (PlanID, RecipeID, CandyID, Qty, Status, EmployeeID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConn.getConnection().prepareStatement(sql)) {
            for (PlanItem item : items) {
                stmt.setInt(1, planID);
                stmt.setInt(2, item.getRecipe().getRecipeID());
                stmt.setInt(3, item.getCandy().getCandyID());
                stmt.setInt(4, item.getQty());
                stmt.setString(5, item.getStatus().name());
                stmt.setInt(6, item.getEmployee().getEmployeeId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    @Override
    public Plan findById(int id) throws DataAccessException {
        String sql = "SELECT PlanID, [Date] FROM [Plan] WHERE PlanID = ?";
        try (PreparedStatement stmt = dbConn.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) return null;

            Plan plan = new Plan();
            plan.setPlanID(rs.getInt("PlanID"));
            plan.setDate(rs.getDate("Date"));
            plan.setItems(loadPlanItems(id));
            return plan;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve plan", e);
        }
    }

    private List<PlanItem> loadPlanItems(int planID) throws SQLException, DataAccessException {
        String sql = "SELECT RecipeID, CandyID, Qty, Status, EmployeeID FROM [PlanItem] WHERE PlanID = ?";
        List<PlanItem> items = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, planID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // load Recipe, Candy, Employee as before
                    Recipe recipe = recipeDB.findById(rs.getInt("RecipeID"));
                    Candy candy = candyDB.findById(rs.getInt("CandyID"));
                    PlanItem item = new PlanItem(recipe, candy, rs.getInt("Qty"));
                    item.setStatus(PlanItem.Status.valueOf(rs.getString("Status")));
                    Employee employee = employeeDB.findById(rs.getInt("EmployeeID"));
                    item.setEmployee(employee);
                    items.add(item);
                }
            }
        }

        return items;
    }

    public List<Plan> getAllPlans() throws DataAccessException {
        List<Plan> plans = new ArrayList<>();
        String sql = "SELECT PlanID, [Date] FROM [Plan] ORDER BY PlanID DESC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Plan plan = new Plan();
                plan.setPlanID(rs.getInt("PlanID"));
                plan.setDate(rs.getDate("Date"));
                plan.setItems(loadPlanItems(plan.getPlanID())); // still safe
                plans.add(plan);
            }
        } catch (Exception e) {
            throw new DataAccessException("Failed to load all plans", e);
        }
        return plans;
    }



}
