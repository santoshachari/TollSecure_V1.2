package com.tollsecure.service;

import java.util.Date;

import com.tollsecure.entity.ShiftTransaction;

public interface ShiftTransactionService {

	void saveOrUpdate(ShiftTransaction shiftTransaction);

	ShiftTransaction getShiftTransactionFromUserAndPunchInTime(Integer userId, Date punchInTime);

	ShiftTransaction getShiftTransactionForLaneDateShift(Integer laneId, String checkDate, Integer shiftId);

}
