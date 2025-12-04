package Controller;

import Database.EmployeeDAO;
import Database.EmployeeDB;
import Connection.DataAccessException;
import Model.Employee;

public class EmployeeController {

    private EmployeeDAO employeeDAO;

    public EmployeeController() {
        try {
            employeeDAO = new EmployeeDB();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    // Laver en ny Employee
    
    public Employee createEmployee(int employeeId, String name, String niveau) {
        Employee employee = new Employee(employeeId, name, niveau);

        try {
            return employeeDAO.insert(employee);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee getEmployee(int id) {
        try {
            return employeeDAO.findById(id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee updateEmployee(Employee employee) {
        try {
            return employeeDAO.update(employee);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    
    public void updateEmployeeName(int id, String newName) {
        try {
            Employee emp = employeeDAO.findById(id);
            if (emp != null) {
                emp.setName(newName);
                employeeDAO.update(emp);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployeeNiveau(int id, String newNiveau) {
        try {
            Employee emp = employeeDAO.findById(id);
            if (emp != null) {
                emp.setNiveau(newNiveau);  
                employeeDAO.update(emp);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int id) {
        try {
            employeeDAO.delete(id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
