package springbook.user.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.exceptions.DuplicateUserIdException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDaoJdbc implements UserDao{
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper =
            new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    User user = new User();
                    user.setId(resultSet.getString("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPasswrod(resultSet.getString("password"));
                    user.setLevel(Level.valueOf(resultSet.getInt("level")));
                    user.setLogin(resultSet.getInt("login"));
                    user.setRecommend(resultSet.getInt("recommend"));
                    return user;
                }
            };

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) {
        this.jdbcTemplate.update("INSERT INTO users(id, name, email, password, level, login, recommend)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)"
                , user.getId()
                , user.getName()
                , user.getEmail()
                , user.getPassword()
                , user.getLevel().intValue()
                , user.getLogin()
                , user.getRecommend()
        );
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                new Object[] {id},
                userMapper
        );
    }

    // 테이블의 모든 레코드를 삭제
    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM users");
    }

    /**
     * 테이블의 전체 레코드 수를 리턴
     */
    public int getCount() {
        return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM users");
    }

    /**
     * 테이블의 전체 레코드를 리턴
     */
    public List<User> getAll() {
        return this.jdbcTemplate.query(
                "SELECT * FROM users ORDER BY id",
                userMapper
        );
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(
                "UPDATE users SET name = ?, password = ?, level = ?, login = ?, "
                + "recommend = ? where id = ? ", user.getName(), user.getPassword()
                , user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
                user.getId()
        );
    }
}
