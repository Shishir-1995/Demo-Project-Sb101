package com.masai.dao;

import java.util.List;

import com.masai.entity.Company;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class CompanyDAOImpl implements CompanyDAO {
	public void addCompany(Company company) throws SomeThingWentWrongException {
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			
			//check if company with same name exists
			Query query = em.createQuery("SELECT count(c) FROM Company c WHERE companyName = :companyName");
			query.setParameter("companyName", company.getCompanyName());
			if((Long)query.getSingleResult() > 0) {
				//you are here means company with given name exists so throw exceptions
				throw new SomeThingWentWrongException("Company already exists with name " + company.getCompanyName());
			}
			
			//you are here means no company with given name
			EntityTransaction et = em.getTransaction();
			et.begin();
			em.persist(company);
			et.commit();
		}catch(PersistenceException ex) {
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
	}
	
	public List<Company> getCompanyList() throws SomeThingWentWrongException, NoRecordFoundException{
		EntityManager em = null;
		List<Company> companyList = null;
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("FROM Company c");
			companyList = (List<Company>)query.getResultList();
			if(companyList.size() ==0) {
				throw new NoRecordFoundException("No company Found");
			}
		}catch(IllegalArgumentException ex) {
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
		return companyList;
	}
	
	public void updateCompany(Company company) throws SomeThingWentWrongException, NoRecordFoundException{
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			//check if company with company with given id exists
			Company companyFromDB = em.find(Company.class, company.getId());
			if(companyFromDB == null)
				throw new NoRecordFoundException("No Company found with the given id " + company.getId());

			//You are here means company exists with given id
			//check if company is to be renamed
			if(!companyFromDB.getCompanyName().equals(company.getCompanyName())) {
				//you are here means company is to be renamed, check for no existing company with new name.
				//check if company with same name exists
				Query query = em.createQuery("SELECT count(c) FROM Company c WHERE companyName = :companyName");
				query.setParameter("companyName", company.getCompanyName());
				if((Long)query.getSingleResult() > 0) {
					//you are here means company with given name exists so throw exceptions
					throw new SomeThingWentWrongException("Company already exists with name " + company.getCompanyName());
				}
			}
			
			//proceed for update operation
			
			EntityTransaction et = em.getTransaction();
			et.begin();
			companyFromDB.setCompanyName(company.getCompanyName());
			companyFromDB.setEstdYear(company.getEstdYear());
			companyFromDB.setSectorType(company.getSectorType());
			et.commit();
		}catch(PersistenceException ex) {
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
	}
	
	public Company getCompanyObjectByName(String companyName) throws SomeThingWentWrongException, NoRecordFoundException{
		EntityManager em = null;
		List<Company> companyList = null;
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("FROM Company c WHERE companyName = :companyName");
			query.setParameter("companyName", companyName);
			//we cannot use getSingleResult here because if no company exists with the given name then it throws exception
			//so used getSingleResult if and only if you are sure that there will be result against your query
			companyList = (List<Company>)query.getResultList();
			if(companyList.size() ==0) {
				throw new NoRecordFoundException("No company Found");
			}
		}catch(IllegalArgumentException ex) {
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
		return companyList.get(0);
	}
}