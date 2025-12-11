package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import connection.DataAccessException;
import model.Bolcher;
import model.Candy;
import model.GourmetBolcher;
import model.LogoBolcher;
import model.Lollipop;

public class CandyDB implements CandyDAO {

    private Connection conn;

    public CandyDB() {
        try {
            conn = DBConnection.getInstance().getConnection();
        } catch (DataAccessException e) {
            System.out.println("Could not connect");
        }
    }

    @Override
    public Candy insert(Candy candy) throws DataAccessException {
        String sql = "INSERT INTO candy (Type, Price, Stock, MinStock, MaxStock, Date, Name) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, candy.getType());
            stmt.setInt(2, candy.getPrice());
            stmt.setInt(3, candy.getStock());
            stmt.setInt(4, candy.getMinStock());
            stmt.setInt(5, candy.getMaxStock());
            stmt.setDate(6, candy.getDate());
            stmt.setString(7, candy.getName());

            stmt.executeUpdate();

            // get generated ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                candy.setCandyID(rs.getInt(1));
            }

            return candy;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to insert candy", e);
        }
    }


    // UPDATE
    @Override
    public Candy update(Candy candy) throws DataAccessException {
        String sql = "UPDATE candy SET Type = ?, Price = ?, Stock = ?, MinStock = ?, MaxStock = ?, Date = ?, Name = ? "
                   + "WHERE CandyID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, candy.getType());
            stmt.setInt(2, candy.getPrice());
            stmt.setInt(3, candy.getStock());
            stmt.setInt(4, candy.getMinStock());
            stmt.setInt(5, candy.getMaxStock());
            stmt.setDate(6, candy.getDate());
            stmt.setString(7, candy.getName());
            stmt.setInt(8, candy.getCandyID());

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
                        rs.getInt("Stock"),
                        rs.getString("Name")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to fetch candy by ID", e);
        }
    }

    // GET ALL
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
                        rs.getInt("Stock"),
                        rs.getString("Name")
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

                switch (type.toLowerCase()) {
                    case "bolcher":
                        candy = new Bolcher(
                                rs.getInt("CandyID"),
                                rs.getString("Type"),
                                rs.getInt("Price"),
                                rs.getInt("Stock"),
                                rs.getInt("MinStock"),
                                rs.getInt("MaxStock"),
                                rs.getDate("Date"),
                                rs.getString("Name")
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
                                rs.getDate("Date"),
                                rs.getString("Name")
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
                                rs.getDate("Date"),
                                rs.getString("Name")
                        );
                        break;

                    case "lollipop":
                        candy = new Lollipop(
                                rs.getInt("CandyID"),
                                rs.getString("Type"),
                                rs.getInt("Price"),
                                rs.getInt("Stock"),
                                rs.getInt("MinStock"),
                                rs.getInt("MaxStock"),
                                rs.getDate("Date"),
                                rs.getString("Name")
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
                                rs.getInt("Stock"),
                                rs.getString("Name")
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

    @Override
    public List<Candy> getLowStockCandy() throws DataAccessException {
        List<Candy> list = new ArrayList<>();
        String sql = "SELECT * FROM candy WHERE Stock < MinStock";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Candy candy = new Candy(
                        rs.getInt("CandyID"),
                        rs.getString("Type"),
                        rs.getInt("Price"),
                        rs.getInt("MinStock"),
                        rs.getInt("MaxStock"),
                        rs.getDate("Date"),
                        rs.getInt("Stock"),
                        rs.getString("Name")
                );
                list.add(candy);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to fetch low stock candy", e);
        }

        return list;
    }
}
