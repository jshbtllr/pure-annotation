package com.exercise9.core.service;
import com.exercise9.core.model.Roles;
import com.exercise9.core.model.Address;
import com.exercise9.core.model.ContactInfo;
import com.exercise9.core.model.Employee;
import com.exercise9.core.model.Name;
import com.exercise9.core.dao.EmployeeDAO;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=false)
public class EmployeeCrudServiceImpl implements CrudServiceInterface <Employee> {
	private Logger logger = Logger.getLogger(EmployeeCrudServiceImpl.class);

	@Autowired
	private EmployeeDAO employeeDao;

	public Employee create(Employee employee) {
		return employeeDao.add(employee);
	}	

	@Transactional(readOnly=true)
	public List <Employee> read(Integer sortFunction, Boolean ascending) {
		logger.info("List Employees");
		List <Employee> list = employeeDao.showEmployees(sortFunction, ascending);

		if(!list.isEmpty()) {
			if(sortFunction == 2) {
				Collections.sort(list, new gwaComparator());

				if(ascending == false) {
					Collections.sort(list, Collections.reverseOrder(new gwaComparator()));						
				}
			}
		}

		return list;
	}	

	public Employee delete(Long employeeId) {
		logger.info("Delete Employee");
		Employee employee = new Employee();
		employee = employeeDao.get(Employee.class, employeeId);
		return employeeDao.delete(employee);
	}

	public Employee update(Employee employee) {
		logger.info("Update Employee");
		return employeeDao.update(employee);
	}

	public Employee get(Long employeeId) {
		logger.info("Get Employee Details");
		Employee employee = new Employee();
		employee = employeeDao.get(Employee.class, employeeId);

		return employee;
	}
}

class gwaComparator implements Comparator <Employee> {
	public int compare(Employee a, Employee b) {
		return a.getGradeWeightAverage() < b.getGradeWeightAverage() ? -1 : a.getGradeWeightAverage() == b.getGradeWeightAverage() ? 0 : 1;
	}
}