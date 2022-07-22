package com.ssm.chapter5.mapper;

import com.ssm.chapter5.pojo.Employee;

public interface EmployeeMapper {

	Employee getEmployee(Long id);
	
	Employee getEmployee2(Long id);
}
