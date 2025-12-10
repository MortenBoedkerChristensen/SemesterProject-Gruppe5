package model;

import java.sql.Date;
import java.util.List;

public class Plan {

    private int planID;
    private Date date;
    private List<PlanItem> plan;
    /*
     * #TODO
     * Metoder til at håndtere PlanItem
     * Display af aktiv plan => UI
     */
    
  /*  private int locationID;     // matches SQL table
    private int candyID;        // matches SQL table
    private Recipes recipe;     // single recipe per candy
*/
    // Enum for status
    

    // Constructors
    public Plan() {}

    public Plan(List<PlanItem> plan) {
        this.plan = plan;
    }

    // ----- ID -----
    public int getPlanID() {
        return planID;
    }

    public void setPlanID(int planID) {
        this.planID = planID;
    }

    // ----- DATE -----
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public List<PlanItem> getPlan() {
    	return plan;
    }
    
    public PlanItem getPlanItem(int index) {
    	return plan.get(index);
    }

    /*
    // ----- STATUS -----
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // ----- LOCATION -----
    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }
    

    // ----- CANDY -----
    public int getCandyID() {
        return candyID;
    }

    public void setCandyID(int candyID) {
        this.candyID = candyID;
    }

    // ----- RECIPE -----
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    } */
}
