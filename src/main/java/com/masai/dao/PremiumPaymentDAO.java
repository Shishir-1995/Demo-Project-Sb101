package com.masai.dao;

import java.util.List;

import com.masai.entity.PremiumPayment;

public interface PremiumPaymentDAO {
	List<PremiumPayment> getPremiumPaymentHistory(int orderId);
}
