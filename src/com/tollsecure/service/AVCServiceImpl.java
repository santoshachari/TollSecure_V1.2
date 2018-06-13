package com.tollsecure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tollsecure.dao.AVCDAO;
import com.tollsecure.entity.AVC;
import com.tollsecure.entity.LastTollTransaction;

@Service
public class AVCServiceImpl implements AVCService {

	@Autowired
	private AVCDAO theAVCDAO;
	
	@Override
	@Transactional
	public boolean checkForTransactionIdIfExists(Integer transactionId) {
		
		return theAVCDAO.checkForTransactionIdIfExists(transactionId);
	}

	@Override
	@Transactional
	public void save(AVC theAVC) {
		theAVCDAO.save(theAVC);	
	}

}
