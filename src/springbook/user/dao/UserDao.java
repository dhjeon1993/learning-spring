package springbook.user.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    // 관심사 분리. 독립된 클래스로 DB 연결 작업 수행
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        // 관심사 1. 분리 완료
        Connection c = dataSource.getConnection();

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

    public User get(String id) throws SQLException {
        // 관심사 1. 분리 완료
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM users WHERE id = ?"
        );

        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        User user = null;
        if (rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPasswrod(rs.getString("password"));
        }

        rs.close();
        c.close();

        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return user;
    }

    // 테이블의 모든 레코드를 삭제
    public void deleteAll() throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "DELETE FROM users"
        );

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    /**
     * 테이블의 전체 레코드 수를 리턴
     */
    public int getCount() throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "SELECT COUNT(*) FROM users"
        );

        ResultSet rs = ps.executeQuery();
        rs.next();

        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }
}
