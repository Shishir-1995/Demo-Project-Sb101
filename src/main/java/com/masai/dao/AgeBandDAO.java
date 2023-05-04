package com.masai.dao;

import java.util.List;

import com.masai.entity.AgeBand;
import com.masai.exception.SomeThingWentWrongException;

public interface AgeBandDAO {
	public List<AgeBand> getAllAgeBands() throws SomeThingWentWrongException;
}
