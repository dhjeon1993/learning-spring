package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.*;

public abstract class UserDao {
    public void add(User user) throws ClassNotFoundException, SQLException {
        // 관심사 1. 분리 완료
        Connection c = getConnection();

        // 관심사 2.
        // SQL 쿼리를 만들고 실행
        PreparedStatement ps = c.prepareStatement(
                "INSERT INTO users (id, name, password) VALUES(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        // 관심사 3.
        // 작업을 마치고 리소스 해제
        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        // 관심사 1. 분리 완료
        Connection c = getConnection();

        PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM users WHERE id = ?"
        );

        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPasswrod(rs.getString("password"));

        rs.close();
        c.close();

        return user;
    }

    // 관심사 1. DB 연결 과정
    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
