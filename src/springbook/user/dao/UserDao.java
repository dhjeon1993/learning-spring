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

    public void add(final User user) throws SQLException {
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps =
                        c.prepareStatement(
                                "INSERT INTO users(id, name, password)" +
                                        "VALUES(?, ?, ?)"
                        );
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        });
    }

    public User get(String id) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement(
                    "SELECT * FROM users WHERE id = ?"
            );

            ps.setString(1, id);

            rs = ps.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPasswrod(rs.getString("password"));
            }

            if (user == null) {
                throw new EmptyResultDataAccessException(1);
            }

            return user;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }

            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    // 테이블의 모든 레코드를 삭제
    public void deleteAll() throws SQLException {
        // 전략 패턴으로 분리
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement(
                        "DELETE FROM users"
                );
                return ps;
            }
        });
    }

    /**
     * 테이블의 전체 레코드 수를 리턴
     */
    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();

            ps = c.prepareStatement(
                    "SELECT COUNT(*) FROM users"
            );

            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {}
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {}
            }

            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();

            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {try {ps.close();} catch (SQLException e) {}}
            if (c != null) {try {c.close();} catch (SQLException e) {}}
        }
    }
}
