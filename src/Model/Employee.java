package Model;

public class Employee {
	private int employeeId;
	private String name;
	private String niveau;
	
	public Employee(int employeeId, String name, String niveau) {
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
	
	public void niveau(String niveau) {
		this.niveau = niveau;
	}
	
	public String getNiveau() {
		return niveau;
	}
	public String setNiveau(String niveau) {
		return this.niveau = niveau;
	}
}
