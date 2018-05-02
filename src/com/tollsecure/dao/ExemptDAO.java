package com.tollsecure.dao;

import com.tollsecure.entity.Exempt;

public interface ExemptDAO {

	void saveOrUpdate(Exempt exempt);

	Exempt getLastExempt(String vehicleNumber);

}
