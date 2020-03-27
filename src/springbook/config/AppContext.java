package springbook.config;

import org.mariadb.jdbc.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springbook.user.dao.UserDao;
import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "springbook.user")
@EnableTransactionManagement
@Import({SqlServiceContext.class, TestAppContext.class, ProductionAppContext.class})
//@ImportResource("/resources/config/test-applicationContext.xml")
public class AppContext {

    @Autowired
    private UserDao userDao;

    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl("jdbc:mysql://45.120.69.55/study_spring");
        dataSource.setUsername("study_admin");
        dataSource.setPassword("Tmxjel$&*$8266");

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager =
                new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

}
