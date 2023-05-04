package com.masai.service;

import java.util.List;

import com.masai.exception.SomeThingWentWrongException;

public interface AgeBandWisePremiumSurchargeForPlanService {
	public List<Object[]> getPremiumSurchargeForPlan(String planName) throws SomeThingWentWrongException;
}
