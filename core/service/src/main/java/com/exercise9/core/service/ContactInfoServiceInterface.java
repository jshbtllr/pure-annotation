package com.exercise9.core.service;
import java.util.Set;
import com.exercise9.core.model.ContactInfo;
import com.exercise9.core.model.Employee;

public interface ContactInfoServiceInterface {
	public Integer addContactInfo(Long employeeId, ContactInfo addInfo);
	public Set <ContactInfo> addContactSet(Set <ContactInfo> contacts, Employee employee, ContactInfo addInfo);
	public ContactInfo checkInfo(ContactInfo info);
	public Integer removeContactInfo(Long employeeId, ContactInfo deleteContact);
	public Integer updateContactInfo(Long employeeId, ContactInfo updateContact, String newInfoDetail);
	public Set <ContactInfo> getCurrentContacts(Long employeeId);
}