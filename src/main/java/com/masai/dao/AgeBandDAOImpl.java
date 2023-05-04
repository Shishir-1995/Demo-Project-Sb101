package com.masai.dao;

import java.util.List;
import com.masai.entity.AgeBand;
import com.masai.exception.SomeThingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class AgeBandDAOImpl implements AgeBandDAO {
	@Override
	public List<AgeBand> getAllAgeBands() throws SomeThingWentWrongException{
		EntityManager em = null;
		List<AgeBand> ageBandList = null;
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("FROM AgeBand ab");
			ageBandList = (List<AgeBand>)query.getResultList();
		}catch(IllegalArgumentException ex) {
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		}finally{
			em.close();
		}
		return ageBandList;
	}

}
