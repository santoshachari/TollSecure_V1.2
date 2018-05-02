package com.seoniproject.service;

import com.seoniproject.entity.Exempt;

public interface ExemptService {

	void saveOrUpdate(Exempt exempt);

	Exempt getLastExempt(String vehicleNumber);

}
