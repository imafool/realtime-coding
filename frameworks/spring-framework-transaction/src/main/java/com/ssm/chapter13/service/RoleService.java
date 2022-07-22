package com.ssm.chapter13.service;

import java.util.List;

import com.ssm.chapter13.pojo.Role;

public interface RoleService {
	
	int insertRole(Role role);
	
	int insertRoleList(List<Role> roleList);
	
	int insertRoleList2(List<Role> roleList);
}
