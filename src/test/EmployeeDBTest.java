package test;

import database.EmployeeDB;
import connection.DataAccessException;
import model.Employee;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeDBTest {

    private static EmployeeDB employeeDB;
    private static Employee testEmployee;

    @BeforeAll
    static void setup() throws DataAccessException {
        employeeDB = new EmployeeDB();

        // Assume there's already an employee in your DB, otherwise insert one
        // For testing purposes, let's assume ID = 9999 and name = "TestEmployee"
        testEmployee = new Employee(9999, "TestEmployee", 1);
    }

    @Test
    @Order(1)
    void testFindById() throws DataAccessException {
        Employee emp = employeeDB.findById(testEmployee.getEmployeeId());
        assertNotNull(emp, "Employee should not be null");
        assertEquals("TestEmployee", emp.getName());
        assertEquals(1, emp.getNiveau());
    }

    @Test
    @Order(2)
    void testFindByName() throws DataAccessException {
        Employee emp = employeeDB.FindByName("TestEmployee");
        assertNotNull(emp, "Employee should not be null");
        assertEquals(testEmployee.getEmployeeId(), emp.getEmployeeId());
        assertEquals(1, emp.getNiveau());
    }

    @Test
    @Order(3)
    void testGetAllEmployees() throws DataAccessException {
        List<Employee> all = employeeDB.getAllEmployees();
        assertNotNull(all, "Employee list should not be null");
        assertTrue(all.size() > 0, "There should be at least one employee");
        assertTrue(all.stream().anyMatch(e -> e.getEmployeeId() == 9999));
    }
}
