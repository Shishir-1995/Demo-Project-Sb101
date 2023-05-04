package com.masai.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.masai.entity.Customer;
import com.masai.entity.LoggedInUserId;
import com.masai.entity.Order;
import com.masai.entity.PremiumPayment;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;
import com.masai.service.CustomerService;
import com.masai.service.CustomerServiceImpl;
import com.masai.service.OrderService;
import com.masai.service.OrderServiceImpl;
import com.masai.service.PlanService;
import com.masai.service.PlanServiceImpl;
import com.masai.service.PremiumPaymentService;
import com.masai.service.PremiumPaymentServiceImpl;

public class CustomerUI {
	static void customerRegistration(Scanner sc) {
		//code to take input
		System.out.print("Enter name ");
		String name = sc.next();
		System.out.print("Enter username ");
		String username = sc.next();
		System.out.print("Enter password ");
		String password = sc.next();
		System.out.print("Enter date of birth ");
		LocalDate dateOfBirth = LocalDate.parse(sc.next());
		System.out.print("Any medical condition [y/n] ");
		int hasMedicalCondition = sc.next().toLowerCase().charAt(0) == 'y'?1:0;
		
		//Create an object of customer
		Customer customer = new Customer(name, username, password, dateOfBirth, hasMedicalCondition, null);
		
		try {
			//Create an object of CustomerService
			CustomerService customerService = new CustomerServiceImpl();
			customerService.addCustomer(customer);
			System.out.println("Customer added successfully");
		}catch(SomeThingWentWrongException ex) {
			System.out.println(ex);
		}
	}
	
	static void displayUserMenu() {
		System.out.println("1. View All Plan");
		System.out.println("2. Calculate Policy Premium");
		System.out.println("3. Purchase a new Policy");
		System.out.println("4. View Purchased Policy along with payment history");
		System.out.println("5. Renew Existing Policy");
		System.out.println("6. Change Password");
		System.out.println("7. Delete Account");
		System.out.println("0. Logout");
	}
	
	static void userMenu(Scanner sc) {
		int choice = 0;
		do {
			displayUserMenu();
			System.out.print("Enter selection ");
			choice = sc.nextInt();
    		switch(choice) {
    			case 1:
    				//this code is same as we have used on the admin side
    				//so we are using here as it is
    				AdminUI.viewPlan();
    				break;
    			case 2:
    				calculatePolicyPremium(sc);
    				break;
    			case 3:
    				//code to purchase a new policy
    				purchaseNewPolicy(sc);
    				break;
    			case 4:
    				//code to view policies purchased by logged in user
    				viewPurchasedPolicies();
    				break;
    			case 5:
    				renewPolicies(sc);
    				break;
    			case 6:
    				changePassword(sc);
    				break;
    			case 7:
    				deleteAccount(sc);
    				System.out.println("Logging you out");
    				choice = 0;
    			case 0:
    				LoggedInUserId.loggedInUserId = -1;	//-1 id cannot belong to any customer
    				System.out.println("Bye Bye User");
    				break;
    			default:
    				System.out.println("Invalid Selection, try again");
    		}
    	}while(choice != 0);
	}
	
	static void userLogin(Scanner sc) {
		System.out.print("Enter username ");
		String username = sc.next();
		System.out.print("Enter password ");
		String password = sc.next();
		try {
			CustomerService customerService = new CustomerServiceImpl();
			customerService.login(username, password);
			userMenu(sc);
		}catch(NoRecordFoundException | SomeThingWentWrongException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	static void changePassword(Scanner sc) {
		System.out.print("Enter old password ");
		String oldPassword = sc.next();
		System.out.print("Enter new password ");
		String newPassword = sc.next();
		System.out.print("Re-Enter new password ");
		String reEnterNewPassword = sc.next();
		
		//Check if new password is correct
		if(!newPassword.equals(reEnterNewPassword)) {
			System.out.println("New password and Re-Entered password mismtached");
			return;
		}else if(newPassword.equals(oldPassword)) {
			System.out.println("New password and old password must be different");
			return;
		}
		
		try {
			CustomerService customerService = new CustomerServiceImpl();
			customerService.changePassword(oldPassword, reEnterNewPassword);
			System.out.println("Password updated");
		}catch(SomeThingWentWrongException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	static void deleteAccount(Scanner sc) {
		System.out.print("Are you sure you want to delete your account?[y/n] ");
		char choice = sc.next().toLowerCase().charAt(0);
		if(choice == 'y') {
			try {
				CustomerService customerService = new CustomerServiceImpl();
				customerService.deleteAccount();
				System.out.println("Its really sad to see you go, As per your request account is deleted");			
			}catch(SomeThingWentWrongException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	static void purchaseNewPolicy(Scanner sc) {
		System.out.print("Enter plan name ");
		String planName = sc.next();
		System.out.print("Enter mode of Payment [Cash/Cheque/Online] ");
		String modeOfPayment = sc.next();
		try {
			PlanService planService = new PlanServiceImpl();
			Double premiumAmount = planService.getPremiumAmount(planName).get(3);
			//you are here means plan-name, age of customer is already verified (By above method calling)
			OrderService orderService = new OrderServiceImpl();
			orderService.purchasePolicy(planName, modeOfPayment, premiumAmount);
			System.out.println("Policy purchased Successfully");
		}catch(SomeThingWentWrongException | NoRecordFoundException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	static void viewPurchasedPolicies() {
		try {
			OrderService orderService = new OrderServiceImpl();
			List<Object[]> policyList = orderService.getPurchasedPolicies();
			for(Object obj[]: policyList) {
				PremiumPaymentService pp = new PremiumPaymentServiceImpl();  
				List<PremiumPayment> list = pp.getPremiumPaymentHistory((Integer)obj[1]);
				System.out.println("Plan name " + obj[0] + " Policy Status " + obj[4] + " Date of Commencement " + obj[2] + " Expiration Date " + obj[3]);
				for(PremiumPayment currentPP : list)
					System.out.println("Premium Amount: " + currentPP.getAmount() + " Model of Payment" + currentPP.getModeOfPayment() + " Date of Payment " + currentPP.getPaymentDate());
			}
		}catch(SomeThingWentWrongException | NoRecordFoundException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	static void renewPolicies(Scanner sc) {
		try {
			OrderService orderService = new OrderServiceImpl();
			List<Object[]> policyList = orderService.getPoliciesForRenewal();
			for(Object obj[]: policyList) {
				System.out.println("Order Id: " + obj[0] + ", Expiration date: " + obj[1] + ", Plan Name: " + obj[2]);
			}
			
			System.out.println("Enter Order Id of the policy to be renewed ");
			int orderId = sc.nextInt();
			
			String planName = null;
			LocalDate nextToExpirationDate = null;
			
			for(Object obj[]: policyList) {
				if(((Integer)obj[0]).intValue() == orderId) {
					planName = (String) obj[2];
					nextToExpirationDate = ((LocalDate)obj[1]).plusDays(1);
					break;
				}
			}
			
			if(planName == null || nextToExpirationDate == null) {
				System.out.println("Invalid order Id");
				return;
			}
			
			//you have to calculate user's age on the day of policy expiration + 1
			//use this age to compute premium for renewal of policy
			PlanService planService = new PlanServiceImpl();
			List<Double> list = planService.getPremiumAmount(planName, nextToExpirationDate);
			System.out.println("Base Premium " + list.get(0));
			System.out.println("Surchage " + list.get(1));
			System.out.println("Tax(%) " + list.get(2));
			System.out.println("Total " + list.get(3));
			
			System.out.print("Do you wish to renew?[y/n] ");
			char choice = sc.next().toLowerCase().charAt(0);
			
			if(choice != 'y') {
				System.out.println("You have to renew policy within 30 day from date of expiration "
						+ "to avoid discontinuation of policy");
				return;
			}
			//you are here means user want to continue the policy
			//update the expiration date of policy
			
			LocalDate newExpirationDate = nextToExpirationDate.plusYears(1);
			double premiumAmount = list.get(3);
			System.out.print("Enter mode of payment [Cash/Cheque/Online] ");
			String modeOfPayment = sc.next();
			
			orderService.renewPolicy(orderId, newExpirationDate, premiumAmount, modeOfPayment);
			System.out.println("policy Renewed");
		}catch(SomeThingWentWrongException | NoRecordFoundException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	static void calculatePolicyPremium(Scanner sc) {
		System.out.print("Enter plan name ");
		String planName = sc.next();
		
		try {
			PlanService planService = new PlanServiceImpl();
			List<Double> list = planService.getPremiumAmount(planName);
			System.out.println("Base Premium " + list.get(0));
			System.out.println("Surchage " + list.get(1));
			System.out.println("Tax(%) " + list.get(2));
			System.out.println("Total " + list.get(3));
		}catch(NoRecordFoundException | SomeThingWentWrongException ex) {
			System.out.println(ex);
		}
	}
}