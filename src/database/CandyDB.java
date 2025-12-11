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
    
    private final String INSERTCANDY = "INSERT INTO candy (CandyID, Type, Price, Stock, MinStock, MaxStock, Date, Name) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE candy SET Type = ?, Price = ?, Stock = ?, MinStock = ?, MaxStock = ?, Date = ?, Name = ? WHERE CandyID = ?";
    private final String DELETE = "DELETE FROM candy WHERE CandyID = ?";
    private final String FINDBYID  = "SELECT * FROM candy WHERE CandyID = ?";
    private final String GETLOWSTOCKCANDY  = "SELECT * FROM candy WHERE Stock < MinStock";
    private final String GETALLCANDY = "SELECT * FROM candy";
    

    public CandyDB() {
        try {
            conn = DBConnection.getInstance().getConnection();
        } catch (DataAccessException e) {
            System.out.println("Could not connect");
        }
    }

    // INSERT
    @Override
    public Candy insert(Candy candy) throws DataAccessException {
        String sql = "INSERT INTO Candy (CandyID, CandyType, Price, Stock, MinStock, MaxStock, Date, CandyName) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, candy.getCandyID());
            stmt.setString(2, candy.getType());
            stmt.setInt(3, candy.getPrice());
            stmt.setInt(4, candy.getStock());
            stmt.setInt(5, candy.getMinStock());
            stmt.setInt(6, candy.getMaxStock());
          //  stmt.setDate(7, candy.getDate());
            stmt.setString(8, candy.getName());

            stmt.executeUpdate();
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
          //  stmt.setDate(6, candy.getDate());
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
                return buildCandyObject(
                   		rs.getInt("CandyID"),
                        rs.getString("CandyType"),
                        rs.getInt("MinStock"),
                        rs.getInt("MaxStock"),                     
                        rs.getString("CandyName"));
                   
                
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
        String sql = "SELECT * FROM Candy";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

        	while (rs.next()) { 
                list.add(buildCandyObject(
                   		rs.getInt("CandyID"),
                        rs.getString("CandyType"),
                        rs.getInt("MinStock"),
                        rs.getInt("MaxStock"),                     
                        rs.getString("CandyName")));
                System.out.println("Object Build");
               
            
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to fetch all candy", e);
        }

        return list;
    }

    @Override
    public List<Candy> getCandyByType(String type) throws DataAccessException {
        List<Candy> list = new ArrayList<>();
        String sql = "SELECT * FROM candy WHERE CandyType = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) { 
                list.add(buildCandyObject(
                   		rs.getInt("CandyID"),
                        rs.getString("CandyType"),
                        rs.getInt("MinStock"),
                        rs.getInt("MaxStock"),                     
                        rs.getString("CandyName")));
               
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
                list.add(buildCandyObject(
                		rs.getInt("CandyID"),
                        rs.getString("CandyType"),
                        rs.getInt("MinStock"),
                        rs.getInt("MaxStock"),                     
                        rs.getString("CandyName")));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to fetch low stock candy", e);
        }

        return list;
    }
    
    private Candy buildCandyObject(int candyID, String type, int minStock, int maxStock, String name) {
    	Candy rs = null;
    	switch(type.toLowerCase()) {
    	case "bolcher":
    	case "bolsjer":
    		rs = new Bolcher(candyID, type, 50, 100 , 750, 300, name);
    		break;
    	case "lollipop":
    	case "slikkepind":
    		rs = new Lollipop(candyID, type, 50, 100 , 750, 300, name);
    		break;
    	case "gourmetBolcher":
    	case "gourmetBoljer":
    		rs = new GourmetBolcher(candyID, type, 50, 100 , 750, 300, name);
    		break;
    	
    	}
    	
    	
    	return rs;
    }
}
