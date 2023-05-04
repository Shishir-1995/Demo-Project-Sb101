package com.masai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;

@Entity
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "company_name", nullable = false, unique = true, length = 50)
	private String companyName;
	
	@Column(name = "estd_year", nullable = false)
	int estdYear;
	
	@Column(name = "sector_type", nullable = false, length = 10)
	private String sectorType;
	
	@OneToMany(cascade =  CascadeType.ALL, mappedBy = "company")
	Set<Plan> planSet;

	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Company(String companyName, int estdYear, String sectorType, Set<Plan> planSet) {
		super();
		this.companyName = companyName;
		this.estdYear = estdYear;
		this.sectorType = sectorType;
		this.planSet = planSet;
	}

	public int getId() {
		return id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public int getEstdYear() {
		return estdYear;
	}

	public String getSectorType() {
		return sectorType;
	}

	public Set<Plan> getPlanSet() {
		return planSet;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setEstdYear(int estdYear) {
		this.estdYear = estdYear;
	}

	public void setSectorType(String sectorType) {
		this.sectorType = sectorType;
	}

	public void setPlanSet(Set<Plan> planSet) {
		this.planSet = planSet;
	}

	@Override
	public int hashCode() {
		return Objects.hash(companyName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		return Objects.equals(companyName, other.companyName);
	}
}
