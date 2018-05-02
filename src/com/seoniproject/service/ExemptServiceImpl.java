package com.seoniproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seoniproject.dao.ExemptDAO;
import com.seoniproject.entity.Exempt;

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
