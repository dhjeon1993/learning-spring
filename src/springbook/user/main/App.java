package springbook.user.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDaoJdbc;

public class App {
	public static void main(String[] args) {
		ApplicationContext context1 =
				new AnnotationConfigApplicationContext(DaoFactory.class);
		ApplicationContext context2 =
				new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDaoJdbc dao1 = context1.getBean("userDao", UserDaoJdbc.class);
		UserDaoJdbc dao2 = context2.getBean("userDao", UserDaoJdbc.class);

		System.out.println(dao1 == dao2);
		System.out.println("dao1 = " + dao1);
		System.out.println("dao2 = " + dao2);
	}
}
