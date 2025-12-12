package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Plan {

    private int planID;
    private Date date;

    private List<PlanItem> items = new ArrayList<>();

    public Plan() {}

    public Plan(Date date) {
        this.date = date;
    }

    // ----- ID -----
    public int getPlanID() { return planID; }
    public void setPlanID(int planID) { this.planID = planID; }

    // ----- DATE -----
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    // ----- PLAN ITEMS -----
    public List<PlanItem> getItems() { return items; }
    public void setItems(List<PlanItem> items) { this.items = items; }

    public void addItem(PlanItem item) { items.add(item); }
    public void removeItem(PlanItem item) { items.remove(item); }
    public PlanItem getItem(int index) { return items.get(index); }

    // Find recipe by ID
    public PlanItem findItemByRecipeId(int recipeId) {
        for (PlanItem item : items) {
            if (item.getRecipe().getRecipeID() == recipeId) {
                return item;
            }
        }
        return null;
    }

    // Update qty for specific recipe
    public void updateQty(int recipeId, int qty) {
        PlanItem item = findItemByRecipeId(recipeId);
        if (item != null) {
            item.setQty(qty);
        }
    }
}