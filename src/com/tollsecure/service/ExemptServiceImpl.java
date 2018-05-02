package com.tollsecure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tollsecure.dao.ExemptDAO;
import com.tollsecure.entity.Exempt;

@Service
public class ExemptServiceImpl implements ExemptService {

	@Autowired
	private ExemptDAO theExemptDAO;
	
	@Override
	@Transactional
	public void saveOrUpdate(Exempt exempt) {
		theExemptDAO.saveOrUpdate(exempt);
		
	}

	@Override
	@Transactional
	public Exempt getLastExempt(String vehicleNumber) {
		// TODO Auto-generated method stub
		return theExemptDAO.getLastExempt(vehicleNumber);
	}
	
}
