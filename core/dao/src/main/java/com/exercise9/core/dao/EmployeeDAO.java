package com.exercise9.core.dao;

import com.exercise9.core.model.Employee;
import com.exercise9.core.model.EmployeeGradeDTO;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDAO extends GenericDAOImpl <Employee> {
	public List <Employee> showEmployees(Integer sort, Boolean ascending) {
		Criteria criteria = null;
		List <Employee> list = null;
		
		criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class, "employee");
		if(sort == 1) {
			if(ascending == true) {
				criteria.addOrder(Order.asc("employee.name.lastName"));
			} else {
				criteria.addOrder(Order.desc("employee.name.lastName"));
			}
		} else if(sort == 3) {
			if(ascending == true) {
				criteria.addOrder(Order.asc("hireDate"));
			} else {
				System.out.println("Sorts by hiredate desc");
				criteria.addOrder(Order.desc("hireDate"));
			}
		} else if(sort == 4) {
			criteria.addOrder(Order.asc("id"));
		}

		list = criteria.list();	
		//if(order != 0) {	
		for ( Employee employee : list ) {
			Hibernate.initialize(employee.getRole());
			Hibernate.initialize(employee.getContactInfo());
		//}
		}	
		
		return list;				
	}	

	public Employee getEmployeeCollection(Long employeeId) {
		Employee employee = null;
		Criteria criteria = null;

		criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
		criteria.add(Restrictions.eq("id", employeeId));
		employee = (Employee) criteria.list().get(0);
		Hibernate.initialize(employee.getRole());
		Hibernate.initialize(employee.getContactInfo());
		
		return employee;
	}				

	public Boolean employeeCheck(Long employeeId) {
		Query query = null;
		Boolean present = false;

		query = sessionFactory.getCurrentSession().createQuery("SELECT id FROM Employee WHERE id = :employeeid");
		query.setParameter("employeeid", employeeId);

		present = !(query.list().isEmpty());
	
		return present;
	}
}