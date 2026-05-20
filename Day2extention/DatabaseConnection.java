import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static Connection connect() {

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con =
                    DriverManager.getConnection(
                            "jdbc:oracle:thin:@localhost:1521:xe",
                            "system",
                            "oracle"
                    );

            return con;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}