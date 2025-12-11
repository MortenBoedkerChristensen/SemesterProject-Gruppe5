package utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DBConnection;
import connection.DataAccessException;
import database.CandyDB;

public class DevMenu {

	public static void main(String[] args) throws DataAccessException {
	/*	try {
			Connection conn = DBConnection.getInstance().getConnection();
			System.out.println("Success");
			String sql = "SELECT * FROM CANDY";
			int x = 0;
			try(PreparedStatement stmt = conn.prepareStatement(sql);
				 ResultSet rs = stmt.executeQuery()) {

			            while (rs.next()) {
			            	x++;
			            }
			            System.out.println(x);
				 }
				
			} catch (SQLException | DataAccessException e) {
				// TODO Auto-generated catch block
				System.out.println("somting wroooong :(((");
				e.printStackTrace();
			} */
		CandyDB cdb = new CandyDB();
		System.out.println(cdb.getAllCandy());
		
		

	}

}
