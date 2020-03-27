package springbook.user.sql.annotation;

import org.springframework.context.annotation.Import;
import springbook.config.SqlServiceContext;

@Import(value= SqlServiceContext.class)
public @interface EnableSqlService {
}
