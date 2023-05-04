package com.masai.dao;

import java.time.LocalDate;
import java.util.List;

import com.masai.entity.AgeBandWisePremiumForPlan;
import com.masai.entity.Plan;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

public interface PlanDAO {
	void addPlan(Plan plan) throws SomeThingWentWrongException;
	List<Object[]> getAllPlans() throws SomeThingWentWrongException, NoRecordFoundException;
	void updatePlan(Plan plan) throws SomeThingWentWrongException, NoRecordFoundException;
	void updatePremiumAndSurcharge(String planName, List<AgeBandWisePremiumForPlan> ageBandWisePremiumForPlanList) 
			throws SomeThingWentWrongException, NoRecordFoundException;
	List<Double> getPremiumForPlan(String planName) throws SomeThingWentWrongException, NoRecordFoundException;
	List<Double> getPremiumForPlan(String planName, LocalDate nextToExpDate) 
			throws SomeThingWentWrongException, NoRecordFoundException;
}
