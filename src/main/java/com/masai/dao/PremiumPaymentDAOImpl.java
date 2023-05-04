package com.masai.dao;

import java.util.List;

import javax.xml.transform.ErrorListener;

import com.masai.entity.PremiumPayment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class PremiumPaymentDAOImpl implements PremiumPaymentDAO {

	@Override
	public List<PremiumPayment> getPremiumPaymentHistory(int orderId) {
		EntityManager em = null;
		List<PremiumPayment> list = null;
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT pp FROM PremiumPayment pp WHERE pp.order.id = :id ORDER BY pp.paymentDate DESC");
			query.setParameter("id", orderId);
			list = query.getResultList();
		}finally{
			em.clear();
		}
		return list;
	}

}
