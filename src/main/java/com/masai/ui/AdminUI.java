package com.masai.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.masai.entity.AgeBandWisePremiumForPlan;
import com.masai.entity.Company;
import com.masai.entity.Customer;
import com.masai.entity.Plan;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;
import com.masai.service.AgeBandWisePremiumSurchargeForPlanService;
import com.masai.service.AgeBandWisePremiumSurchargeForPlanServiceImpl;
import com.masai.service.CompanyService;
import com.masai.service.CompanyServiceImpl;
import com.masai.service.CustomerService;
import com.masai.service.CustomerServiceImpl;
import com.masai.service.OrderService;
import com.masai.service.OrderServiceImpl;
import com.masai.service.PlanService;
import com.masai.service.PlanServiceImpl;

public class AdminUI {
	static void addCompany(Scanner sc) {
		//code to take company details input
		System.out.print("Enter company name ");
		String companyName = sc.next();
		System.out.print("Enter establishment year ");
		int estdYear = sc.nextInt();
		System.out.print("Enter sector type (public/private) ");
		String sectorType = sc.next();
		
		//code to create Company Entity object
		Company company = new Company(companyName, estdYear, sectorType, null);
		
		//Create an object of Service Layer here
		CompanyService companyService = new CompanyServiceImpl();
		try {
			companyService.addCompany(company);
			System.out.println("Company added successfully");
		}catch(SomeThingWentWrongException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	static void viewCompanies() {
		//Create an object of Service Layer here
		CompanyService companyService = new CompanyServiceImpl();
		try {
			List<Company> companyList = companyService.getCompanyList();
			companyList.forEach(company -> System.out.println("Id: " + company.getId() + " Company Name:" 
					+ company.getCompanyName() + " Estd Year:" + company.getEstdYear() + " Sector Type:" + company.getSectorType()));
		}catch(SomeThingWentWrongException | NoRecordFoundException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	static void updateCompanyDetails(Scanner sc) {
		//code to take company details input
		System.out.print("Enter id ");
		int id = sc.nextInt();
		System.out.print("Enter company name ");
		String companyName = sc.next();
		System.out.print("Enter establishment year ");
		int estdYear = sc.nextInt();
		System.out.print("Enter sector type (public/private) ");
		String sectorType = sc.next();
		
		//code to create Company Entity object
		Company company = new Company(companyName, estdYear, sectorType, null);
		company.setId(id);
		
		//Create an object of Service Layer here
		CompanyService companyService = new CompanyServiceImpl();
		try {
			companyService.updateCompany(company);
			System.out.println("Company updated successfully");
		}catch(SomeThingWentWrongException | NoRecordFoundException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	static void addPlan(Scanner sc) {
		//code to take company details input
		System.out.print("Enter plan name ");
		String planName = sc.next();
		System.out.print("Enter plan type (Health/Term) ");
		String planType = sc.next();
		System.out.print("Enter gst rate ");
		double gstRate = sc.nextDouble();
		System.out.print("Enter Max Coverage Age ");
		int maxCoverageAge = sc.nextInt();
		System.out.print("Enter Company Name ");
		String companyName = sc.next();		

		try {
			//Create an object of Service Layer here
			CompanyService companyService = new CompanyServiceImpl();
			Company company = companyService.getCompanyObjectByName(companyName);
			
			//Create an object of Plan for given data
			Plan plan = new Plan(company, planName, planType, gstRate, maxCoverageAge, null, null);
						
			Set<AgeBandWisePremiumForPlan> ageBandWisePremiumForPlanSet = new HashSet<AgeBandWisePremiumForPlan>();
			int ageBands[][] = {{18, 35}, {36, 50}, {51, 70}};
			for(int ageBand[] : ageBands) {
				System.out.println("For age band" + ageBand[0] + "-" + ageBand[1]);
				System.out.print("Enter premium ");
				double premiumAmount = sc.nextDouble();
				System.out.print("Enter surcharge ");
				double surchargeAmount = sc.nextDouble();
				ageBandWisePremiumForPlanSet.add(new AgeBandWisePremiumForPlan(plan, null, premiumAmount, surchargeAmount));				
			}
			
			plan.setAgeBandWisePremiumSet(ageBandWisePremiumForPlanSet);
			
			PlanService planService = new PlanServiceImpl();
			planService.addPlan(plan);
			System.out.println("Plan added successfully");
		}catch(SomeThingWentWrongException | NoRecordFoundException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	static void viewPlan() {
		PlanService planService = new PlanServiceImpl();
		try {
			List<Object[]> planList = planService.getAllPlans();
			for(Object[] plan: planList) {
				System.out.println("Plan Id: " + plan[5] + ", Plan Name: " + plan[0] + ", Plan Type: " 
						+ plan[1] + "GST Rate: " + plan[2] + ", Max Coverage Age: " + plan[3]
						 + " Company Name: " + plan[4]);
				AgeBandWisePremiumSurchargeForPlanService ab = new AgeBandWisePremiumSurchargeForPlanServiceImpl();
				List<Object[]> premiumList = ab.getPremiumSurchargeForPlan((String)plan[0]);
				for(Object[] premium: premiumList) {
					System.out.println("For age " + premium[0] + "-" + premium[1] + " Premium: " + premium[2] + " Surcharge: " + premium[3]);
				}
				System.out.println();
			}
		}catch(SomeThingWentWrongException | NoRecordFoundException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	static void updatePlan(Scanner sc) {
		//change: planName, planType, gstRate, maxCoverageAge, Company
		//code to take company details input
		System.out.print("Enter id ");
		int id = sc.nextInt();
		System.out.print("Enter plan name ");
		String planName = sc.next();
		System.out.print("Enter plan type (Health/Term) ");
		String planType = sc.next();
		System.out.print("Enter GST rate ");
		double gstRate = sc.nextDouble();
		System.out.print("Enter Max Coverage Age ");
		int maxCoverageAge = sc.nextInt();
		System.out.print("Enter Company Name ");
		String companyName = sc.next();
		
		try {
			//get company entity details from company name
			CompanyService companyService = new CompanyServiceImpl();
			Company company = companyService.getCompanyObjectByName(companyName);
			
			//Create Plan object with updated information
			Plan plan = new Plan(company, planName, planType, gstRate, maxCoverageAge, null, null);
			plan.setId(id);
			
			PlanService planService = new PlanServiceImpl();
			planService.updatePlan(plan);
			System.out.println("Plan updated successfully");
		}catch(SomeThingWentWrongException | NoRecordFoundException ex){
			System.out.println(ex.getMessage());
		}
	}
	
	static void updatePremiumAndSurcharge(Scanner sc) {
		System.out.print("Enter plan name ");
		String planName = sc.next();
		
		List<AgeBandWisePremiumForPlan> ageBandWisePremiumForPlanList = new ArrayList<>();
		int ageBands[][] = {{18, 35}, {36, 50}, {51, 70}};
		for(int ageBand[] : ageBands) {
			System.out.println("For age band" + ageBand[0] + "-" + ageBand[1]);
			System.out.print("Enter premium ");
			double premiumAmount = sc.nextDouble();
			System.out.print("Enter surcharge ");
			double surchargeAmount = sc.nextDouble();
			ageBandWisePremiumForPlanList.add(new AgeBandWisePremiumForPlan(null, null, premiumAmount, surchargeAmount));				
		}
		
		PlanService planService = new PlanServiceImpl();
		try {
			planService.updatePremiumAndSurcharge(planName, ageBandWisePremiumForPlanList);
		}catch(SomeThingWentWrongException | NoRecordFoundException ex){
			System.out.println(ex.getMessage());
		}	
	}
	
	static void makePoliciesDiscontinued() {
		try {
			OrderService orderService = new OrderServiceImpl();
			int discontinuedPolicyCount = orderService.makePoliciesDiscontinued();
			if(discontinuedPolicyCount > 0) {
				System.out.println(discontinuedPolicyCount + " policies are discontinued");
			}else {
				System.out.println("No policy to discontinued");
			}
		}catch(SomeThingWentWrongException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	static void viewAllCustomers() {
		try {
			CustomerService customerService = new CustomerServiceImpl();
			List<Object[]> customerList = customerService.getCustomerList();
			for(Object obj[]: customerList) {
				System.out.println("Name: " + obj[0] + " Username: " + obj[1] + " Date of Birth: " + obj[2] + 
						" Any Medical Condition: " + (((Integer)obj[3]).intValue() == 0?"No":"Yes") + 
						" User Active: " + (((Integer)obj[4]).intValue() == 0?"Yes":"No"));
			}
		}catch(SomeThingWentWrongException | NoRecordFoundException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	static void viewPolicyReport() {
		try {
			OrderService orderService = new OrderServiceImpl();
			List<Object[]> policyReportList = orderService.getPolicyReport();
			for(Object obj[]: policyReportList) {
				System.out.println("Total " + obj[0] + " policies : " + obj[1]);
			}
		}catch(SomeThingWentWrongException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
