package com.exercise9.core.service;

import com.exercise9.core.model.Roles;
import com.exercise9.core.model.Employee;
import com.exercise9.util.InputUtil;
import com.exercise9.core.dao.RoleDAO;
import com.exercise9.core.dao.EmployeeDAO;

import java.util.Set;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=false)
public class EmployeeRoleServiceImpl implements EmployeeRoleServiceInterface {
	private Logger logger = Logger.getLogger(EmployeeRoleServiceImpl.class);

	@Autowired
	private EmployeeDAO employeeDao;

	@Autowired
	private RoleDAO roleDao;

	public Integer addRemoveEmployeeRoles(Integer option, Long employeeId, Long roleId) {			/*Option 1 add, Option 2 remove*/
		Employee employee = null;
		Set <Roles> employeeRoles;
		Integer roleCount = null;

		employee = employeeDao.getEmployeeCollection(employeeId);
		employeeRoles = employee.getRole();	
		roleCount = employeeRoles.size();

		if(option == 1) {
			logger.info("Add Employee Roles");
			employeeRoles = addRoleSet(employeeRoles, roleId);
			if(roleCount.equals(employeeRoles.size())) {
				return 0;
			}
		} else {
			logger.info("Remove Employee Roles");
			employeeRoles = removeRoleSet(employeeRoles, roleId);
		}

		employee.setRole(employeeRoles);
		employeeDao.update(employee);

		return 1;
	}

	public Set <Roles> addRoleSet(Set <Roles> roles, Long roleId) {
		Roles newRole = new Roles(" ", " ");
		Boolean exist = false;

		newRole.setId(roleId);

		if(!(roleDao.checkDuplicateRole(newRole, 4))) {
			return roles;
		}
		newRole = roleDao.get(Roles.class, roleId);
		
		if(roles.isEmpty()) {
			roles.add(newRole);
		} else {
			for(Roles list : roles) {
				if(newRole.getId().equals(list.getId())) {
					exist = true;
				}
			}
			if(!exist) {
				roles.add(newRole);
			}
		}
		return roles;
	}

	public Set <Roles> removeRoleSet(Set <Roles> roles, Long roleId) {
		Roles deleteRole = new Roles(" ", " ");
		Iterator <Roles> iterator = null;
		Boolean exist = false;

		deleteRole = roleDao.get(Roles.class, roleId);
		
		iterator = roles.iterator();
		while(iterator.hasNext()) {
			if(deleteRole.getId().equals(iterator.next().getId())) {
				exist = true;
				iterator.remove();
			}
		}

		return roles;
	}	

	public Set <Roles> getCurrentRoles(Long employeeId) {
		logger.info("Get Current Employee Roles");
		Set <Roles> currentRoles = employeeDao.getEmployeeCollection(employeeId).getRole();
		return currentRoles;
	}
}