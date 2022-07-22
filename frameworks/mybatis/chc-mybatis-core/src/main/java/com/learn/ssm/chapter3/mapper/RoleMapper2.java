package com.learn.ssm.chapter3.mapper;

import org.apache.ibatis.annotations.Select;

import com.learn.ssm.chapter3.pojo.Role;

//×¢½â·½Ê½Ó³ÉäÆ÷
public interface RoleMapper2 {
	
	@Select("select id, role_name as roleName, note from t_role where id=#{id}")
	public Role getRole(Long id);
}
