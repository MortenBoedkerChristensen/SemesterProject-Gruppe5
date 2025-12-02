package Database;

import java.util.List;

import Connection.DataAccessException;
import Model.Employee;

public interface EmployeeDAO {
	
	List<Employee> getAllProducts() throws DataAccessException;

	void delete(int id) throws DataAccessException;

	Employee update(Employee employee) throws DataAccessException;

	Employee findById(int id) throws DataAccessException;

	Employee insert(Employee employee) throws DataAccessException;


}
