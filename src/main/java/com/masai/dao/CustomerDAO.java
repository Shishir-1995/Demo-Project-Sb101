package com.masai.dao;

import java.util.List;

import com.masai.entity.Customer;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

public interface CustomerDAO {
	void addCustomer(Customer customer) throws SomeThingWentWrongException;
	void login(String username, String password) throws SomeThingWentWrongException;
	void changePassword(String oldPassword, String newPassword) throws SomeThingWentWrongException;
	void deleteAccount() throws SomeThingWentWrongException;
	List<Object[]> getCustomerList() throws SomeThingWentWrongException, NoRecordFoundException;
}
