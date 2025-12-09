package Controller;

import Connection.DataAccessException;
import Database.PlanDB;

public class PlanController {

    private final PlanDB planDB;

    public PlanController() throws DataAccessException {
        planDB = new PlanDB();
    }

    // Just delegate to PlanDB
    public void createPlannedProduction(int maxCandies) throws DataAccessException {
        planDB.createPlannedProduction(maxCandies);
    }
}
