package com.masai.service;

import java.util.List;

import com.masai.dao.CustomerDAO;
import com.masai.dao.CustomerDAOImpl;
import com.masai.entity.Customer;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

public class CustomerServiceImpl implements CustomerService {

	@Override
	public void addCustomer(Customer customer) throws SomeThingWentWrongException {
		//Create an object of CustomerDAO
		CustomerDAO customerDAO = new CustomerDAOImpl();
		customerDAO.addCustomer(customer);
	}
	
	@Override
	public void login(String username, String password) 
			throws SomeThingWentWrongException, NoRecordFoundException{
		//Create an object of CustomerDAO
		CustomerDAO customerDAO = new CustomerDAOImpl();
		customerDAO.login(username, password);		
	}
	
	@Override
	public void changePassword(String oldPassword, String newPassword) 
			throws SomeThingWentWrongException{
		//Create an object of CustomerDAO
		CustomerDAO customerDAO = new CustomerDAOImpl();
		customerDAO.changePassword(oldPassword, newPassword);
	}
	
	@Override
	public void deleteAccount() throws SomeThingWentWrongException{
		//Create an object of CustomerDAO
		CustomerDAO customerDAO = new CustomerDAOImpl();
		customerDAO.deleteAccount();
	}
	
	@Override
	public List<Object[]> getCustomerList() throws SomeThingWentWrongException, NoRecordFoundException{
		//Create an object of CustomerDAO
		CustomerDAO customerDAO = new CustomerDAOImpl();
		return customerDAO.getCustomerList();
	}

}
