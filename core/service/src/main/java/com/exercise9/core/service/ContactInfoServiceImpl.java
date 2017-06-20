package com.exercise9.core.service;

import com.exercise9.core.model.ContactInfo;
import com.exercise9.core.model.Employee;
import com.exercise9.core.dao.EmployeeDAO;
import com.exercise9.util.InputUtil;

import java.util.Set;
import java.util.Iterator;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=false)
public class ContactInfoServiceImpl implements ContactInfoServiceInterface {
	private Logger logger = Logger.getLogger(ContactInfoServiceImpl.class);

	@Autowired
	private EmployeeDAO employeeDao;

	public Integer addContactInfo(Long employeeId, ContactInfo addInfo) {
		Employee employee = null;
		Set <ContactInfo> contacts;
		Integer contactCount = null;

		employee = employeeDao.getEmployeeCollection(employeeId);
		contacts = employee.getContactInfo();	
		contactCount = contacts.size();

		contacts = addContactSet(contacts, employee, addInfo);

		employee.setContactInfo(contacts);
		employeeDao.update(employee);
		
		if(contacts.size() == contactCount) {
			logger.info("No contacts added");
			return 0;
		} else {
			logger.info("Contacts added");
			return 1;
		}		
	}

	public Set <ContactInfo> addContactSet(Set <ContactInfo> contacts, Employee employee, ContactInfo addInfo) {	
		Boolean exist = false;

		addInfo = checkInfo(addInfo);

		if(addInfo.getInfoType().equals(" ")) {
			return contacts;
		} else {

			if(contacts.isEmpty()) {
				contacts.add(addInfo); 			
			} else {
				for(ContactInfo list : contacts) {
					if(list.getInfoDetail().equals(addInfo.getInfoDetail())) {
						exist = true;
					}
				}
				if(!exist) {
					contacts.add(addInfo);		
				}
			}
		}
		return contacts;
	}

	public ContactInfo checkInfo(ContactInfo info) {
		if(info.getInfoType().equals("email")) {
			if(!EmailValidator.getInstance().isValid(info.getInfoDetail())) {
				logger.info("Invalid Email");
				info.setInfoType(" ");
			}
			logger.info("Valid Email");
		} else if(info.getInfoType().equals("telephone")) {
			if(!info.getInfoDetail().matches("^[1-9]{1}\\d{6}")) {
				logger.info("Invalid Telephone");
				info.setInfoType(" ");
			}
			logger.info("Valid Telephone");
		} else if(info.getInfoType().equals("cellphone")) {
			if(!info.getInfoDetail().matches("^09\\d{9}")) {
				logger.info("Invalid Cellphone");
				info.setInfoType(" ");
			}	
			logger.info("Valid Cellphone");		
		} else {
			logger.info("Invalid info type");
			info.setInfoType(" ");
		}

		return info;		
	}

	public Integer removeContactInfo(Long employeeId, ContactInfo deleteContact) {
		Employee employee;
		Boolean exist = false;
		Set <ContactInfo> contacts = null;
		Iterator <ContactInfo> iterator = null;
		logger.info("Remove Contact Info");
		employee = employeeDao.getEmployeeCollection(employeeId);
		contacts = employee.getContactInfo();

		iterator = contacts.iterator();
		while(iterator.hasNext()) {
			if(deleteContact.getInfoDetail().equals(iterator.next().getInfoDetail())) {
				exist = true;
				iterator.remove();
			}
		}

		employee.setContactInfo(contacts);
		employeeDao.update(employee);

		if(!exist) {
			return 0;
		}
		return 1;
	}

	public Integer updateContactInfo(Long employeeId, ContactInfo updateContact, String newInfoDetail) {
		logger.info("Update Contact Info");
		Employee employee = null;
		Boolean exist = false;
		ContactInfo newInfo = new ContactInfo(updateContact.getInfoType(), newInfoDetail);
		Set <ContactInfo> contacts = null;
		Iterator <ContactInfo> iterator = null;		

		employee = employeeDao.getEmployeeCollection(employeeId);
		contacts = employee.getContactInfo();
		
		for(ContactInfo list : contacts) {
			if(updateContact.getInfoDetail().equals(list.getInfoDetail())) {
				newInfo = checkInfo(newInfo);
				if(newInfo.getInfoType().equals(" ")) {
					return 0;
				} else {					
					list.setInfoDetail(newInfoDetail);
				}
			}
		}

		employee.setContactInfo(contacts);
		employeeDao.update(employee);	

		return 1;
	}

	public Set <ContactInfo> getCurrentContacts(Long employeeId) {
		Set <ContactInfo> contactList = employeeDao.getEmployeeCollection(employeeId).getContactInfo();
		return contactList;
	}
}