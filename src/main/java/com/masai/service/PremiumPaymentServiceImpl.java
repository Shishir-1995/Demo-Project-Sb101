package com.masai.service;

import java.util.List;

import com.masai.dao.PremiumPaymentDAO;
import com.masai.dao.PremiumPaymentDAOImpl;
import com.masai.entity.PremiumPayment;

public class PremiumPaymentServiceImpl implements PremiumPaymentService {

	@Override
	public List<PremiumPayment> getPremiumPaymentHistory(int orderId) {
		PremiumPaymentDAO pp = new PremiumPaymentDAOImpl();
		return pp.getPremiumPaymentHistory(orderId);
		
	}

}
