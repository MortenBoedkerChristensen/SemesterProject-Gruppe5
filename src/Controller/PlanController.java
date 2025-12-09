package Controller;

import Connection.DataAccessException;
import Database.PlanDB;

public class PlanController {

    private final PlanDB planDB;

    public PlanController() throws DataAccessException {
        planDB = new PlanDB();
    }
    
    public void createPlannedProductionForEmployee(String employeeName, int maxCandies) throws DataAccessException {
		planDB.createPlannedProductionForEmployee(employeeName, maxCandies);
	}
}
