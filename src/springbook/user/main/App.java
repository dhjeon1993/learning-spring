package springbook.user.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDao;

public class App {
	public static void main(String[] args) {
		ApplicationContext context1 =
				new AnnotationConfigApplicationContext(DaoFactory.class);
		ApplicationContext context2 =
				new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao1 = context1.getBean("userDao", UserDao.class);
		UserDao dao2 = context2.getBean("userDao", UserDao.class);

		System.out.println(dao1 == dao2);
		System.out.println("dao1 = " + dao1);
		System.out.println("dao2 = " + dao2);
	}
}
