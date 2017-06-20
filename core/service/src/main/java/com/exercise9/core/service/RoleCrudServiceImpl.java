package com.exercise9.core.service;

import com.exercise9.core.model.Roles;
import com.exercise9.util.InputUtil;
import com.exercise9.core.dao.RoleDAO;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=false)
public class RoleCrudServiceImpl implements CrudServiceInterface <Roles> {
	private Logger logger = Logger.getLogger(RoleCrudServiceImpl.class);
	
	@Autowired
	private RoleDAO roleDao;

	public Roles create(Roles role) {
		if (roleDao.checkDuplicateRole(role, 1)) {
			role.setRoleName(" ");
			return role;
		} else {
			role = roleDao.add(role);
			return role;
		}
	}

	@Transactional(readOnly=false)
	public List <Roles> read(Integer sortRule, Boolean ascending) {
		logger.info("List available Roles");
		List <Roles> list = roleDao.showRoles(sortRule, ascending);
		return list;
	}	

	public Roles update(Roles role) {
		logger.info("Update available Roles");
		if(roleDao.checkDuplicateRole(role, 2)) {
			role.setRoleName(" ");
			return role;
		} else {
			role = roleDao.update(role);
			return role;
		}
	}		

	public Roles delete(Long roleId) {
		logger.info("Delete available Roles");
		Roles role = new Roles();
		role.setId(roleId);
		if (!(roleDao.checkDuplicateRole(role, 3))) {
			role = roleDao.get(Roles.class, roleId);
			role = roleDao.delete(role);
			return role;
		} else {
			return role;
		}
	}

	public Roles get(Long roleId) {
		logger.info("Get Role Information");
		Roles role = new Roles();
		role = roleDao.get(Roles.class, roleId);
		return role;
	}
}	