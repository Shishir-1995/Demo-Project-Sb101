package com.masai.service;

import java.util.List;

import com.masai.dao.AgeBandWisePremiumSurchargeForPlanDAO;
import com.masai.dao.AgeBandWisePremiumSurchargeForPlanDAOImpl;
import com.masai.exception.SomeThingWentWrongException;

public class AgeBandWisePremiumSurchargeForPlanServiceImpl implements AgeBandWisePremiumSurchargeForPlanService {

	@Override
	public List<Object[]> getPremiumSurchargeForPlan(String planName) throws SomeThingWentWrongException {
		AgeBandWisePremiumSurchargeForPlanDAO ab = new AgeBandWisePremiumSurchargeForPlanDAOImpl();
		return ab.getPremiumSurchargeForPlan(planName);
	}

}
