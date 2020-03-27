package springbook.config;

import org.mariadb.jdbc.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
@ImportResource("/resources/config/test-applicationContext.xml")
public class TestApplicationContext {

    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl("jdbc:mysql://45.120.69.55/study_spring");
        dataSource.setUsername("study_admin");
        dataSource.setPassword("Tmxjel$&*$8266");

        return dataSource;
    }

}
