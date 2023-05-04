package com.masai.service;

import java.time.LocalDate;
import java.util.List;

import com.masai.dao.OrderDAO;
import com.masai.dao.OrderDAOimpl;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

public class OrderServiceImpl implements OrderService {

	@Override
	public void purchasePolicy(String planName, String modeOfPayment, double premiumPayment)
			throws SomeThingWentWrongException {
		OrderDAO orderDAO = new OrderDAOimpl();
		orderDAO.purchasePolicy(planName, modeOfPayment, premiumPayment);
	}
	
	@Override
	public List<Object[]> getPurchasedPolicies() throws SomeThingWentWrongException, NoRecordFoundException {
		OrderDAO orderDAO = new OrderDAOimpl();
		return orderDAO.getPurchasedPolicies();
	}
	
	@Override
	public List<Object[]> getPoliciesForRenewal() throws SomeThingWentWrongException, NoRecordFoundException{
		OrderDAO orderDAO = new OrderDAOimpl();
		return orderDAO.getPoliciesForRenewal();
	}
	
	@Override
	public void renewPolicy(int orderId, LocalDate newExpirationDate, double premiumAmount, String modeOfPayment) 
			throws SomeThingWentWrongException{
		OrderDAO orderDAO = new OrderDAOimpl();
		orderDAO.renewPolicy(orderId, newExpirationDate, premiumAmount, modeOfPayment);	
	}
	
	@Override
	public int makePoliciesDiscontinued() throws SomeThingWentWrongException {
		OrderDAO orderDAO = new OrderDAOimpl();
		return orderDAO.makePoliciesDiscontinued();
	}
	
	@Override
	public List<Object[]> getPolicyReport() throws SomeThingWentWrongException{
		OrderDAO orderDAO = new OrderDAOimpl();
		return orderDAO.getPolicyReport();
	}

}
