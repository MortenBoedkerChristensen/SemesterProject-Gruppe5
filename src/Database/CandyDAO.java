package Database;

import java.util.List;

import Connection.DataAccessException;
import Model.Candy;

public interface CandyDAO {
	
	List<Candy> getAllCandy() throws DataAccessException;
	
	List<Candy> getCandyByType(String type) throws DataAccessException;

	void delete(int id) throws DataAccessException;

	Candy update(Candy candy) throws DataAccessException;

	Candy findById(int id) throws DataAccessException;

	Candy insert(Candy candy) throws DataAccessException;
}