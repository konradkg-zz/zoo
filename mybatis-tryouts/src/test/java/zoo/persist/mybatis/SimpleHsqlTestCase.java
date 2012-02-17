package zoo.persist.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration({"classpath:/META-INF/spring/hsql-test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleHsqlTestCase {

    @Test
    public void simpleTest() throws Exception {
	
    }
    
}
