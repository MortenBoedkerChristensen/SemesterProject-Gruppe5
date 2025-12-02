package Database;

import java.util.List;

import Connection.DataAccessException;
import Model.Plan;

public interface PlanDAO {
	
	List<Plan> getAllProducts() throws DataAccessException;

	void delete(int id) throws DataAccessException;

	Plan update(Plan plan) throws DataAccessException;

	Plan findById(int id) throws DataAccessException;

	Plan insert(Plan plan) throws DataAccessException;

}
