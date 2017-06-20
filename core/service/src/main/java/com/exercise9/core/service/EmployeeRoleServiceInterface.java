package com.exercise9.core.service;
import java.util.Set;
import com.exercise9.core.model.Roles;

public interface EmployeeRoleServiceInterface {
	public Integer addRemoveEmployeeRoles(Integer option, Long employeeId, Long roleId);
	public Set <Roles> addRoleSet(Set <Roles> roles, Long roleId);
	public Set <Roles> removeRoleSet(Set <Roles> roles, Long roleId);
	public Set <Roles> getCurrentRoles(Long employeeId);
}