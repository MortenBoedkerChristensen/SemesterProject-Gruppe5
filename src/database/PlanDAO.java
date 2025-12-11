package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DataAccessException;
import model.Plan;

public interface PlanDAO {

	Plan create(Plan plan) throws DataAccessException;
	
	void savePlanWithItems(Plan plan) throws DataAccessException;

	Plan findById(int id) throws DataAccessException;

}
