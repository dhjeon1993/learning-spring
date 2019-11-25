package springbook.user.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao {
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) throws SQLException {
        this.jdbcTemplate.update("INSERT INTO users(id, name, password)" +
                "VALUES (?, ?, ?)"
                , user.getId()
                , user.getName()
                , user.getPassword()
        );
    }

    public User get(String id) throws SQLException {
        return this.jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                new Object[] {id},
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet resultSet, int i) throws SQLException {
                        User user = new User();
                        user.setId(resultSet.getString("id"));
                        user.setName(resultSet.getString("name"));
                        user.setPasswrod(resultSet.getString("password"));
                        return user;
                    }
                }
        );
    }

    // 테이블의 모든 레코드를 삭제
    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update("DELETE FROM users");
    }

    /**
     * 테이블의 전체 레코드 수를 리턴
     */
    public int getCount() throws SQLException {
        return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM users");
    }

    /**
     * 테이블의 전체 레코드를 리턴
     */
    public List<User> getAll() {
        return this.jdbcTemplate.query(
                "SELECT * FROM users ORDER BY id",
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet resultSet, int i) throws SQLException {
                        User user = new User();
                        user.setId(resultSet.getString("id"));
                        user.setName(resultSet.getString("name"));
                        user.setPasswrod(resultSet.getString("password"));
                        return user;
                    }
                }
        );
    }
}
