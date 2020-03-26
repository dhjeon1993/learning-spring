package springbook.user.test;

import org.junit.Before;
import org.junit.Test;
import springbook.issuetracker.sqlservice.UpdatableSqlRegistry;
import springbook.user.sql.ConcurrentHashMapSqlRegistry;
import springbook.user.sql.SqlNotFoundException;
import springbook.user.sql.SqlUpdateFailureException;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest{
    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        return new ConcurrentHashMapSqlRegistry();
    }
}
