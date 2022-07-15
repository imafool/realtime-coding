package com.ssm.chapter5.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ssm.chapter5.param.PageParams;
import com.ssm.chapter5.param.RoleParams;
import com.ssm.chapter5.pojo.Role;

public interface RoleMapper {
	
	Role getRole(Long id);
	
	List<Role> findRolesByMap(Map<String, Object> parameterMap);
	
	List<Role> findRolesByAnnotation(@Param("roleName") String rolename, @Param("note") String note);
	
	List<Role> findRolesByBean(RoleParams roleParam);
	
	List<Role> findByMix(@Param("params") RoleParams roleParams, @Param("page") PageParams PageParam);
	
	Role getRoleUseResultMap(Long id);
	
	int insertRole(Role role);
	
	int insertRole2(Role role);
	
	int updateRole(Role role);
	
	int deleteRole(Long id);
}
