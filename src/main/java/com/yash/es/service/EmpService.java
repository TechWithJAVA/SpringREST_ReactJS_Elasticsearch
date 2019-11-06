package com.yash.es.service;

import java.util.List;
import java.util.Map;

import com.yash.es.model.Employee;


public interface EmpService {

	public Map<String, Object> insertEmployeeRecord(Employee Employee);
	
	public Employee fetchEmployeeRecord(String empId, boolean flag);
	
	public Map<String, Object> updateEmployeeRecord(Employee Employee, String empId);
	
	public String deleteEmployeeRecord(String empId);

	public List<Map<String, Object>> fetchQueryBuilder(Map<String, String> headerMap);

	public  List<Employee> fetchAllEmployee();
		
	
}
