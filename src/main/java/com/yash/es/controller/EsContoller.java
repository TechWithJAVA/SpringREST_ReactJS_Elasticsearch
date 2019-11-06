package com.yash.es.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yash.es.model.Employee;
import com.yash.es.service.EmpService;

@RestController
@RequestMapping("/employees")
public class EsContoller {

	@Autowired
	EmpService empService;

	@PostMapping("/create")
	public Map<String, Object> createEmployee(@RequestBody Employee employee) throws Exception {
			Map<String, Object> response =null;
		if(!StringUtils.isEmpty(employee.getEmpId()))
		response= empService.insertEmployeeRecord(employee);
		    return response;
			
	}
	
	@GetMapping("fetch/fetchAll")
	public List<Employee> fetchAll() {
		List<Employee> response=null;
		response=empService.fetchAllEmployee();
		return response;
	}

	@GetMapping("/retrive/{empId}")
	public Employee retriveEmployee(@PathVariable String empId) {
		boolean flag=true;
		Employee employee =empService.fetchEmployeeRecord(empId,flag);
		return employee;
	}

	@GetMapping("/retriveQuery/{empId}")
	public Employee retriveEmployeeQuery(@PathVariable String empId) {
        boolean flag=false;
        Employee employee =empService.fetchEmployeeRecord(empId,flag);
		return employee;
	}
	@GetMapping("/requestSearch")
	public Employee requestSearch(@RequestParam("EmpId") String empId){
		 boolean flag=false;
		 Employee employee =empService.fetchEmployeeRecord(empId,flag);
		return employee;
	}
	@PostMapping("/multiSearch")
	public List<Map<String, Object>> multiSearch(@RequestBody Employee employee){
		 Map<String,String> headerMap=new HashMap<>();
		 if(!Objects.isNull(employee.getEmpId()))
			 headerMap.put("EmpId", employee.getEmpId());
		/* if(!(employee.getEmpId()==null))
		 headerMap.put("EmpId", employee.getEmpId());*/
		 
		 if(!Objects.isNull(employee.getEmpName()))
			 headerMap.put("EmpName", employee.getEmpName());
		/* if(!(employee.getEmpName()==null))
		 headerMap.put("EmpName", employee.getEmpName());*/
		 
		 if(!Objects.isNull(employee.getCompanyName()))
		    headerMap.put("CompanyName", employee.getCompanyName());
		
		 /*if(!(employee.getCompanyName()==null))
	     headerMap.put("CompanyName", employee.getCompanyName());*/
		
		 if(!Objects.isNull(employee.getAddress()) && !Objects.isNull(employee.getAddress().getCountry()))
		    headerMap.put("Address.Country", employee.getAddress().getCountry());
		/* if(!(employee.getAddress()==null) && !(employee.getAddress().getCountry()==null))
		 headerMap.put("Address.Country", employee.getAddress().getCountry());*/
		
		 if(!Objects.isNull(employee.getAddress()) && !Objects.isNull(employee.getAddress().getState()))
			headerMap.put("Address.State", employee.getAddress().getState());
		/* if(!(employee.getAddress()==null) && !(employee.getAddress().getState()==null))
		 headerMap.put("Address.State", employee.getAddress().getState());*/
		
		 if(!Objects.isNull(employee.getAddress()) && !Objects.isNull(employee.getAddress().getCityName()))
			 headerMap.put("Address.CityName", employee.getAddress().getCityName());
		/*if(!(employee.getAddress()==null) &&!(employee.getAddress().getCityName()==null))
			 headerMap.put("Address.CityName", employee.getAddress().getCityName());*/
		 if(!Objects.isNull(employee.getAddress()) && !Objects.isNull(employee.getAddress().getPin())) 
			 headerMap.put("Address.Pin", employee.getAddress().getPin());
		 /*if(!(employee.getAddress()==null) &&!(employee.getAddress().getPin()==null))
		 headerMap.put("Address.Pin", employee.getAddress().getPin());*/
		 if(!Objects.isNull(employee.getAddress()) && !Objects.isNull(employee.getAddress().getMobileNo()))
			 headerMap.put("Address.MobileNo", employee.getAddress().getMobileNo());
		/* if(!(employee.getAddress()==null) &&!(employee.getAddress().getMobileNo()==null))
		 headerMap.put("Address.MobileNo", employee.getAddress().getMobileNo());*/
		// System.out.println(headerMap);
		List<Map<String, Object>> response= empService.fetchQueryBuilder(headerMap);
	
		return response;
	}
	
	@PutMapping("/update/{empId}")
	public Map<String, Object> updateEmployee(@RequestBody Employee employee, @PathVariable String empId){

		Map<String, Object> response=empService.updateEmployeeRecord(employee, empId);
		
		return response;

	}
	
	@DeleteMapping("/delete/{empId}")
	public String deleteEmployee(@PathVariable String empId) throws Exception {

		String response = empService.deleteEmployeeRecord(empId);
		return response;
	}
}
