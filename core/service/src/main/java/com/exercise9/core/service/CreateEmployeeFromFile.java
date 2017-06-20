package com.exercise9.core.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import com.exercise9.core.model.Employee;
import com.exercise9.core.model.Name;
import com.exercise9.core.model.Address;
import com.exercise9.core.model.ContactInfo;
import com.exercise9.core.model.Roles;
import com.exercise9.util.InputUtil;
import com.exercise9.core.service.EmployeeCrudServiceImpl;
import com.exercise9.core.service.RoleCrudServiceImpl;
import com.exercise9.core.service.EmployeeRoleServiceImpl;
import com.exercise9.core.service.ContactInfoServiceImpl;

import org.apache.log4j.Logger;
import org.apache.commons.io.IOUtils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=false)
public class CreateEmployeeFromFile {
	private Logger logger = Logger.getLogger(CreateEmployeeFromFile.class);

	@Autowired
	private ContactInfoServiceImpl contactInfoService;

	@Autowired
	private RoleCrudServiceImpl roleService;

	@Autowired
	private EmployeeRoleServiceImpl employeeRoleService;

	@Autowired
	private EmployeeCrudServiceImpl employeeService;

	public StringBuilder parseFile(MultipartFile file) {
		logger.info("Parsing the File");
		Boolean gradeFlag = false;
		Boolean hireFlag = false;
		Boolean successFlag = true;
		Boolean birthFlag = false;
		Boolean contactFlag = false;	
		Boolean incompleteFlag = false;
		Employee employee = null;
		ContactInfo info = null;
		Name name = null;
		Address address = null; 
		Float gradeWeightAverage = null;
		Set <ContactInfo> contacts = new HashSet <ContactInfo>();
		Set <Roles> role = new HashSet <Roles>();
		Boolean employed = null;
		Date hireDate = null;
		Date birthdate = null;
		StringBuilder message = new StringBuilder();  
		String fileContents = null;
		
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes()); /* file into bytes into inputstream */
			fileContents = IOUtils.toString(inputStream, "UTF-8");			/* turns inputstream to string*/
		} catch(NullPointerException | IOException npe) {
			npe.printStackTrace();
		}

		List <String> employeeDetails = Arrays.asList(fileContents.split("\n")); /* separate filecontents to List of strings */

		if(employeeDetails.size() != 16) {
			incompleteFlag = true;
			message.append("File Lacks Information");
			return message;
		}

		name = new Name(employeeDetails.get(1), employeeDetails.get(2), employeeDetails.get(3), employeeDetails.get(4), employeeDetails.get(0));
		address = new Address(employeeDetails.get(5), employeeDetails.get(6), employeeDetails.get(7), employeeDetails.get(8), employeeDetails.get(9));		
		employed = Boolean.parseBoolean(employeeDetails.get(12));	

	    try {
	    	gradeWeightAverage = Float.parseFloat(employeeDetails.get(11));
	    } catch (NumberFormatException nfe) {
	    	gradeFlag = true;
	    	successFlag = false;
	    }

	    if(gradeFlag != true) {
	    	if(!InputUtil.checkGrade(gradeWeightAverage)) {
	    		gradeFlag = true;
	    		successFlag = false;
	    	}
	    }

	    if(employed) {
	    	if(InputUtil.checkDate(employeeDetails.get(13))) {
	    		hireDate = InputUtil.getDate(employeeDetails.get(13));
	    	} else {
	    		hireFlag = true;
	    		successFlag = false;
	    	}
	    } else {
	    	hireDate = InputUtil.getDate("31/12/9999");
	    }

	    if(InputUtil.checkDate(employeeDetails.get(10))) {
	    	birthdate = InputUtil.getDate(employeeDetails.get(10));
	    } else {
	    	birthFlag = true;
	    	successFlag = false;
	    }

	    List <String> contactDetails = Arrays.asList(employeeDetails.get(14).split(","));

	    info = new ContactInfo(contactDetails.get(0), contactDetails.get(1));
	    if(!info.getInfoDetail().equals("")) {
	    	info = contactInfoService.checkInfo(info);
		    if(info.getInfoType().equals(" ")) {
		    	contactFlag = true;
		    	successFlag = false;
		    } else {
		 	   contacts.add(info);    	
			}
	    }

	    List <String> addedRole = Arrays.asList(employeeDetails.get(15).split(","));

	    if(successFlag == true) {
	    	for(String add : addedRole) {
	    		Long roleId = Long.parseLong(add);
	    		Roles in = roleService.get(roleId);
	    		role.add(in);
	    	}
	    	employee = new Employee(name, address, birthdate, gradeWeightAverage, hireDate, employed, 
					contacts, role);

	    	employeeService.create(employee);
	    	message.append("Employee Successfully added");
	    } else {
	    	if(gradeFlag) {
	    		message.append("Invalid GWA input<br/>");
	    	}
	    	if (hireFlag) {
	    		message.append("Invalid Hire Date<br/>");
	    	}
	    	if(birthFlag) {
	    		message.append("Invalid Birthdate<br/>");
	    	}
	    	if(contactFlag) {
	    		message.append("Invalid Contact Information<br/>");
	    	}

	    	message.append("Employee not added");
	    }
		return message;
	}

}