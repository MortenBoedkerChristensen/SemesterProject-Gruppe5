package Controller;

import Database.EmployeeDAO;
import Database.EmployeeDB;
import Connection.DataAccessException;
import Model.Employee;
import Model.Candy;
import Database.*;

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
