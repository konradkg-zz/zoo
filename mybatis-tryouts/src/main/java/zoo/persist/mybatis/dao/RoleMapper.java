package zoo.persist.mybatis.dao;

import java.util.List;

import zoo.persist.mybatis.bo.Role;

public interface RoleMapper {

	  List<Role> getRoleList();

	  Role getRole(int roleId);
	
}
