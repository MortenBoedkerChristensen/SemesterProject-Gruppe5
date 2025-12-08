package Model;

import java.sql.Date;

public class Plan {

    private int planID;
    private Date date;
    private Status status;
    private int locationID;     // matches SQL table
    private int candyID;        // matches SQL table
    private Recipes recipe;     // single recipe per candy

    // Enum for status
    public enum Status {
        STARTED,
        COOKING,
        READY
    }

    // Constructors
    public Plan() {}

    public Plan(Recipes recipe) {
        this.recipe = recipe;
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
    public Recipes getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipes recipe) {
        this.recipe = recipe;
    }
}
