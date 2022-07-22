package com.ssm.chapter5.mapper;

import com.ssm.chapter5.param.PdCountRoleParams;
import com.ssm.chapter5.param.PdFindRoleParams;

public interface PdRoleMapper {

	void countRole(PdCountRoleParams pdCountRoleParams);
	
	void findRole(PdFindRoleParams pdFindRoleParams);
}
