package com.tollsecure.service;

import com.tollsecure.entity.Exempt;

public interface ExemptService {

	void saveOrUpdate(Exempt exempt);

	Exempt getLastExempt(String vehicleNumber);

}
