package springbook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
import springbook.user.sql.SqlMapConfig;
import springbook.user.sql.annotation.EnableSqlService;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
@PropertySource("classpath:config/database.properties")
@ComponentScan(basePackages = "springbook.user")
@EnableTransactionManagement
@EnableSqlService
//@Import({SqlServiceContext.class})
//@ImportResource("/resources/config/test-applicationContext.xml")
public class AppContext implements SqlMapConfig {

    @Autowired
    private UserDao userDao;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    private Class<? extends Driver> driverClass;

    private String url;

    private String username;

    private String password;

    @Bean
    protected String dataSourceParams(
            @Value("${db.driver.class}")
            Class<? extends Driver> driverClass,

            @Value("${db.url}")
            String url,

            @Value("${db.username}")
            String username,

            @Value("${db.password}")
            String password
    ) {
        this.driverClass = driverClass;
        this.url = url;
        this.username = username;
        this.password = password;
        return "dataSourceParams";
    }

    @Bean
    @DependsOn("dataSourceParams")
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setDriverClass(this.driverClass);

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager =
                new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Override
    public Resource getSqlMapResource() {
        return new ClassPathResource("sqlmap.xml", UserDao.class);
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
