package springbook.user.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/resources/config/test-applicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserService userSerivce;

    @Test
    public void bean() {
        assertThat(this.userSerivce, is(notNullValue()));
    }
}
