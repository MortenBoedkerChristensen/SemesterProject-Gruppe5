	package Database;
	
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;
	
	import Connection.DBConnection;
	import Connection.DataAccessException;
	import Model.Employee;
	
	public class EmployeeDB implements EmployeeDAO {
	
	    private DBConnection dbConn;
	
	    public EmployeeDB() throws DataAccessException {
	        dbConn = DBConnection.getInstance();
	    }
	    @Override
	    public Employee findById(int id) throws DataAccessException {
	        String sql = "SELECT employeeId, name, niveau FROM Employee WHERE employeeId = ?";
	        Employee employee = null;
	
	        try {
	            PreparedStatement stmt = dbConn.getConnection().prepareStatement(sql);
	            stmt.setInt(1, id);
	
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                int empId = rs.getInt("employeeId");
	                String name = rs.getString("name");
	                int niveau = rs.getInt("niveau");
	
	                employee = new Employee(empId, name, niveau);
	            }
	
	        } catch (SQLException e) {
	            throw new DataAccessException("Failed to retrieve employee with ID = " + id, e);
	        }
	
	        return employee;
	    }
	    
	}
