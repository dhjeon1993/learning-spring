package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.sql.DataSource;

// 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource =
                new SimpleDriverDataSource();
        dataSource.setDriverClass(org.mariadb.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/learning_spring");
        dataSource.setUsername("root");
        dataSource.setPassword("jdh0412");

        return dataSource;
    }
}
