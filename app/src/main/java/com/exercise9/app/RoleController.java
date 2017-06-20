package com.exercise9.app;

import com.exercise9.core.model.Roles;
import com.exercise9.core.model.ContactInfo;
import com.exercise9.core.service.RoleCrudServiceImpl;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoleController {
	private Logger logger = Logger.getLogger(RoleController.class);

	@Autowired
	private RoleCrudServiceImpl roleService;

	private static final Integer sortById = new Integer(1);
	private static final Integer sortByCode = new Integer(2);
	private static final Integer sortByName = new Integer(3);
	private static final Boolean ascending = true;
	private static final Boolean descending = false;

	@RequestMapping(value="/roles", method=RequestMethod.GET)
	protected ModelAndView showRoles(@RequestParam(required=false) String sort, @RequestParam(required=false) String order) {
		logger.info("Role Controller show current Roles");
		Integer sortType = null;
		Boolean orderType = null;

		if((sort == null) || (sort.equals("id"))) {
			sortType = sortById;
		} else if(sort.equals("code")) {
			sortType = sortByCode;
		} else if(sort.equals("name")) {
			sortType = sortByName;
		} 

		if((order == null) || (order.equals("ascending"))) {
			orderType = ascending;
		} else {
			orderType = descending;
		}

		ModelAndView modelAndView = new ModelAndView("roles");
		List <Roles> roleList = roleService.read(sortType, orderType);
		modelAndView.addObject("roles", roleList);

		return modelAndView;
	}

	@RequestMapping(value="/roles", method=RequestMethod.POST)
	public ModelAndView deleteRole(@RequestParam("roleId") String id) {
		logger.info("Role Controller delete role");
		Long roleId = Long.parseLong(id);
		Roles role = roleService.delete(roleId);

		ModelAndView modelAndView = new ModelAndView("roles");

		List <Roles> roleList = roleService.read(sortById, ascending);
		modelAndView.addObject("roles", roleList);

		return modelAndView;
	}

	@RequestMapping(value="/roles/add", method=RequestMethod.POST)
	public ModelAndView addRole(@RequestParam String roleCode, @RequestParam String roleName) {
		logger.info("Role Controller add role");
		Roles role = new Roles(roleName, roleCode);
		role = roleService.create(role);
		ModelAndView modelAndView = new ModelAndView("message");
		
		if(role.getRoleName() == " ") {
			modelAndView.addObject("message", "Role Code already exists<br/>New role was not added");
		} else {
			modelAndView.addObject("message", "New Role was added successfully");
		}

		modelAndView.addObject("redirectView", "http://localhost:8080/roles");
		return modelAndView;		
	}

	@RequestMapping(value="roles/add", method=RequestMethod.GET)
	public ModelAndView showAddForm() {
		logger.info("Role Controller show add role form");
		return new ModelAndView("roleaddform");
	}

	@RequestMapping(value="/roles/update", method=RequestMethod.POST)
	public ModelAndView addRole(@RequestParam String roleCode, @RequestParam String roleName, @RequestParam("roleId") String id) {
		logger.info("Role Controller update role");
		Roles role = new Roles(roleName, roleCode);
		Long roleId = Long.parseLong(id);
		role.setId(roleId);
		role = roleService.update(role);
		ModelAndView modelAndView = new ModelAndView("message");
		
		if(role.getRoleName() == " ") {
			modelAndView.addObject("message", "Role Code already exists</br/>Role was not updated");
		} else {
			modelAndView.addObject("message", "Role was updated successfully");
		}

		modelAndView.addObject("redirectView", "http://localhost:8080/roles");
		return modelAndView;		
	}

	@RequestMapping(value="roles/update", method=RequestMethod.GET)
	public ModelAndView showUpdateForm(@RequestParam("roleId") String id) {
		logger.info("Role Controller show update role form");
		ModelAndView modelAndView = new ModelAndView("roleupdateform");
		Long roleId = Long.parseLong(id);
		Roles role = roleService.get(roleId);
		modelAndView.addObject("role", role);
		return modelAndView;
	}
}