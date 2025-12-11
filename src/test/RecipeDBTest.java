package test;

import database.RecipeDB;
import connection.DataAccessException;
import model.Recipe;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeDBTest {

    private static RecipeDB recipeDB;

    // IDs that must exist in your database for the test to work
    private static final int EXISTING_RECIPE_ID = 1;
    private static final int EXISTING_CANDY_ID = 1;

    @BeforeAll
    static void setup() {
        recipeDB = new RecipeDB();
    }

    @Test
    @Order(1)
    void testFindById() throws DataAccessException {
        Recipe recipe = recipeDB.findById(EXISTING_RECIPE_ID);
        assertNotNull(recipe, "Recipe should not be null");
        assertEquals(EXISTING_RECIPE_ID, recipe.getRecipeID());
        assertNotNull(recipe.getIngridients(), "Ingredients map should not be null");
        assertTrue(recipe.getIngridients().size() > 0, "Recipe should have at least one ingredient");
    }

    @Test
    @Order(2)
    void testGetRecipeByCandyId() throws DataAccessException {
        Recipe recipe = recipeDB.getRecipeByCandyId(EXISTING_CANDY_ID);
        assertNotNull(recipe, "Recipe should not be null");
        assertEquals(EXISTING_CANDY_ID, recipe.getRecipeID());
        assertNotNull(recipe.getIngridients(), "Ingredients map should not be null");
        assertTrue(recipe.getIngridients().size() > 0, "Recipe should have at least one ingredient");
    }
}
