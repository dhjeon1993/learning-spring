package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DUserDao extends UserDao {
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        Connection c =
                DriverManager.getConnection(
                        "jdbc:mysql://localhost/learning_spring",
                        "root",
                        "jdh0412"
                );
        return c;
    }
}
