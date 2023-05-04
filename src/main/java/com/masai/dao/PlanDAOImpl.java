package com.masai.dao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.masai.entity.AgeBand;
import com.masai.entity.AgeBandWisePremiumForPlan;
import com.masai.entity.Company;
import com.masai.entity.Customer;
import com.masai.entity.LoggedInUserId;
import com.masai.entity.Plan;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class PlanDAOImpl implements PlanDAO {
	@Override
	public void addPlan(Plan plan) throws SomeThingWentWrongException {
		EntityManager em = null;
		
		try {
			em = EMUtils.getEntityManager();
			
			//check if plan with same name exists
			Query query = em.createQuery("SELECT count(p) FROM Plan p WHERE planName = :planName");
			query.setParameter("planName", plan.getPlanName());
			if((Long)query.getSingleResult() > 0) {
				//you are here means company with given name exists so throw exceptions
				throw new SomeThingWentWrongException("Plan already exists with name " + plan.getPlanName());
			}
			
			//take age band object
			query = em.createQuery("SELECT ab FROM AgeBand ab");
			List<AgeBand> ageBandList = query.getResultList();
			
			Set<AgeBandWisePremiumForPlan> set = plan.getAgeBandWisePremiumSet();
			int i = 0;
			for(AgeBandWisePremiumForPlan ap :set)
				ap.setAgeBand(ageBandList.get(i++));
			
			//you are here means no company with given name
			EntityTransaction et = em.getTransaction();
			et.begin();
			em.persist(plan);
			et.commit();
		}catch(PersistenceException ex) {
			ex.printStackTrace();
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
	}
	
	@Override
	public List<Object[]> getAllPlans() throws SomeThingWentWrongException, NoRecordFoundException {
		EntityManager em = null;
		List<Object[]> planList = null;
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT p.planName, p.planType, p.gstRate, p.maxCoverageAge, c.companyName, p.id FROM Plan p JOIN Company c ON p.company = c");
			planList = (List<Object[]>)query.getResultList();
			if(planList.size() == 0) {
				throw new NoRecordFoundException("No plan Found");
			}
		}catch(IllegalArgumentException ex) {
			ex.printStackTrace();	//this is only for debugging, please comment in production mode
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
		return planList;
	}
	
	@Override
	public void updatePlan(Plan plan) throws SomeThingWentWrongException, NoRecordFoundException{
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			//check if plan with given id exists
			Plan planFromDB = em.find(Plan.class, plan.getId());
			if(planFromDB == null)
				throw new NoRecordFoundException("No plan found with the given id " + plan.getId());

			//You are here means company exists with given id
			//check if company is to be renamed
			if(!planFromDB.getPlanName().equals(plan.getPlanName())) {
				//you are here means plan is to be renamed, check for no existing plan with new name.
				//check if plan with same name exists
				Query query = em.createQuery("SELECT count(p) FROM Plan p WHERE planName = :planName");
				query.setParameter("planName", plan.getPlanName());
				if((Long)query.getSingleResult() > 0) {
					//you are here means company with given name exists so throw exceptions
					throw new SomeThingWentWrongException("Plan already exists with name " + plan.getPlanName());
				}
			}
			
			//proceed for update operation
			EntityTransaction et = em.getTransaction();
			et.begin();
			planFromDB.setPlanName(plan.getPlanName());
			planFromDB.setPlanType(plan.getPlanType());
			planFromDB.setGstRate(plan.getGstRate());
			planFromDB.setMaxCoverageAge(plan.getMaxCoverageAge());
			planFromDB.setCompany(plan.getCompany());
			et.commit();
		}catch(PersistenceException ex) {
			ex.printStackTrace();
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
	}
	
	@Override
	public void updatePremiumAndSurcharge(String planName, List<AgeBandWisePremiumForPlan> ageBandWisePremiumForPlanList) 
			throws SomeThingWentWrongException, NoRecordFoundException{
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("FROM Plan p WHERE planName = :planName");
			query.setParameter("planName", planName);
			//we cannot use getSingleResult here because if no company exists with the given name then it throws exception
			//so used getSingleResult if and only if you are sure that there will be result against your query
			List<Plan> planList = (List<Plan>)query.getResultList();
			if(planList.size() == 0) {
				throw new NoRecordFoundException("No plan Found");
			}
			Plan plan = planList.get(0);
			Set<AgeBandWisePremiumForPlan> setToUpdate = plan.getAgeBandWisePremiumSet();
			int index = -1;
			for(AgeBandWisePremiumForPlan p1: setToUpdate) {
				AgeBand b1 = p1.getAgeBand();
				if(b1.getStartAge() >= 18 && b1.getEndAge() <= 35) {
					index = 0;
				}else if(b1.getStartAge() >= 36 && b1.getEndAge() <= 50) {
					index = 1;
				}else {
					index = 2;
				}
				p1.setPremiumAmount(ageBandWisePremiumForPlanList.get(index).getPremiumAmount());
				p1.setSurchargeAmount(ageBandWisePremiumForPlanList.get(index).getSurchargeAmount());
			}
			
			EntityTransaction et = em.getTransaction();
			et.begin();
			plan.setAgeBandWisePremiumSet(setToUpdate);
			et.commit();
		}catch(PersistenceException | IllegalArgumentException ex) {
			ex.printStackTrace();
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
	}
	
	@Override
	public List<Double> getPremiumForPlan(String planName) throws SomeThingWentWrongException, NoRecordFoundException{
		EntityManager em = null;
		List<Double> list = new ArrayList<>();
		//index-0: contains base premium amount
		//index-1: contains surcharge if customer has health condition; 0.0 otherwise
		//index-2: contains GST rates in %
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("FROM Plan p WHERE planName = :planName");
			query.setParameter("planName", planName);
			//we cannot use getSingleResult here because if no company exists with the given name then it throws exception
			//so used getSingleResult if and only if you are sure that there will be result against your query
			List<Plan> planList = (List<Plan>)query.getResultList();
			if(planList.size() == 0) {
				throw new NoRecordFoundException("No plan Found");
			}
			Plan plan = planList.get(0);
			list.add(plan.getGstRate());
			Customer customer = em.find(Customer.class, LoggedInUserId.loggedInUserId);
			//get the age of customer in years
			long ageInYears = ChronoUnit.YEARS.between(customer.getDateOfBirth(), LocalDate.now());
			if(ageInYears < 18 || ageInYears > 70)
				throw new SomeThingWentWrongException("Insurance will be issued to person of age 18 to 70 only");
			
			Set<AgeBandWisePremiumForPlan> ageBandSet = plan.getAgeBandWisePremiumSet();
			for(AgeBandWisePremiumForPlan abs: ageBandSet) {
				if(ageInYears >= abs.getAgeBand().getStartAge() && ageInYears <= abs.getAgeBand().getEndAge()) {
					list.add(0, abs.getPremiumAmount());
					if(customer.getHasMedicalCondition() == 1)
						list.add(1, abs.getSurchargeAmount());
					else
						list.add(1, 0.0);
					break;
				}
			}
		}catch(PersistenceException | IllegalArgumentException ex) {
			ex.printStackTrace();
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
		return list;
	}
	
	@Override
	public List<Double> getPremiumForPlan(String planName, LocalDate nextToExpDate) 
			throws SomeThingWentWrongException, NoRecordFoundException{
		EntityManager em = null;
		List<Double> list = new ArrayList<>();
		//index-0: contains base premium amount
		//index-1: contains surcharge if customer has health condition; 0.0 otherwise
		//index-2: contains GST rates in %
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("FROM Plan p WHERE planName = :planName");
			query.setParameter("planName", planName);
			//We we are sure that plan exists so we can use getSingleResult here
			Plan plan  = (Plan)query.getSingleResult();
			list.add(plan.getGstRate());
			Customer customer = em.find(Customer.class, LoggedInUserId.loggedInUserId);
			//get the age of customer in years on the day next to the expiration
			long ageInYears = ChronoUnit.YEARS.between(customer.getDateOfBirth(), nextToExpDate);
			if(ageInYears > 70)
				throw new SomeThingWentWrongException("Coverage is available up to the age of 70");
			
			Set<AgeBandWisePremiumForPlan> ageBandSet = plan.getAgeBandWisePremiumSet();
			for(AgeBandWisePremiumForPlan abs: ageBandSet) {
				if(ageInYears >= abs.getAgeBand().getStartAge() && ageInYears <= abs.getAgeBand().getEndAge()) {
					list.add(0, abs.getPremiumAmount());
					if(customer.getHasMedicalCondition() == 1)
						list.add(1, abs.getSurchargeAmount());
					else
						list.add(1, 0.0);
					break;
				}
			}
		}catch(PersistenceException | IllegalArgumentException ex) {
			ex.printStackTrace();
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
		return list;
	}
}
