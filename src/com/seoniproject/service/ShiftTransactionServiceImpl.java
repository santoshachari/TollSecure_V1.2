package com.seoniproject.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seoniproject.dao.ShiftTransactionDAO;
import com.seoniproject.entity.ShiftTransaction;

@Service
public class ShiftTransactionServiceImpl implements ShiftTransactionService {

	@Autowired
	private ShiftTransactionDAO theShiftTransdactionDAO;
	
	@Override
	@Transactional
	public void saveOrUpdate(ShiftTransaction shiftTransaction) {
		theShiftTransdactionDAO.saveOrUpdate(shiftTransaction);
	}

	@Override
	@Transactional
	public ShiftTransaction getShiftTransactionFromUserAndPunchInTime(Integer userId, Date punchInTime) {

		return theShiftTransdactionDAO.getShiftTransactionFromUserAndPunchInTime(userId, punchInTime);
	}

	@Override
	@Transactional
	public ShiftTransaction getShiftTransactionForLaneDateShift(Integer laneId, String checkDate, Integer shiftId) {
		
		return theShiftTransdactionDAO.getShiftTransactionForLaneDateShift(laneId, checkDate, shiftId);
	}

}
