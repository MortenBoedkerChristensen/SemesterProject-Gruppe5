package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import Connection.DBConnection;
import Connection.DataAccessException;
import Model.Plan;

public class PlanDB implements PlanDAO {

	private Connection conn;
	
	public PlanDB() {
		try {
	        conn = DBConnection.getInstance().getConnection();
	    	} catch (DataAccessException e) {
	    		System.out.println("Could not connect");
	    	}
	}
	
	@Override
	public Plan insert(Plan plan) throws DataAccessException {
		String sql = "INSERT INTO Plan (planId, projectId, description, startDate, endDate) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, plan.getPlanID());
		//	stmt.setInt(2, plan.getProjectId());
			stmt.setString(3, plan.getDescription());
			stmt.setDate(4, plan.getStartDate());
			stmt.setDate(5, plan.getEndDate());
			stmt.executeUpdate();
			return plan;
		} catch (SQLException e) {
			throw new DataAccessException("Failed to insert plan", e);
		}

	}
	//UPDATE
	@Override
	public Plan update(Plan plan) throws DataAccessException {
		String sql = "UPDATE Plan SET projectId = ?, description = ?, startDate = ?, endDate = ? WHERE planId = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
		//	stmt.setInt(1, plan.getProjectId());
			stmt.setString(2, plan.getDescription());
			stmt.setDate(3, plan.getStartDate());
			stmt.setDate(4, plan.getEndDate());
			stmt.setInt(5, plan.getPlanID());
			stmt.executeUpdate();
			return plan;
		} catch (SQLException e) {
			throw new DataAccessException("Failed to update plan", e);
		}
	}
	
	// DELETE
    @Override
    public void delete(int id) throws DataAccessException {

        String sql = "DELETE FROM Plan WHERE planId = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException("Failed to delete plan", e);
		}
    }
	
	//GET ALL PLANS
	
	@Override
	public List<Plan> getPlans() throws DataAccessException {
		List<Plan> plans = new java.util.ArrayList<>();
		String sql = "SELECT planId, projectId, description, startDate, endDate FROM Plan";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			java.sql.ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int planId = rs.getInt("planId");
			//	int projectId = rs.getInt("projectId");
				String description = rs.getString("description");
				java.sql.Date startDate = rs.getDate("startDate");
				java.sql.Date endDate = rs.getDate("endDate");
				Plan plan = new Plan(planId, /*projectId,*/ description, startDate, endDate);
				plans.add(plan);
			}
			return plans;
		} catch (SQLException e) {
			throw new DataAccessException("Failed to retrieve plans", e);
		}
	}

	


	@Override
	public Plan findById(int id) throws DataAccessException {
		
		String sql = "SELECT planId, projectId, description, startDate, endDate FROM Plan WHERE planId = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			java.sql.ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int planId = rs.getInt("planId");
			//	int projectId = rs.getInt("projectId");
				String description = rs.getString("description");
				java.sql.Date startDate = rs.getDate("startDate");
				java.sql.Date endDate = rs.getDate("endDate");
				Plan plan = new Plan(planId, /*projectId,*/ description, startDate, endDate);
				return plan;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to find plan by ID", e);
		}
	}
}
