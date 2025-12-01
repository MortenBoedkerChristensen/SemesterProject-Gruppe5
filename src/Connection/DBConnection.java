package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * 
 * @author knol
 * @version 2018-08-30
 * revised by BON
 * @version 2022-09-28
 *  * revised by GIBE
 * @version 2024, 2025
 */
public class DBConnection {
	private Connection connection = null;
	private static DBConnection dbConnection;

	
	private static final String DBNAME = "DMA-CSD-V251_10665995";
	private static final String SERVERNAME = "hildur.ucn.dk";
	private static final String PORTNUMBER = "1433";
	private static final String USERNAME = "DMA-CSD-V251_10665995";
	private static final String PASSWORD = "Password1!";

	// constructor - private because of singleton pattern
	private DBConnection() throws DataAccessException {
		String urlString = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=false", SERVERNAME, PORTNUMBER,
				DBNAME);
		try {
			connection = DriverManager.getConnection(urlString, USERNAME, PASSWORD);
		} catch (SQLException e) {
			throw new DataAccessException(String.format("Could not connect to database %s@%s:%s user %s", DBNAME,
					SERVERNAME, PORTNUMBER, USERNAME), e);
		}
	}


	public static synchronized DBConnection getInstance() throws DataAccessException {
		if (dbConnection == null) {
			dbConnection = new DBConnection();
		}
		return dbConnection;
	}

	public void startTransaction() throws DataAccessException {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException("Could not start transaction.", e);
		}
	}

	public void commitTransaction() throws DataAccessException {
		try {
			comTra();
		} catch (SQLException e) {
			throw new DataAccessException("Could not commit transaction", e);
		}
	}

	private void comTra() throws SQLException {
		try {
			connection.commit();
		} catch (SQLException e) {
			throw e;
			// e.printStackTrace();
		} finally {
			connection.setAutoCommit(true);
		}
	}

	public void rollbackTransaction() throws DataAccessException {
		try {
			rolTra();
		} catch (SQLException e) {
			throw new DataAccessException("Could not rollback transaction", e);
		}
	}

	private void rolTra() throws SQLException {
		try {
			connection.rollback();
		} catch (SQLException e) {
			throw e;
			// e.printStackTrace();
		} finally {
			connection.setAutoCommit(true);
		}
	}


	public Connection getConnection() {
		return connection;
	}

}
