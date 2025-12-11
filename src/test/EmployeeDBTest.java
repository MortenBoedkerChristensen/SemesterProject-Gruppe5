package test;

import database.EmployeeDB;
import connection.DBConnection;
import connection.DataAccessException;
import model.Employee;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeDBTest {

    private static EmployeeDB employeeDB;
    private static int insertedId;

    @BeforeAll
    static void setup() throws Exception {
        employeeDB = new EmployeeDB();

        // Insert test employee manually because EmployeeDB has no insert method
        String sql = "INSERT INTO Employee (name, niveau) VALUES (?, ?)";

        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setString(1, "TestEmployee");
        stmt.setInt(2, 3);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            insertedId = rs.getInt(1);
        }
    }

    @Test
    @Order(1)
    void testFindById() throws DataAccessException {
        Employee emp = employeeDB.findById(insertedId);
        assertNotNull(emp);
        assertEquals("TestEmployee", emp.getName());
        assertEquals(3, emp.getNiveau());
    }

    @Test
    @Order(2)
    void testFindByName() throws DataAccessException {
        Employee emp = employeeDB.FindByName("TestEmployee");
        assertNotNull(emp);
        assertEquals(insertedId, emp.getEmployeeId());
    }

    @Test
    @Order(3)
    void testGetAllEmployees() throws DataAccessException {
        assertTrue(employeeDB.getAllEmployees().size() > 0);
    }

    @AfterAll
    static void cleanup() throws Exception {
        String sql = "DELETE FROM Employee WHERE employeeId = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, insertedId);
        stmt.executeUpdate();
    }
}
