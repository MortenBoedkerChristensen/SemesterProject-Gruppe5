package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Connection.DBConnection;
import Connection.DataAccessException;
import Model.Bolcher;
import Model.Candy;
import Model.GourmetBolcher;
import Model.LogoBolcher;

public class CandyDB implements CandyDAO {

    private static CandyDB instance;
    private Connection conn;

    private CandyDB() throws DataAccessException {
        // Hent connection fra dit DBConnection Singleton
        conn = DBConnection.getInstance().getConnection();
    }

    public static synchronized CandyDB getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new CandyDB();
        }
        return instance;
    }

    // INSERT
    @Override
    public Candy insert(Candy candy) throws DataAccessException {

        String sql = "INSERT INTO candy (CandyID, Type, Price, Stock, MinStock, MaxStock, Date) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, candy.getCandyID());
            stmt.setString(2, candy.getType());
            stmt.setInt(3, candy.getPrice());
            stmt.setInt(4, candy.getStock());
            stmt.setInt(5, candy.getMinStock());
            stmt.setInt(6, candy.getMaxStock());
            stmt.setDate(7, candy.getDate());

            stmt.executeUpdate();
            return candy;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to insert candy", e);
        }
    }

    // UPDATE
    @Override
    public Candy update(Candy candy) throws DataAccessException {

        String sql = "UPDATE candy SET Type = ?, Price = ?, Stock = ?, MinStock = ?, MaxStock = ?, Date = ? "
                   + "WHERE CandyID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, candy.getType());
            stmt.setInt(2, candy.getPrice());
            stmt.setInt(3, candy.getStock());
            stmt.setInt(4, candy.getMinStock());
            stmt.setInt(5, candy.getMaxStock());
            stmt.setDate(6, candy.getDate());
            stmt.setInt(7, candy.getCandyID());

            stmt.executeUpdate();
            return candy;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to update candy", e);
        }
    }

    // DELETE
    @Override
    public void delete(int id) throws DataAccessException {

        String sql = "DELETE FROM candy WHERE CandyID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete candy", e);
        }
    }

    // FIND BY ID
    @Override
    public Candy findById(int id) throws DataAccessException {

        String sql = "SELECT * FROM candy WHERE CandyID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Candy(
                    rs.getInt("CandyID"),
                    rs.getString("Type"),
                    rs.getInt("Price"),
                    rs.getInt("MinStock"),
                    rs.getInt("MaxStock"),
                    rs.getDate("Date"),
                    rs.getInt("Stock")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to fetch candy by ID", e);
        }
    }

    // GET ALL ??????????
    @Override
    public List<Candy> getAllCandy() throws DataAccessException {

        List<Candy> list = new ArrayList<>();
        String sql = "SELECT * FROM candy";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Candy candy = new Candy(
                    rs.getInt("CandyID"),
                    rs.getString("Type"),
                    rs.getInt("Price"),
                    rs.getInt("MinStock"),
                    rs.getInt("MaxStock"),
                    rs.getDate("Date"),
                    rs.getInt("Stock")
                );
                list.add(candy);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to fetch all candy", e);
        }

        return list;
    }

    @Override
    public List<Candy> getCandyByType(String type) throws DataAccessException {
        List<Candy> list = new ArrayList<>();
        String sql = "SELECT * FROM candy WHERE Type = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Candy candy;

                // Lav en instans af den rigtige subklasse baseret på type
                switch (type.toLowerCase()) {
                    case "bolcher":
                        candy = new Bolcher(
                            rs.getInt("CandyID"),
                            rs.getString("Type"),
                            rs.getInt("Price"),
                            rs.getInt("Stock"),
                            rs.getInt("MinStock"),
                            rs.getInt("MaxStock"),
                            rs.getDate("Date")
                        );
                        break;

                    case "logobolcher":
                        candy = new LogoBolcher(
                            rs.getInt("CandyID"),
                            rs.getString("Type"),
                            rs.getInt("Price"),
                            rs.getInt("Stock"),
                            rs.getInt("MinStock"),
                            rs.getInt("MaxStock"),
                            rs.getDate("Date")
                        );
                        break;

                    case "gourmetbolcher":
                        candy = new GourmetBolcher(
                            rs.getInt("CandyID"),
                            rs.getString("Type"),
                            rs.getInt("Price"),
                            rs.getInt("Stock"),
                            rs.getInt("MinStock"),
                            rs.getInt("MaxStock"),
                            rs.getDate("Date")
                        );
                        break;

                    default:
                        candy = new Candy(
                            rs.getInt("CandyID"),
                            rs.getString("Type"),
                            rs.getInt("Price"),
                            rs.getInt("MinStock"),
                            rs.getInt("MaxStock"),
                            rs.getDate("Date"),
                            rs.getInt("Stock")
                        );
                        break;
                }

                list.add(candy);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to fetch candy by type", e);
        }

        return list;
    }
}
