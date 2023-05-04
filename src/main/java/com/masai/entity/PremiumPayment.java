package com.masai.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "premium_payment")
public class PremiumPayment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	
	@Column(name = "amount", nullable = false)
	private double amount;
	
	@Column(name = "payment_date", nullable = false)
	private LocalDate paymentDate;
	
	@Column(name = "mode_of_payment", nullable = false)
	private String modeOfPayment;

	public PremiumPayment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PremiumPayment(Order order, double amount, LocalDate paymentDate, String modeOfPayment) {
		super();
		this.order = order;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.modeOfPayment = modeOfPayment;
	}

	public int getId() {
		return id;
	}

	public Order getOrder() {
		return order;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
}
