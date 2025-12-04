package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

import Connection.DBConnection;
import Model.Candy;

public class CandyDB {

    private static CandyDB instance;
    private Connection conn;
    private DBConnection dbconnection;

    private CandyDB(String name) {
        dbconnection = new DBConnection(name);
        conn = dbconnection.getConnection();
    }

    public static synchronized CandyDB getInstance(String name) {
        if (instance == null) {
            instance = new CandyDB(name);
        }
        return instance;
    }

    // INSERT
    public void insertCandy(int candyID, String type, int minStock, int maxStock,
                            int price, Date date, int stock) {

        String sql = "INSERT INTO candy (CandyID, Type, MinStock, MaxStock, Price, Date, Stock) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, candyID);
            stmt.setString(2, type);
            stmt.setInt(3, minStock);
            stmt.setInt(4, maxStock);
            stmt.setInt(5, price);
            stmt.setDate(6, date);
            stmt.setInt(7, stock);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // GET by type
    public Candy getCandyByType(String type) {

        String sql = "SELECT * FROM candy WHERE Type = ?";
        Candy candy = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                candy = new Candy(
                		 rs.getInt("CandyID"),
                         rs.getString("Type"),
                         rs.getInt("Price"),
                         rs.getInt("MinStock"),
                         rs.getInt("MaxStock"),
                         rs.getDate("Date"),
                         rs.getInt("Stock")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return candy;
    }

    // GET by ID
    public Candy getCandyByID(int id) {

        String sql = "SELECT * FROM candy WHERE CandyID = ?";
        Candy candy = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                candy = new Candy(
                    rs.getInt("CandyID"),
                    rs.getString("Type"),
                    rs.getInt("Price"),
                    rs.getInt("MinStock"),
                    rs.getInt("MaxStock"),
                    rs.getDate("Date"),
                    rs.getInt("Stock")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return candy;
    }

    // UPDATE
    public void updateCandy(Candy candy) {

        String sql = "UPDATE candy SET Type = ?, MinStock = ?, MaxStock = ?, Price = ?, Date = ?, Stock = ? "
                   + "WHERE CandyID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, candy.getType());
            stmt.setInt(2, candy.getMinStock());
            stmt.setInt(3, candy.getMaxStock());
            stmt.setInt(4, candy.getPrice());
            stmt.setDate(5, candy.getDate());
            stmt.setInt(6, candy.getStock());
            stmt.setInt(7, candy.getCandyID());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
