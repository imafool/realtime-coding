package com.ssm.chapter16.service;

import java.util.List;

import com.ssm.chapter16.pojo.Role;

public interface RoleService {
	Role getRole(Long id);
	
	int updateRole(Role role);
	
	int updateRoleArr(List<Role> roleList);
	
}
