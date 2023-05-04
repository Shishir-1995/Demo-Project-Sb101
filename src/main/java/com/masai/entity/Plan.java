package com.masai.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class Plan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;
	
	@Column(name = "plan_name", nullable = false, length = 50)
	private String planName;
	
	@Column(name = "plan_type", nullable = false, length = 10)
	private String planType;
	
	@Column(name = "gst_rate", nullable = false)
	private double gstRate;
	
	@Column(name = "max_coverage_age", nullable = false)
	private int maxCoverageAge;
	
	@OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
	private Set<AgeBandWisePremiumForPlan> ageBandWisePremiumSet;
	
	@OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
	private Set<Order> orderSet;

	public Plan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Plan(Company company, String planName, String planType, double gstRate, int maxCoverageAge,
			Set<AgeBandWisePremiumForPlan> ageBandWisePremiumSet, Set<Order> orderSet) {
		super();
		this.company = company;
		this.planName = planName;
		this.planType = planType;
		this.gstRate = gstRate;
		this.maxCoverageAge = maxCoverageAge;
		this.ageBandWisePremiumSet = ageBandWisePremiumSet;
		this.orderSet = orderSet;
	}

	public int getId() {
		return id;
	}

	public Company getCompany() {
		return company;
	}

	public String getPlanName() {
		return planName;
	}

	public String getPlanType() {
		return planType;
	}

	public double getGstRate() {
		return gstRate;
	}

	public int getMaxCoverageAge() {
		return maxCoverageAge;
	}

	public Set<Order> getOrder() {
		return orderSet;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public void setGstRate(double gstRate) {
		this.gstRate = gstRate;
	}

	public void setMaxCoverageAge(int maxCoverageAge) {
		this.maxCoverageAge = maxCoverageAge;
	}

	public void setOrder(Set<Order> orderSet) {
		this.orderSet = orderSet;
	}

	public Set<AgeBandWisePremiumForPlan> getAgeBandWisePremiumSet() {
		return ageBandWisePremiumSet;
	}

	public Set<Order> getOrderSet() {
		return orderSet;
	}

	public void setAgeBandWisePremiumSet(Set<AgeBandWisePremiumForPlan> ageBandWisePremiumSet) {
		this.ageBandWisePremiumSet = ageBandWisePremiumSet;
	}

	public void setOrderSet(Set<Order> orderSet) {
		this.orderSet = orderSet;
	}
}
