package com.masai.dao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.masai.entity.Customer;
import com.masai.entity.LoggedInUserId;
import com.masai.entity.Order;
import com.masai.entity.Plan;
import com.masai.entity.PremiumPayment;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class OrderDAOimpl implements OrderDAO {

	@Override
	public void purchasePolicy(String planName, String modeOfPayment, double premiumPayment) 
			throws SomeThingWentWrongException {
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("FROM Plan p WHERE planName = :planName");
			query.setParameter("planName", planName);
			//Here we can use getSingleResult because we are sure that plan exists with given name
			Plan plan = (Plan)query.getSingleResult();
			//get the entity of Customer
			Customer customer = em.find(Customer.class, LoggedInUserId.loggedInUserId);
			
			//If age of person is more than 60 then he is not allowed to purchase insurance
			if(ChronoUnit.YEARS.between(customer.getDateOfBirth(), customer.getDateOfBirth()) > 60) {
				throw new SomeThingWentWrongException("Maximum age of entry is 60");
			}
			
			//create the Order object
			Order order = new Order(customer, plan, LocalDate.now(), LocalDate.now().plusYears(1).minusDays(1), 
					"continued", null);
			//Create an object of Premium Payment also
			Set<PremiumPayment> setPP = new HashSet<>();
			setPP.add(new PremiumPayment(order, premiumPayment, LocalDate.now(), modeOfPayment));
			order.setPremiumPaymentSet(setPP);
			EntityTransaction et = em.getTransaction();
			et.begin();
			em.persist(order);
			et.commit();
		}catch(PersistenceException | IllegalArgumentException ex) {
			ex.printStackTrace();
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
	}
	
	@Override
	public List<Object[]> getPurchasedPolicies() throws SomeThingWentWrongException, NoRecordFoundException{
		EntityManager em = null;
		List<Object[]> list = null;
		try {
			em = EMUtils.getEntityManager();
			//find the details of logged in customer
			Customer customer = em.find(Customer.class, LoggedInUserId.loggedInUserId);
			//SELECT p.planName, o.id, o.policyStatus, o.dateOfPurchase
			//FROM Plan p JOIN Order o ON o.plan = p JOIN Customer c ON o.customer = c WHERE c = :cust
			Query query = em.createQuery("SELECT p.planName, o.id, o.dateOfPurchase, o.expirationDate, o.policyStatus "
					+ "FROM Plan p JOIN Order o ON o.plan = p JOIN Customer c ON o.customer = c WHERE c = :cust");
			query.setParameter("cust", customer);
			list = query.getResultList();
			if(list.size() == 0)
				throw new SomeThingWentWrongException("No policy found");
		}catch(PersistenceException ex) {
			ex.printStackTrace();
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
		System.out.println("C");
		return list;
	}
	
	@Override
	public List<Object[]> getPoliciesForRenewal() throws SomeThingWentWrongException, NoRecordFoundException{
		EntityManager em = null;
		List<Object[]> list = null;
		try {
			em = EMUtils.getEntityManager();
			//find the details of logged in customer
			Customer customer = em.find(Customer.class, LoggedInUserId.loggedInUserId);
			//SELECT p.planName, o.id, o.policyStatus, o.dateOfPurchase
			//FROM Plan p JOIN Order o ON o.plan = p JOIN Customer c ON o.customer = c WHERE c = :cust
			Query query = em.createQuery("SELECT o.id, o.expirationDate, p.planName "
					+ "From Order o JOIN Plan p ON o.plan = p JOIN Customer c ON o.customer = c "
					+ "WHERE c = :cust AND o.expirationDate BETWEEN :previous30Days AND :yesterday "
					+ "ORDER BY o.expirationDate DESC");
			query.setParameter("cust", customer);
			query.setParameter("yesterday", LocalDate.now().minusDays(1));
			query.setParameter("previous30Days", LocalDate.now().minusDays(30));
			list = query.getResultList();
			if(list.size() == 0)
				throw new SomeThingWentWrongException("No policy found");
		}catch(PersistenceException ex) {
			ex.printStackTrace();
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
		return list;
	}
	
	
	@Override
	public void renewPolicy(int orderId, LocalDate newExpirationDate, double premiumAmount, String modeOfPayment) 
			throws SomeThingWentWrongException{
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			//get the Order object
			Order order = em.find(Order.class, orderId);	//now the entity is in the managed state
			EntityTransaction et = em.getTransaction();
			et.begin();
			order.setExpirationDate(newExpirationDate);
			order.getPremiumPaymentSet().add(new PremiumPayment(order, premiumAmount, LocalDate.now(), modeOfPayment));
			et.commit();
		}catch(PersistenceException ex) {
			ex.printStackTrace();
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
	}
	
	@Override
	public int makePoliciesDiscontinued() throws SomeThingWentWrongException {
		EntityManager em = null;
		int discontinuedPolicyCount = 0;
		try {
			em = EMUtils.getEntityManager();
			//get the Order object
			EntityTransaction et = em.getTransaction();
			et.begin();
			Query query = em.createQuery("UPDATE Order SET policyStatus = 'discontinued' WHERE policyStatus = 'continued' AND expirationDate <= :date");
			query.setParameter("date", LocalDate.now().minusDays(30));
			discontinuedPolicyCount = query.executeUpdate();
			et.commit();
		}catch(PersistenceException ex) {
			ex.printStackTrace();
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
		return discontinuedPolicyCount;
	}
	
	@Override
	public List<Object[]> getPolicyReport() throws SomeThingWentWrongException{
		EntityManager em = null;
		List<Object[]> list = null;
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT o.policyStatus, COUNT(o) FROM Order o GROUP BY o.policyStatus ORDER BY o.policyStatus ASC");
			list = query.getResultList();
		}catch(PersistenceException ex) {
			ex.printStackTrace();
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
		return list;
	}

}
