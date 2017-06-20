package com.exercise9.app;

import com.exercise9.core.model.Employee;
import com.exercise9.core.model.Name;
import com.exercise9.core.model.Address;
import com.exercise9.core.model.Roles;
import com.exercise9.core.model.ContactInfo;
import com.exercise9.core.service.EmployeeCrudServiceImpl;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeController {
	private Logger logger = Logger.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeCrudServiceImpl employeeService;

	private static final Integer sortByName = new Integer(1);
	private static final Integer sortByGrade = new Integer(2);
	private static final Integer sortByHire = new Integer(3);
	private static final Integer sortById = new Integer(4);
	private static final Boolean ascending = true;
	private static final Boolean descending = false;

	@RequestMapping(value="/employee", method=RequestMethod.GET)
	public ModelAndView showEmployee(@RequestParam(required=false) String sort, @RequestParam(required=false) String order) {
		logger.info("Employee Controller List Employees");

		Integer sortType = null;
		Boolean orderType = null;

		if(sort == null) {
			sortType = sortById;
		} else {
			if(sort.equals("lastname")) {
				sortType = sortByName;
			} else if(sort.equals("gwa")) {
				sortType = sortByGrade;
			} else if(sort.equals("hiredate")) {
				sortType = sortByHire;
			}
		}

		if((order == null) || (order.equals("ascending"))) {
			orderType = ascending;
		} else {
			orderType = descending;
		}

		ModelAndView modelAndView = new ModelAndView("home");
		List <Employee> employeeList = employeeService.read(sortType, orderType);
		modelAndView.addObject("employees",employeeList);

		return modelAndView;
	}

	@RequestMapping(value="/employee", method=RequestMethod.POST)
	public ModelAndView deleteEmployee(@RequestParam("employeeId") String id) {
		logger.info("Employee Controller delete employee");
		
		Long employeeId = Long.parseLong(id);
		Employee employee = employeeService.delete(employeeId);

		ModelAndView modelAndView = new ModelAndView("home");
		List <Employee> employeeList = employeeService.read(sortById, ascending);
		modelAndView.addObject("employees", employeeList);

		return modelAndView;
	}
}