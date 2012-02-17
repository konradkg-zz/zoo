package zoo.persist.mybatis;

import java.util.Date;
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
	public void listRoles() throws Exception {
		final List<Role> roles = managementService.getRoleList();
		Assert.assertNotNull(roles);
		Assert.assertTrue(roles.size() > 0);
		Assert.assertNotNull(roles.get(0));
		
	}
	
	@Test
	public void insertRole() throws Exception {
		final Role r = new Role();
		r.setActive(true);
		final Date now = new Date();
		r.setDateInserted(now);
		r.setDateModified(now);
		r.setDescription("test role description");
		r.setName("test role");
		
		managementService.insertRole(r);
		//id got updated
		
		final List<Role> roles = managementService.getRoleList();
		for(Role rol : roles) {
			if(rol.getId().equals(r.getId()))
				return;
		}
		
		Assert.fail();
	}

}
