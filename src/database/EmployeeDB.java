	package database;
	
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;

import connection.DBConnection;
import connection.DataAccessException;
import model.Employee;
	
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
	    public List<Employee> getAllEmployees() throws DataAccessException {
	        List<Employee> employees = new ArrayList<>();
	        String sql = "SELECT employeeId, name, niveau FROM Employee";

	        try (PreparedStatement stmt = dbConn.getConnection().prepareStatement(sql)) {
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next()) {
	                int empId = rs.getInt("employeeId");
	                String name = rs.getString("name");
	                int niveau = rs.getInt("niveau");

	                employees.add(new Employee(empId, name, niveau));
	            }
	        } catch (SQLException e) {
	            throw new DataAccessException("Failed to retrieve employees", e);
	        }

	        return employees; // never returns null
	    }
	    
	    public Employee FindByName(String name) throws DataAccessException {
	        String sql = "SELECT employeeId, name, niveau FROM Employee WHERE name = ?";
	        Employee employee = null;
	
	        try {
	            PreparedStatement stmt = dbConn.getConnection().prepareStatement(sql);
	            stmt.setString(1, name);
	
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                int empId = rs.getInt("employeeId");
	                String empName = rs.getString("name");
	                int niveau = rs.getInt("niveau");
	
	                employee = new Employee(empId, empName, niveau);
	            }
	
	        } catch (SQLException e) {
	            throw new DataAccessException("Failed to retrieve employee with name = " + name, e);
	        }
	
	        return employee;
	    }

	    
	}
