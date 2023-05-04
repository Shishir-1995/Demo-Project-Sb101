package com.masai.dao;

import java.time.LocalDate;
import java.util.List;

import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

public interface OrderDAO {
	void purchasePolicy(String planName, String modeOfPayment, double premiumPayment) throws SomeThingWentWrongException;
	List<Object[]> getPurchasedPolicies() throws SomeThingWentWrongException, NoRecordFoundException;
	List<Object[]> getPoliciesForRenewal() throws SomeThingWentWrongException, NoRecordFoundException;
	void renewPolicy(int orderId, LocalDate newExpirationDate, double premiumAmount, String modeOfPayment) 
			throws SomeThingWentWrongException;
	int makePoliciesDiscontinued() throws SomeThingWentWrongException;
	List<Object[]> getPolicyReport() throws SomeThingWentWrongException;
}
