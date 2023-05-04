package com.masai.service;

import java.util.List;

import com.masai.dao.CompanyDAO;
import com.masai.dao.CompanyDAOImpl;
import com.masai.entity.Company;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

public class CompanyServiceImpl implements CompanyService {
	@Override
	public void addCompany(Company company) throws SomeThingWentWrongException {
		//Create an object of DAO class here
		CompanyDAO companyDAO = new CompanyDAOImpl();
		companyDAO.addCompany(company);
	}
	
	@Override
	public List<Company> getCompanyList() throws SomeThingWentWrongException, NoRecordFoundException{
		//Create an object of DAO class here
		CompanyDAO companyDAO = new CompanyDAOImpl();
		return companyDAO.getCompanyList();
	}
	
	@Override
	public void updateCompany(Company company) throws SomeThingWentWrongException, NoRecordFoundException{
		//Create an object of DAO class here
		CompanyDAO companyDAO = new CompanyDAOImpl();
		companyDAO.updateCompany(company);
	}
	
	@Override
	public Company getCompanyObjectByName(String companyName) throws SomeThingWentWrongException, NoRecordFoundException{
		//Create an object of DAO class here
		CompanyDAO companyDAO = new CompanyDAOImpl();
		return companyDAO.getCompanyObjectByName(companyName);
	}

}
