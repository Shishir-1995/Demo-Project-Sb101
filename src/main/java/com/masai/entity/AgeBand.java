package com.masai.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "age_band")
public class AgeBand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "start_age", nullable = false)
	private int startAge;
	
	@Column(name = "end_age", nullable = false)
	private int endAge;
	
	@OneToMany(mappedBy = "ageBand", cascade = CascadeType.ALL)
	private Set<AgeBandWisePremiumForPlan> ageBandSet;

	public AgeBand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AgeBand(int startAge, int endAge, Set<AgeBandWisePremiumForPlan> ageBandSet) {
		super();
		this.startAge = startAge;
		this.endAge = endAge;
		this.ageBandSet = ageBandSet;
	}

	public int getId() {
		return id;
	}

	public int getStartAge() {
		return startAge;
	}

	public int getEndAge() {
		return endAge;
	}

	public Set<AgeBandWisePremiumForPlan> getAgeBandSet() {
		return ageBandSet;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setStartAge(int startAge) {
		this.startAge = startAge;
	}

	public void setEndAge(int endAge) {
		this.endAge = endAge;
	}

	public void setAgeBandSet(Set<AgeBandWisePremiumForPlan> ageBandSet) {
		this.ageBandSet = ageBandSet;
	}
}
