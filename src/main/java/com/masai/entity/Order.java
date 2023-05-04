package com.masai.entity;

import java.time.LocalDate;
import java.util.Set;

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
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id")
	private Plan plan;
	
	@Column(name = "date_of_purchase", nullable = false)
	private LocalDate dateOfPurchase;
	
	@Column(name = "expiration_date", nullable = false)
	private LocalDate expirationDate;
	
	@Column(name = "policy_status", nullable = false, length = 20)
	private String policyStatus;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private Set<PremiumPayment> premiumPaymentSet;

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(Customer customer, Plan plan, LocalDate dateOfPurchase, LocalDate expirationDate, String policyStatus,
			Set<PremiumPayment> premiumPaymentSet) {
		super();
		this.customer = customer;
		this.plan = plan;
		this.dateOfPurchase = dateOfPurchase;
		this.expirationDate = expirationDate;
		this.policyStatus = policyStatus;
		this.premiumPaymentSet = premiumPaymentSet;
	}

	public int getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Plan getPlan() {
		return plan;
	}

	public LocalDate getDateOfPurchase() {
		return dateOfPurchase;
	}

	public String getPolicyStatus() {
		return policyStatus;
	}

	public Set<PremiumPayment> getPremiumPaymentSet() {
		return premiumPaymentSet;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public void setDateOfPurchase(LocalDate dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
	}

	public void setPremiumPaymentSet(Set<PremiumPayment> premiumPaymentSet) {
		this.premiumPaymentSet = premiumPaymentSet;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
}
