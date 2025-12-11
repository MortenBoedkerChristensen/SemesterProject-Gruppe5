package test;

import database.PlanDB;
import connection.DataAccessException;
import model.Plan;
import model.PlanItem;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlanDBTest {

    private static PlanDB planDB;
    private static Plan testPlan;
    private static final String TEST_EMPLOYEE_NAME = "TestEmployee"; // should exist in Employee table

    @BeforeAll
    static void setup() throws DataAccessException {
        planDB = new PlanDB();

        // Create a simple Plan object for testing
        testPlan = new Plan();
        testPlan.setDate(new Date(System.currentTimeMillis()));
        // Normally items are added via createPlannedProductionForEmployee, not manually
    }

    @Test
    @Order(1)
    void testCreatePlannedProductionForEmployee() throws DataAccessException {
        Plan plan = planDB.createPlannedProductionForEmployee(TEST_EMPLOYEE_NAME, 3);
        assertNotNull(plan, "Plan should not be null");
        assertFalse(plan.getItems().isEmpty(), "Plan should have at least one item");

        testPlan = plan; // save for later tests
    }

    @Test
    @Order(2)
    void testFindById() throws DataAccessException {
        assertNotNull(testPlan, "Previous plan creation failed");
        Plan plan = planDB.findById(testPlan.getPlanID());
        assertNotNull(plan, "Should retrieve plan by ID");
        assertEquals(testPlan.getPlanID(), plan.getPlanID());
        assertEquals(testPlan.getItems().size(), plan.getItems().size());
    }

    @Test
    @Order(3)
    void testGetAllPlans() throws DataAccessException {
        List<Plan> plans = planDB.getAllPlans();
        assertNotNull(plans, "getAllPlans should not return null");
        assertTrue(plans.stream().anyMatch(p -> p.getPlanID() == testPlan.getPlanID()),
                "Created test plan should be in getAllPlans result");
    }
}
