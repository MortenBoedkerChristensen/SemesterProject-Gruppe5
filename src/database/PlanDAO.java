package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DataAccessException;
import model.Plan;

public interface PlanDAO {

	Plan create(Plan plan) throws DataAccessException;

	Plan findById(int id) throws DataAccessException;
	
	Plan buildPlan(ResultSet rs) throws SQLException;

}
