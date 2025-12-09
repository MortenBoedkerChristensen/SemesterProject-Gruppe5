package Test;

import Database.CandyDB;
import Database.EmployeeDB;
import Database.PlanDB;
import Controller.PlanController;
import Model.Candy;
import Model.Employee;
import Model.Plan;
import Connection.DataAccessException;

import java.sql.Date;
import java.util.List;

public class TryMe {

    public static void main(String[] args) {
        TryMe tester = new TryMe();
        //tester.runCandyTests();
        //tester.runEmployeeTests();
        tester.runPlanTests();
    }

    public void runCandyTests() {
        CandyDB db = new CandyDB();
        try {
            // finder på id
            Candy found = db.findById(1);
            System.out.println(found != null ? found : "Candy with ID=1 not found");

            // Ser efter low stock
            List<Candy> lowStock = db.getLowStockCandy();
            if (lowStock.isEmpty()) System.out.println("No low stock candies");
            else lowStock.forEach(System.out::println);

            System.out.println("=== CANDY TESTS END ===");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    // 
    public void runEmployeeTests() {
        try {
            EmployeeDB db = new EmployeeDB();
            System.out.println("=== EMPLOYEE TESTS START ===");

            Employee emp = db.findById(1);
            if (emp != null) {
                System.out.println("Employee found: " + emp.getName() + ", Niveau: " + emp.getNiveau());
            } else {
                System.out.println("No employee found with ID=1");
            }

            System.out.println("=== EMPLOYEE TESTS END ===");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    // Test Plan
    public void runPlanTests() {
        try {
            PlanController controller = new PlanController();
            // Laver en plan for 4 produktioner
           // controller.createPlannedProduction(4);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
