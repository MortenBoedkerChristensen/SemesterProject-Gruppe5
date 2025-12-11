package controller;

import java.util.List;
import connection.DataAccessException;
import database.PlanDB;
import model.Plan;

public class PlanController {

    private final PlanDB planDB;

    public PlanController() throws DataAccessException {
        planDB = new PlanDB();
    }

    public Plan createPlannedProductionForEmployee(String employeeName, int maxItems) throws DataAccessException {
        return planDB.createPlannedProductionForEmployee(employeeName, maxItems);
    }

    public List<Plan> getAllPlans() throws DataAccessException {
        return planDB.getAllPlans();
    }
}
