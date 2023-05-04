package com.masai.dao;

import java.util.List;

import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class AgeBandWisePremiumSurchargeForPlanDAOImpl implements AgeBandWisePremiumSurchargeForPlanDAO {

	@Override
	public List<Object[]> getPremiumSurchargeForPlan(String planName) throws SomeThingWentWrongException {
		EntityManager em = null;
		List<Object[]> premiumSurchargeList = null;
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT ab.startAge, ab.endAge, abps.premiumAmount, abps.surchargeAmount FROM Plan p JOIN AgeBandWisePremiumForPlan abps ON abps.plan = p JOIN AgeBand ab ON abps.ageBand = ab WHERE p.planName = :planName");
			query.setParameter("planName", planName);
			premiumSurchargeList = (List<Object[]>)query.getResultList();
		}catch(PersistenceException ex) {
			ex.printStackTrace();
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
		return premiumSurchargeList;
	}

}
