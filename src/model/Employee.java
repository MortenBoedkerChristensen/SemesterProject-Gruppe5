package model;

public class Employee {
    private int employeeId;
    private String name;
    private int niveau;

    public Employee(int employeeId, String name, int niveau) {
        this.employeeId = employeeId;
        this.name = name;
        this.niveau = niveau;
    }

    // Getters
    public int getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public int getNiveau() { return niveau; }

    // Setters
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
    public void setName(String name) { this.name = name; }
    public void setNiveau(int niveau) { this.niveau = niveau; }
}
