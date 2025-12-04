package Database;

import Model.Recipes;
import Connection.DBConnection;
import Connection.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeDB {

    private Connection conn;

    public RecipeDB() throws DataAccessException {
        conn = DBConnection.getInstance().getConnection();
    }

    public Recipes getRecipeByCandyId(int candyId) throws DataAccessException {
        Recipes recipe = null;

        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT Ingredients, Name, Niveau, QtyID FROM Recipe WHERE CandyID = ?"
        )) {
            stmt.setInt(1, candyId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    if (recipe == null) {
                        // First row: initialize recipe
                        recipe = new Recipes(candyId, rs.getString("Name"), rs.getInt("Niveau"));
                    }
                    // Add ingredient from this row
                    String ingredientName = rs.getString("Ingredients");
                    int qty = rs.getInt("QtyID");
                    recipe.addIngridient(ingredientName, qty);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to load recipe for candyID " + candyId, e);
        }

        return recipe;
    }

}
