package zoo.persist.mybatis;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import zoo.persist.mybatis.bo.Role;
import zoo.persist.mybatis.service.KbigDataManagementService;


@ContextConfiguration({"classpath:/META-INF/spring/hsql-test-kbig-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleKbigHsqlTestCase {
	
	@Autowired
	private KbigDataManagementService managementService;

	@Test
	public void simpleTest() throws Exception {
		final List<Role> roles = managementService.getRoleList();
		Assert.assertNotNull(roles);
		Assert.assertTrue(roles.size() > 0);
		
	}

}
