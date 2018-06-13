package com.tollsecure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tollsecure.dao.LastTollTransactionDAO;
import com.tollsecure.entity.LastTollTransaction;

@Service
public class LastTransactionServiceImpl implements LastTollTransactionService{
	
	@Autowired
	private LastTollTransactionDAO theLastTollTransactionDAO;

	@Override
	@Transactional
	public LastTollTransaction getLastTollTransactionForLane(String laneCode) {
		
		return theLastTollTransactionDAO.getLastTollTransactionForLane(laneCode);
	}

	@Override
	@Transactional
	public void saveOrUpdate(LastTollTransaction theLastTollTransaction) {
		
		theLastTollTransactionDAO.saveOrUpdate(theLastTollTransaction);
	}

}
