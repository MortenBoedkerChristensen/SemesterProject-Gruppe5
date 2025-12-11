package test;

import database.PlanDB;
import connection.DBConnection;
import connection.DataAccessException;
import model.Plan;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlanDBTest {

    private static PlanDB planDB;

    private static int employeeId;
    private static int candyId;
    private static int recipeId;
    private static int planId; // <-- store the created plan

    @BeforeAll
    static void setup() throws Exception {

        planDB = new PlanDB();
        Connection conn = DBConnection.getInstance().getConnection();

        //
        // 1. Insert employee
        //
        PreparedStatement empStmt = conn.prepareStatement(
            "INSERT INTO Employee (name, niveau) VALUES ('TestEmployee', 5)",
            Statement.RETURN_GENERATED_KEYS
        );
        empStmt.executeUpdate();
        ResultSet empRS = empStmt.getGeneratedKeys();
        empRS.next();
        employeeId = empRS.getInt(1);

        //
        // 2. Insert candy with low stock
        //
        PreparedStatement candyStmt = conn.prepareStatement(
            "INSERT INTO Candy (Type, Name, Price, Stock, MinStock, MaxStock, Date) " +
            "VALUES ('TestType', 'TestCandy', 5, 1, 100, 200, GETDATE())",
            Statement.RETURN_GENERATED_KEYS
        );
        candyStmt.executeUpdate();
        ResultSet candyRS = candyStmt.getGeneratedKeys();
        candyRS.next();
        candyId = candyRS.getInt(1);

        //
        // 3. Insert recipe for that candy
        //
        PreparedStatement recipeStmt = conn.prepareStatement(
            "INSERT INTO Recipe (CandyID, Name, Niveau, Ingredients, QtyID) " +
            "VALUES (?, 'TestRecipe', 3, 'Sugar', 1)",
            Statement.RETURN_GENERATED_KEYS
        );
        recipeStmt.setInt(1, candyId);
        recipeStmt.executeUpdate();
        ResultSet recipeRS = recipeStmt.getGeneratedKeys();
        recipeRS.next();
        recipeId = recipeRS.getInt(1);
    }


    @Test
    @Order(1)
    void testCreatePlannedProductionForEmployee() throws DataAccessException {
        Plan plan = planDB.createPlannedProductionForEmployee("TestEmployee", 3);

        assertNotNull(plan);
        assertFalse(plan.getItems().isEmpty(), "Plan must contain items");

        planId = plan.getPlanID(); // store for test #2
    }

    @Test
    @Order(2)
    void testFindById() throws DataAccessException {
        Plan loaded = planDB.findById(planId);

        assertNotNull(loaded);
        assertEquals(planId, loaded.getPlanID());
        assertFalse(loaded.getItems().isEmpty());
    }

    @AfterAll
    static void cleanup() throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();

        // delete PlanItems for our plan
        conn.prepareStatement("DELETE FROM PlanItem WHERE PlanID = " + planId).executeUpdate();

        // delete the plan
        conn.prepareStatement("DELETE FROM [Plan] WHERE Date = CONVERT(date, GETDATE())").executeUpdate();


        conn.prepareStatement("DELETE FROM Recipe WHERE RecipeID = " + recipeId).executeUpdate();
        conn.prepareStatement("DELETE FRom Candy WHERE CandyID = " + candyId).executeUpdate();
        conn.prepareStatement("DELETE FROM Employee WHERE employeeId = " + employeeId).executeUpdate();
    }
}
