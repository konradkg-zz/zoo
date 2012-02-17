package zoo.persist.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zoo.persist.mybatis.bo.Role;
import zoo.persist.mybatis.dao.RoleMapper;

@Service
public class KbigDataManagementService {

	  @Autowired
	  private RoleMapper roleMapper;
	  
	  public List<Role> getRoleList() {
		  return roleMapper.getRoleList();
	  }
	
}
