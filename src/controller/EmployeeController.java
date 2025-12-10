package controller;

import connection.DataAccessException;
import database.*;
import model.Candy;
import model.Employee;

public class EmployeeController {
	CandyDAO CandyDB;
	Candy candy;

    private EmployeeDAO employeeDAO;

    public EmployeeController() {
        try {
            employeeDAO = new EmployeeDB();
        } catch (DataAccessException e) {
            e.printStackTrace();
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
}
