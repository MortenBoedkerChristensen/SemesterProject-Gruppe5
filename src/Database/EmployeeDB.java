package Database;

import java.util.List;

import Connection.DBConnection;
import Connection.DataAccessException;
import Model.Employee;

public class EmployeeDB implements EmployeeDAO {

    private DBConnection dbConn;

    public EmployeeDB() throws DataAccessException {
        dbConn = DBConnection.getInstance();
    }

    // INSERT
    
    @Override
    public Employee insert(Employee employee) throws DataAccessException {
        String sql = "INSERT INTO Employee (employeeId, name, niveau) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = dbConn.getConnection().prepareStatement(sql);
            stmt.setInt(1, employee.getEmployeeId());
            stmt.setString(2, employee.getName());
            stmt.setString(3, employee.getNiveau());
            stmt.executeUpdate();
            return employee;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to insert employee", e);
        }
    }


    // FIND BY ID
    
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
                String niveau = rs.getString("niveau");

                employee = new Employee(empId, name, niveau);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve employee with ID = " + id, e);
        }

        return employee;
    }


    // UPDATE
    
    @Override
    public Employee update(Employee employee) throws DataAccessException {
        String sql = "UPDATE Employee SET name = ?, niveau = ? WHERE employeeId = ?";

        try {
            PreparedStatement stmt = dbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getNiveau());
            stmt.setInt(3, employee.getEmployeeId());

            stmt.executeUpdate();
            return employee;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to update employee with ID = " + employee.getEmployeeId(), e);
        }
    }

    // DELETE
    
    @Override
    public void delete(int id) throws DataAccessException {
        String sql = "DELETE FROM Employee WHERE employeeId = ?";

        try {
            PreparedStatement stmt = dbConn.getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete employee with ID = " + id, e);
        }
    }

    // GET ALL EMPLOYEES
    
    @Override
    public List<Employee> getAllEmployees() throws DataAccessException {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT employeeId, name, niveau FROM Employee";

        try {
            PreparedStatement stmt = dbConn.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("employeeId");
                String name = rs.getString("name");
                String niveau = rs.getString("niveau");

                list.add(new Employee(id, name, niveau));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve employees", e);
        }

        return list;
    }

	@Override
	public List<Employee> getEmployeesAtLevelOrHigher(int level) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
}
