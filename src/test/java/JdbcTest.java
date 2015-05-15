import java.sql.*;

public class JdbcTest {
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "hr";
    private static final String DB_PASSWORD = "hr";

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";

    public static void main(String[] args) {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your JDBC Driver?");
            System.out.println(e.getMessage());
        }
        try(Connection conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD)) {
            System.out.println(conn.isClosed());
            try(Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Products")) {
                while (rs.next()) {
                    System.out.println(rs.getString(1) + ", " + rs.getString(2));
                }
            }
        } catch (SQLException e) {
            System.out.println("No!");
            e.printStackTrace();
        }
    }
}
