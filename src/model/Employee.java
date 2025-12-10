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
	
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	public int getEmployeeId() {
		return employeeId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void niveau(int niveau) {
		this.niveau = niveau;
	}
	
	public int getNiveau() {
		return niveau;
	}
	public int setNiveau(int niveau) {
		return this.niveau = niveau;
	}
}
