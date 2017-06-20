package com.exercise9.app;

import com.exercise9.core.model.Employee;
import com.exercise9.core.model.Name;
import com.exercise9.core.model.Address;
import com.exercise9.core.model.Roles;
import com.exercise9.core.model.ContactInfo;
import com.exercise9.core.service.EmployeeCrudServiceImpl;
import com.exercise9.core.service.RoleCrudServiceImpl;
import com.exercise9.core.service.EmployeeRoleServiceImpl;
import com.exercise9.core.service.ContactInfoServiceImpl;
import com.exercise9.util.InputUtil;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Date;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeActionsController {
	private Logger logger = Logger.getLogger(EmployeeActionsController.class);

	@Autowired
	private EmployeeCrudServiceImpl employeeService;

	@Autowired
	private RoleCrudServiceImpl roleService;
	
	@Autowired
	private ContactInfoServiceImpl contactInfoService;

	@Autowired
	private EmployeeRoleServiceImpl employeeRoleService;

	private static final Integer sortById = new Integer(1);
	private static final Boolean ascending = true;


	@RequestMapping(value="/employee/update", method=RequestMethod.GET)
	public ModelAndView showUpdateForm(@RequestParam("employeeId") String id) {
		logger.info("Employee Controller update employee");

		Long employeeId = Long.parseLong(id);
		Employee employee = employeeService.get(employeeId);
		Set <Roles> currentRoles = employeeRoleService.getCurrentRoles(employeeId);
		List <Roles> roleList = roleService.read(sortById, ascending);
		Set <ContactInfo> contacts = contactInfoService.getCurrentContacts(employeeId);

		ModelAndView modelAndView = new ModelAndView("employeeupdateform");
		modelAndView.addObject("employee", employee);
		modelAndView.addObject("currentRoles", currentRoles);
		modelAndView.addObject("roleList", roleList);
		modelAndView.addObject("contacts", contacts);

		return modelAndView;
	}

	@RequestMapping(value="employee/update", method=RequestMethod.POST)
	public ModelAndView updateEmployee(HttpServletRequest request) {
		logger.info("Employee Update Controller");
		Set <ContactInfo> contacts = new HashSet <ContactInfo>();
		Set <Roles> role = new HashSet <Roles>();
		Employee employee = null;
		ContactInfo info = null;		
		Long employeeId = Long.parseLong(request.getParameter("employeeId"));
		String title = request.getParameter("title");
		String firstName = request.getParameter("firstName");
		String middleName = request.getParameter("middleName");
		String lastName = request.getParameter("lastName");
		String suffix = request.getParameter("suffix");
		String streetNumber = request.getParameter("streetNumber");
		String barangay = request.getParameter("barangay");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String zipcode = request.getParameter("zipcode");
		Boolean employed = Boolean.parseBoolean(request.getParameter("employed"));
		String birth = request.getParameter("birthday");
		String hire = request.getParameter("hireDate");
	    Float gradeWeightAverage = null;
	    Date birthdate = null;
	    Date hireDate = null;
		String infoType = request.getParameter("infoType");
	    String infoDetail = request.getParameter("infoDetail");	   
	    String [] listRoles  = request.getParameterValues("roles");
		Boolean gradeFlag = false;
		Boolean hireFlag = false;
		Boolean successFlag = true;
		Boolean birthFlag = false;
		Boolean contactFlag = false;		
		StringBuilder message = null;  
		List <String> addedRole = null;

		Address address = new Address(streetNumber, barangay, city, country, zipcode);
		Name name = new Name(firstName, lastName, middleName, suffix, title);

		if(listRoles != null) {
			addedRole = Arrays.asList(listRoles);
		}

	    try {
	    	gradeWeightAverage = Float.parseFloat(request.getParameter("gwa"));
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
	    	if(InputUtil.checkDate(hire)) {
	    		hireDate = InputUtil.getDate(hire);
	    	} else {
	    		hireFlag = true;
	    		successFlag = false;
	    	}
	    } else {
	    	hireDate = InputUtil.getDate("31/12/9999");
	    }

	    if(InputUtil.checkDate(birth)) {
	    	birthdate = InputUtil.getDate(birth);
	    } else {
	    	birthFlag = true;
	    	successFlag = false;
	    }

	    info = new ContactInfo(infoType, infoDetail);
	    if(!info.getInfoDetail().equals("")) {
	    	info = contactInfoService.checkInfo(info);
		    if(info.getInfoType().equals(" ")) {
		    	contactFlag = true;
		    	successFlag = false;
		    } else {
		 	   contacts.add(info);    	
			}
	    }

	    if(addedRole != null) {
	    	for(String add : addedRole) {
	    		Long roleId = Long.parseLong(add);
	    		Roles in = roleService.get(roleId);
	    		role.add(in);
	    	}
	    }

	    if(successFlag == true) {
	    	employee = new Employee(name, address, birthdate, gradeWeightAverage, hireDate, employed, 
					contacts, role);

	    	employee.setId(employeeId);
	    	employeeService.update(employee);	    	
	    }

	    message = getMessage(successFlag, gradeFlag, hireFlag, birthFlag, contactFlag);

		ModelAndView modelAndView = new ModelAndView("message");
		modelAndView.addObject("message", message);
		modelAndView.addObject("redirectView", "http://localhost:8080/employee");

		return modelAndView;
	}

	@RequestMapping(value="/employee/add", method=RequestMethod.GET)
	protected ModelAndView showAddForm() {
		logger.info("Add Employee");
		List <Roles> roleList = roleService.read(sortById, ascending);
		ModelAndView modelAndView = new ModelAndView("employeeaddform");
		modelAndView.addObject("roleList", roleList);

		return modelAndView;
	}	

	@RequestMapping(value="/employee/add", method=RequestMethod.POST)
	protected ModelAndView addEmployee(HttpServletRequest request) {
		logger.info("Add Employee");
		Set <ContactInfo> contacts = new HashSet <ContactInfo>();
		Set <Roles> role = new HashSet <Roles>();
		Employee employee = null;
		ContactInfo info = null;		
		String title = request.getParameter("title");
		String firstName = request.getParameter("firstName");
		String middleName = request.getParameter("middleName");
		String lastName = request.getParameter("lastName");
		String suffix = request.getParameter("suffix");
		String streetNumber = request.getParameter("streetNumber");
		String barangay = request.getParameter("barangay");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String zipcode = request.getParameter("zipcode");
		Boolean employed = Boolean.parseBoolean(request.getParameter("employed"));
		String birth = request.getParameter("birthday");
		String hire = request.getParameter("hireDate");
	    Float gradeWeightAverage = null;
	    Date birthdate = null;
	    Date hireDate = null;
		String infoType = request.getParameter("infoType");
	    String infoDetail = request.getParameter("infoDetail");	   
	    String [] listRoles  = request.getParameterValues("roles");
		Boolean gradeFlag = false;
		Boolean hireFlag = false;
		Boolean successFlag = true;
		Boolean birthFlag = false;
		Boolean contactFlag = false;		
		StringBuilder message = null;  
		List <String> addedRole = null;  
		Address address = new Address(streetNumber, barangay, city, country, zipcode);
		Name name = new Name(firstName, lastName, middleName, suffix, title);

		if(listRoles != null) {
			addedRole = Arrays.asList(listRoles);
		}

	    try {
	    	gradeWeightAverage = Float.parseFloat(request.getParameter("gwa"));
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
	    	if(InputUtil.checkDate(hire)) {
	    		hireDate = InputUtil.getDate(hire);
	    	} else {
	    		hireFlag = true;
	    		successFlag = false;
	    	}
	    } else {
	    	hireDate = InputUtil.getDate("31/12/9999");
	    }

	    if(InputUtil.checkDate(birth)) {
	    	birthdate = InputUtil.getDate(birth);
	    } else {
	    	birthFlag = true;
	    	successFlag = false;
	    }

	    info = new ContactInfo(infoType, infoDetail);
	    if(!info.getInfoDetail().equals("")) {
	    	info = contactInfoService.checkInfo(info);
		    if(info.getInfoType().equals(" ")) {
		    	contactFlag = true;
		    	successFlag = false;
		    } else {
		 	   contacts.add(info);    	
			}
	    }

	    if(addedRole != null) {
	    	for(String add : addedRole) {
	    		Long roleId = Long.parseLong(add);
	    		Roles in = roleService.get(roleId);
	    		role.add(in);
	    	}
	    }

	    if(successFlag == true) {
	    	employee = new Employee(name, address, birthdate, gradeWeightAverage, hireDate, employed, 
					contacts, role);

	    	employeeService.create(employee);
	    }

	    message = getMessage(successFlag, gradeFlag, hireFlag, birthFlag, contactFlag);

		ModelAndView modelAndView = new ModelAndView("message");
		modelAndView.addObject("message", message);
		modelAndView.addObject("redirectView", "http://localhost:8080/employee");

		return modelAndView;
	}	

	public StringBuilder getMessage(Boolean successFlag, Boolean gradeFlag, Boolean hireFlag, Boolean birthFlag, Boolean contactFlag) {
		StringBuilder message = new StringBuilder();  
	    
	    if(successFlag == true) {
	    	message.append("Success");
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

	    	message.append("Action failed");
	    }

	    return message;		
	}

}