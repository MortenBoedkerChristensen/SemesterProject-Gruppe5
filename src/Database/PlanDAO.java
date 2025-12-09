package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import Connection.DataAccessException;
import Model.Plan;

public interface PlanDAO {

	Plan create(Plan plan) throws DataAccessException;

	Plan findById(int id) throws DataAccessException;
	
	Plan buildPlan(ResultSet rs) throws SQLException;

}
