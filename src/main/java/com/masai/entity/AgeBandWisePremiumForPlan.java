package com.masai.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "Age_Band_Wise_premium_for_plan")
public class AgeBandWisePremiumForPlan {
	@EmbeddedId
	private PlanAgeBandId pid = new PlanAgeBandId();
	
	@ManyToOne
	@MapsId("planId")
	private Plan plan;
	
	@ManyToOne
	@MapsId("ageBandId")
	private AgeBand ageBand;

	@Column(name = "premium_amount", nullable = false)
	private double premiumAmount;
	
	@Column(name = "surcharge_amount", nullable = false)
	private double surchargeAmount;
	
	public AgeBandWisePremiumForPlan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AgeBandWisePremiumForPlan(Plan plan, AgeBand ageBand, double premiumAmount, double surchargeAmount) {
		super();
		this.plan = plan;
		this.ageBand = ageBand;
		this.premiumAmount = premiumAmount;
		this.surchargeAmount = surchargeAmount;
	}
	
	public PlanAgeBandId getPid() {
		return pid;
	}

	public Plan getPlan() {
		return plan;
	}

	public AgeBand getAgeBand() {
		return ageBand;
	}

	public double getPremiumAmount() {
		return premiumAmount;
	}

	public double getSurchargeAmount() {
		return surchargeAmount;
	}

	public void setPid(PlanAgeBandId pid) {
		this.pid = pid;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public void setAgeBand(AgeBand ageBand) {
		this.ageBand = ageBand;
	}

	public void setPremiumAmount(double premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public void setSurchargeAmount(double surchargeAmount) {
		this.surchargeAmount = surchargeAmount;
	}

	public static class PlanAgeBandId implements Serializable{
		private static final long serialVersionUID = 1L;
	    private int planId;
	    private int ageBandId;
		public PlanAgeBandId() {
			super();
			// TODO Auto-generated constructor stub
		}
		public PlanAgeBandId(int planId, int ageBandId) {
			super();
			this.planId = planId;
			this.ageBandId = ageBandId;
		}
		public int getPlanId() {
			return planId;
		}
		public int getAgeBandId() {
			return ageBandId;
		}
		public void setPlanId(int planId) {
			this.planId = planId;
		}
		public void setAgeBandId(int ageBandId) {
			this.ageBandId = ageBandId;
		}
		@Override
		public int hashCode() {
			return Objects.hash(ageBandId, planId);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PlanAgeBandId other = (PlanAgeBandId) obj;
			return ageBandId == other.ageBandId && planId == other.planId;
		}
	}
}
