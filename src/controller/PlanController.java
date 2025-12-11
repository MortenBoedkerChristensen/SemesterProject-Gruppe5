package controller;

import connection.DataAccessException;
import database.PlanDB;
import model.Plan;
import model.PlanItem;

public class PlanController {

    private final PlanDB planDB;

    public PlanController() throws DataAccessException {
        planDB = new PlanDB();
    }

    public String createPlannedProductionForEmployee(String employeeName, int maxCandies) throws DataAccessException {
        Plan plan = planDB.createPlannedProductionForEmployee(employeeName, maxCandies);

        if (plan == null || plan.getItems().isEmpty()) {
            return "Ingen plan blev oprettet.";
        }

        StringBuilder sb = new StringBuilder();
        for (PlanItem item : plan.getItems()) {
            sb.append("Candy: ").append(item.getRecipe().getName())
              .append(", Qty: ").append(item.getQty())
              .append(", Status: ").append(item.getStatus()).append("\n");
        }

        return sb.toString();
    }
}
