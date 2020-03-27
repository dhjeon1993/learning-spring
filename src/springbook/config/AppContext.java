package springbook.config;

import org.mariadb.jdbc.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springbook.user.dao.UserDao;
import springbook.user.service.DummyMailSender;
import springbook.user.service.UserService;
import springbook.user.service.UserServiceTest;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "springbook.user")
@EnableTransactionManagement
@PropertySource("/config/database.properties")
@Import({SqlServiceContext.class})
//@ImportResource("/resources/config/test-applicationContext.xml")
public class AppContext {

    @Autowired
    private UserDao userDao;

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        try {
            dataSource.setDriverClass((Class<? extends java.sql.Driver>)Class.forName(env.getProperty("db.driver.class")));
        } catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager =
                new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Configuration
    @Profile("production")
    public static class ProductionAppContext {
        @Bean
        public MailSender mailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("localhost");
            return mailSender;
        }
    }

    @Configuration
    @Profile("test")
    public static class TestAppContext {
        @Bean
        public UserService testUserService() {
            return new UserServiceTest.TestUserServiceImpl();
        }

        @Bean
        public MailSender mailSender() {
            return new DummyMailSender();
        }
    }
}
