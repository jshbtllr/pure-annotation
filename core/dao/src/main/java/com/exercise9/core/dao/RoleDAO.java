package com.exercise9.core.dao;

import com.exercise9.core.model.Roles;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import org.springframework.stereotype.Repository;

@Repository
public class RoleDAO extends GenericDAOImpl <Roles> {
	public List <Roles> showRoles(Integer sortRule, Boolean ascending) {
		Query query = null;
		Integer rows = new Integer(0);
		List <Roles> list = null;

		if(sortRule == 1) {
			if(ascending == true) {
				query = sessionFactory.getCurrentSession().createQuery("FROM Roles ORDER BY id");
			} else {
				query = sessionFactory.getCurrentSession().createQuery("FROM Roles ORDER BY id DESC");
			}
		} else if(sortRule == 2) {
			if(ascending == true) {
				query = sessionFactory.getCurrentSession().createQuery("FROM Roles ORDER BY roleCode");
			} else {
				query = sessionFactory.getCurrentSession().createQuery("FROM Roles ORDER BY roleCode DESC");
			} 
		} else if(sortRule == 3) {
			if(ascending == true) {
				query = sessionFactory.getCurrentSession().createQuery("FROM Roles ORDER BY roleName");
			} else {
				query = sessionFactory.getCurrentSession().createQuery("FROM Roles ORDER BY roleName desc");
			}
		}

		list = query.setCacheable(true).list();
		return list;
	}		

	public Boolean checkDuplicateRole(Roles role, Integer option) {
		Boolean existing = false;
		Query query = null;

		if (option == 1) {										/*Check Duplicate given rolecode*/
			query = sessionFactory.getCurrentSession().createQuery("SELECT id FROM Roles WHERE roleCode = :rolecode");		
			query.setParameter("rolecode", role.getRoleCode());
		} else if (option == 2) {								/*Check Duplicate given rolecode and rolename*/
			query = sessionFactory.getCurrentSession().createQuery("SELECT id FROM Roles WHERE roleCode = :rolecode AND id != :roleid");
			query.setParameter("rolecode", role.getRoleCode());
			query.setParameter("roleid", role.getId());
		} else if (option == 3) {								/*Check Duplicate assigned to employee given roleid*/
			query = sessionFactory.getCurrentSession().createQuery("SELECT a.id from Employee a join a.role as b WHERE b.id = :paramId");			
			query.setParameter("paramId", role.getId());
		} else if (option == 4) {								/*Check duplicate given roleId*/
			query = sessionFactory.getCurrentSession().createQuery("SELECT id FROM Roles WHERE id = :roleid");		
			query.setParameter("roleid", role.getId());
		}

		existing = !(query.setCacheable(true).list().isEmpty());
		return existing;
	}
}