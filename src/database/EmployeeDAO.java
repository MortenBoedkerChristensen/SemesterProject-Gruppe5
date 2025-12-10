package database;

import java.util.List;

import connection.DataAccessException;
import model.Employee;

public interface EmployeeDAO {
	
	

	Employee findById(int id) throws DataAccessException;



}
