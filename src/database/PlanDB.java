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
        // Only insert date; CandyID/LocationID are in PlanItem table
        String sql = "INSERT INTO [Plan] (date) VALUES (?)";

        try (PreparedStatement stmt = dbConn.getConnection()
                .prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

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

            PlanItem item = new PlanItem(recipe, 1); // default qty = 1
            plan.addItem(item);

            count++;
            if (count >= maxItems) break;
        }

        if (!plan.getItems().isEmpty()) savePlanWithItems(plan);

        return plan;
    }

    public void savePlanWithItems(Plan plan) throws DataAccessException {
        Connection conn = null;
        try {
            conn = dbConn.getConnection();
            dbConn.startTransaction();

            create(plan); // insert Plan
            insertPlanItems(plan.getPlanID(), plan.getItems());

            dbConn.commitTransaction();
        } catch (SQLException e) {
            if (conn != null) dbConn.rollbackTransaction();
            throw new DataAccessException("Failed saving plan with items", e);
        } finally {
            if (conn != null) dbConn.commitTransaction();
        }
    }

    private void insertPlanItems(int planID, List<PlanItem> items) throws SQLException {
        String sql = "INSERT INTO PlanItem (planID, recipeID, qty, status) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = dbConn.getConnection().prepareStatement(sql)) {
            for (PlanItem item : items) {
                stmt.setInt(1, planID);
                stmt.setInt(2, item.getRecipe().getRecipeID());
                stmt.setInt(3, item.getQty());
                stmt.setString(4, item.getStatus().name());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    @Override
    public Plan findById(int id) throws DataAccessException {
        String sql = "SELECT planID, date FROM Plan WHERE planID = ?";
        try (PreparedStatement stmt = dbConn.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) return null;

            Plan plan = new Plan();
            plan.setPlanID(rs.getInt("planID"));
            plan.setDate(rs.getDate("date"));
            plan.setItems(loadPlanItems(id));
            return plan;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve plan", e);
        }
    }

    private List<PlanItem> loadPlanItems(int planID) throws SQLException, DataAccessException {
        String sql = "SELECT recipeID, qty, status FROM PlanItem WHERE planID = ?";
        List<PlanItem> items = new ArrayList<>();

        try (PreparedStatement stmt = dbConn.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, planID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Recipe recipe = recipeDB.findById(rs.getInt("recipeID"));
                PlanItem item = new PlanItem(recipe, rs.getInt("qty"));
                item.setStatus(PlanItem.Status.valueOf(rs.getString("status")));
                items.add(item);
            }
        }

        return items;
    }
}
