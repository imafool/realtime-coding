package com.ssm.chapter8.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssm.chapter8.params.PageParams;
import com.ssm.chapter8.pojo.Role;

public interface RoleMapper {
	
	Role getRole(Long id);

	List<Role> findRole(@Param("pageParams") PageParams pageParams, @Param("roleName") String roleName);
}