package com.ssm.chapter5.mapper;

import com.ssm.chapter5.pojo.EmployeeTask;

public interface EmployeeTaskMapper {

	EmployeeTask getEmployeeTaskByEmpId(Long empId);
}
