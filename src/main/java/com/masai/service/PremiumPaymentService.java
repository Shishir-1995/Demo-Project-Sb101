package com.masai.service;

import java.util.List;

import com.masai.entity.PremiumPayment;

public interface PremiumPaymentService {
	List<PremiumPayment> getPremiumPaymentHistory(int orderId);
}
