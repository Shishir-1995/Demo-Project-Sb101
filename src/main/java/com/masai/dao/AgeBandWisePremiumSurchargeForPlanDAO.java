package com.masai.dao;

import java.util.List;

import com.masai.exception.SomeThingWentWrongException;

public interface AgeBandWisePremiumSurchargeForPlanDAO {
	public List<Object[]> getPremiumSurchargeForPlan(String planName) throws SomeThingWentWrongException;
}
