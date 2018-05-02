package com.seoniproject.dao;

import com.seoniproject.entity.Exempt;

public interface ExemptDAO {

	void saveOrUpdate(Exempt exempt);

	Exempt getLastExempt(String vehicleNumber);

}
