package controller;

import java.util.List;
import connection.DataAccessException;
import database.PlanDB;
import model.Plan;
import model.PlanItem;

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
    public String createProductionPlanText(String employeeName, int amount) throws DataAccessException {
        Plan plan = createPlannedProductionForEmployee(employeeName, amount);

        if (plan == null || plan.getItems().isEmpty()) {
            return "Ingen plan blev oprettet.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Plan ID: ").append(plan.getPlanID())
          .append(", Dato: ").append(plan.getDate()).append("\n\n");

        for (PlanItem item : plan.getItems()) {
            sb.append("Candy: ").append(item.getCandy().getName())
              .append(", Recipe: ").append(item.getRecipe().getName())
              .append(", Qty: ").append(item.getQty())
              .append(", Ingredients: ");

            item.getRecipe().getIngridients().forEach((ing, qty) ->
                sb.append(ing).append(" ").append(qty).append(", ")
            );

            sb.setLength(sb.length() - 2);
            sb.append("\n");
        }

        return sb.toString();
    }

}
