package com.masai.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 50)	//this is for not null
	private String name;
	
	@Column(unique = true, length = 50, nullable = false)	//this is for making it unique
	private String username;
	
	@Column(nullable = false, length = 50)
	private String password;
	
	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;
	
	@Column(name = "has_medical_condition", nullable = false)
	private int hasMedicalCondition;

	@Column(name = "is_deleted", nullable = false)
	private int isDeleted;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private Set<Order> orderSet;

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(String name, String username, String password, LocalDate dateOfBirth, int hasMedicalCondition,
			Set<Order> orderSet) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.hasMedicalCondition = hasMedicalCondition;
		this.orderSet = orderSet;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public int getHasMedicalCondition() {
		return hasMedicalCondition;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setHasMedicalCondition(int hasMedicalCondition) {
		this.hasMedicalCondition = hasMedicalCondition;
	}

	public Set<Order> getOrderSet() {
		return orderSet;
	}

	public void setOrderSet(Set<Order> orderSet) {
		this.orderSet = orderSet;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
}
