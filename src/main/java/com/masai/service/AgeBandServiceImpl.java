package com.masai.service;

import java.util.List;

import com.masai.dao.AgeBandDAO;
import com.masai.dao.AgeBandDAOImpl;
import com.masai.entity.AgeBand;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

public class AgeBandServiceImpl implements AgeBandService {

	@Override
	public List<AgeBand> getAgeBandList() throws SomeThingWentWrongException, NoRecordFoundException {
		//Create object of AgeBandDAO
		AgeBandDAO ageBandDao = new AgeBandDAOImpl();
		return ageBandDao.getAllAgeBands();
	}

}
