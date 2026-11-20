package HospitalSystem;
import java.sql.*;

public class Conn {
    Connection c;
    Statement s;

    public Conn() {
        try {
            // 1. Driver Register karein
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. Connection String (Database name 'hospital_db' maan kar)
            c = DriverManager.getConnection("jdbc:mysql:///HospitalSystem", "root", "vishal");
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}