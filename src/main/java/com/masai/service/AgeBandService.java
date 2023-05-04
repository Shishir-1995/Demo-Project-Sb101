package com.masai.service;

import java.util.List;

import com.masai.entity.AgeBand;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

public interface AgeBandService {
	List<AgeBand> getAgeBandList() throws SomeThingWentWrongException, NoRecordFoundException;
}
