package model;

public class PlanItem {
    private Recipe recipe;
    private Candy candy;
    private int qty;
    private Status status;
    private Employee employee;

    public PlanItem(Recipe recipe, Candy candy, int qty) {
        this.recipe = recipe;
        this.candy = candy;
        this.qty = qty;
        this.status = Status.STARTED; // default
    }

    public enum Status {
        STARTED,
        COOKING,
        READY
    }

    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) { this.recipe = recipe; }

    public Candy getCandy() { return candy; }
    public void setCandy(Candy candy) { this.candy = candy; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
}
