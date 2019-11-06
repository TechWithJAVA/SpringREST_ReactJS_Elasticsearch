package com.yash.es.dao;

import java.util.List;
import java.util.Map;


import com.yash.es.model.Employee;


public interface EmpDao {

	public Map<String, Object> insertQuery(Employee employee, Map<String, Object> dataMap);

	public Employee fetchQuery(String empId, boolean flag);


	public Map<String, Object> updateQuery(String empId, String employeeJson);

	public String deleteQuery(String empId);

	public List<Map<String, Object>> matchQuery(Map<String, String> headerMap);

	public  List<Employee> fetchAll();
	

	
}
