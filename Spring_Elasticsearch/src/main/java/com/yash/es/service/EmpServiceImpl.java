package com.yash.es.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.yash.es.dao.EmpDao;
import com.yash.es.model.Employee;

@Service
public class EmpServiceImpl implements EmpService {

	@Autowired
	Gson gson;

	@Autowired
	EmpDao empDao;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> insertEmployeeRecord(Employee employee) {

		// empDao.creteQuery(employee);
		Map<String, Object> response = null;
		//employee.setEmpId(employee.getEmpId().toString());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String jsonInString = gson.toJson(employee);

		dataMap = (Map<String, Object>) gson.fromJson(jsonInString, dataMap.getClass());
		response = empDao.insertQuery(employee, dataMap);
		return response;
	}

	@Override
	public Employee fetchEmployeeRecord(String empId, boolean flag) {
		// TODO Auto-generated method stub
		Employee employee;
		
		employee = empDao.fetchQuery(empId,flag);
		return employee;
		
	}

	@Override
	public Map<String, Object> updateEmployeeRecord(Employee employee, String empId) {
		Employee employeedetail;
		 Map<String,Object> response=null;
		boolean flag=true;
		employeedetail = empDao.fetchQuery(empId,flag);
		if(!StringUtils.isEmpty(employeedetail)) {
			String employeeJson=checkMap(employee);
		    response = empDao.updateQuery(empId, employeeJson);
		}else {
			employee.setEmpId(empId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			String jsonInString = gson.toJson(employee);

			dataMap = (Map<String, Object>) gson.fromJson(jsonInString, dataMap.getClass());
			if(!StringUtils.isEmpty(empId))
			response = empDao.insertQuery(employee, dataMap);
		}
		return response;
	}

	private String checkMap(Employee employee) {
		 Map<String,Object> headerMap=new HashMap<>();
		 Map<String,String> addressMap=new HashMap<>();
		 if(!Objects.isNull(employee.getEmpId()))
			 headerMap.put("EmpId", employee.getEmpId());
		 if(!Objects.isNull(employee.getEmpName()))
			 headerMap.put("EmpName", employee.getEmpName());
		 if(!Objects.isNull(employee.getCompanyName()))
			    headerMap.put("CompanyName", employee.getCompanyName());
		 if(!Objects.isNull(employee.getAddress()) && !Objects.isNull(employee.getAddress().getCountry()))
			 addressMap.put("Country", employee.getAddress().getCountry());
		 if(!Objects.isNull(employee.getAddress()) && !Objects.isNull(employee.getAddress().getState()))
			 addressMap.put("State", employee.getAddress().getState());
		 if(!Objects.isNull(employee.getAddress()) && !Objects.isNull(employee.getAddress().getCityName()))
			 addressMap.put("CityName", employee.getAddress().getCityName());
		 if(!Objects.isNull(employee.getAddress()) && !Objects.isNull(employee.getAddress().getPin())) 
			 addressMap.put("Pin", employee.getAddress().getPin());
		 if(!Objects.isNull(employee.getAddress()) && !Objects.isNull(employee.getAddress().getMobileNo()))
			 addressMap.put("MobileNo", employee.getAddress().getMobileNo());
		 if(!Objects.isNull(addressMap))  
			 headerMap.put("Address", addressMap);
		 String employeeJson = gson.toJson(headerMap);
		return employeeJson;
	}

	@Override
	public String deleteEmployeeRecord(String empId) {
		// TODO Auto-generated method stub
		String response = null;
		response = empDao.deleteQuery(empId);

		return response;
	}
	
	public List<Map<String, Object>> fetchQueryBuilder(Map<String, String> headerMap) {
		 
		 List<Map<String, Object>> response=empDao.matchQuery(headerMap);
		
		
		return response;
	}

	@Override
	public  List<Employee> fetchAllEmployee() {
		List<Employee> response=null;
		response=empDao.fetchAll();
	return response;	
	}

}
