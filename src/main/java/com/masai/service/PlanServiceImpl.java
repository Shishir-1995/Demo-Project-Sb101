package com.masai.service;

import java.time.LocalDate;
import java.util.List;

import com.masai.dao.PlanDAO;
import com.masai.dao.PlanDAOImpl;
import com.masai.entity.AgeBandWisePremiumForPlan;
import com.masai.entity.Plan;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

public class PlanServiceImpl implements PlanService {
	@Override
	public void addPlan(Plan plan) throws SomeThingWentWrongException {
		//Create an object of Plan DAO
		PlanDAO planDAO = new PlanDAOImpl();
		planDAO.addPlan(plan);
	}
	
	@Override
	public List<Object[]> getAllPlans() throws SomeThingWentWrongException, NoRecordFoundException{
		//Create an object of Plan DAO
		PlanDAO planDAO = new PlanDAOImpl();
		return planDAO.getAllPlans();
	}
	
	@Override
	public void updatePlan(Plan plan) throws SomeThingWentWrongException, NoRecordFoundException{
		//Create an object of Plan DAO
		PlanDAO planDAO = new PlanDAOImpl();
		planDAO.updatePlan(plan);
	}
	
	@Override
	public void updatePremiumAndSurcharge(String planName, List<AgeBandWisePremiumForPlan> ageBandWisePremiumForPlanList) 
			throws SomeThingWentWrongException, NoRecordFoundException{
		PlanDAO planDAO = new PlanDAOImpl();
		planDAO.updatePremiumAndSurcharge(planName, ageBandWisePremiumForPlanList);
	}
	
	@Override
	public List<Double> getPremiumAmount(String planName) throws SomeThingWentWrongException, NoRecordFoundException{
		PlanDAO planDAO = new PlanDAOImpl();
		List<Double> premiumComponents = planDAO.getPremiumForPlan(planName);
		double premiumAmount = (premiumComponents.get(0) + premiumComponents.get(1)) * (1 + premiumComponents.get(2)/100);
		premiumComponents.add(3, premiumAmount);
		return premiumComponents;
	}
	
	@Override
	public List<Double> getPremiumAmount(String planName, LocalDate nextToExpDate) 
			throws SomeThingWentWrongException, NoRecordFoundException{
		PlanDAO planDAO = new PlanDAOImpl();
		List<Double> premiumComponents = planDAO.getPremiumForPlan(planName, nextToExpDate);
		double premiumAmount = (premiumComponents.get(0) + premiumComponents.get(1)) * (1 + premiumComponents.get(2)/100);
		premiumComponents.add(3, premiumAmount);
		return premiumComponents;
	}
}
