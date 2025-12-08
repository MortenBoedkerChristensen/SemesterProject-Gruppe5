package Database;

import java.util.List;

import Connection.DataAccessException;
import Model.Employee;

public interface EmployeeDAO {
	
	

	Employee findById(int id) throws DataAccessException;



}
