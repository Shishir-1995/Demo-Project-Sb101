package com.masai.dao;

import java.util.List;

import com.masai.entity.Customer;
import com.masai.entity.LoggedInUserId;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class CustomerDAOImpl implements CustomerDAO {
	@Override
	public void addCustomer(Customer customer) throws SomeThingWentWrongException {
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			
			//check if company with same username exists
			Query query = em.createQuery("SELECT count(c) FROM Customer c WHERE username = :username");
			query.setParameter("username", customer.getUsername());
			if((Long)query.getSingleResult() > 0) {
				//you are here means company with given name exists so throw exceptions
				throw new SomeThingWentWrongException("The username" + customer.getUsername() + " is already occupied");
			}
			
			//you are here means no company with given name
			EntityTransaction et = em.getTransaction();
			et.begin();
			em.persist(customer);
			et.commit();
		}catch(PersistenceException ex) {
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
	}
	
	@Override
	public void login(String username, String password) throws SomeThingWentWrongException{
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			
			Query query = em.createQuery("SELECT c.id FROM Customer c WHERE username = :username AND password = :password AND isDeleted = 0");
			query.setParameter("username", username);
			query.setParameter("password", password);
			List<Integer> listInt = (List<Integer>)query.getResultList();
			if(listInt.size() == 0) {
				//you are here means company with given name exists so throw exceptions
				throw new SomeThingWentWrongException("The username or password is incorrect");
			}
			LoggedInUserId.loggedInUserId = listInt.get(0);
		}catch(PersistenceException ex) {
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
	}
	
	@Override
	public void changePassword(String oldPassword, String newPassword) throws SomeThingWentWrongException{
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT count(c) FROM Customer c WHERE password = :oldPassword AND id = :id");
			query.setParameter("oldPassword", oldPassword);
			query.setParameter("id", LoggedInUserId.loggedInUserId);
			Long userCount = (Long)query.getSingleResult();
			if(userCount == 0) {
				//you are here old password is incorrect for logged in user
				throw new SomeThingWentWrongException("Invalid old password");
			}
			//You are here means all checks done, We can proceed for changing the password
			Customer customer = em.find(Customer.class, LoggedInUserId.loggedInUserId);
			EntityTransaction et = em.getTransaction();
			et.begin();
			customer.setPassword(newPassword);
			et.commit();
		}catch(PersistenceException ex) {
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
	}
	
	@Override
	public void deleteAccount() throws SomeThingWentWrongException{
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			Customer customer = em.find(Customer.class, LoggedInUserId.loggedInUserId);
			EntityTransaction et = em.getTransaction();
			et.begin();
			customer.setIsDeleted(1);
			et.commit();
		}catch(PersistenceException ex) {
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
	}
	
	@Override
	public List<Object[]> getCustomerList() throws SomeThingWentWrongException, NoRecordFoundException{
		EntityManager em = null;
		List<Object[]> customerList = null;
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT c.name, c.username, c.dateOfBirth, c.hasMedicalCondition, "
					+ "c.isDeleted FROM Customer c");
			customerList = query.getResultList();
			if(customerList.size() == 0)
				throw new NoRecordFoundException("No Customer Found");
		}catch(PersistenceException ex) {
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
		return customerList;
	}
}
