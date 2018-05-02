package com.seoniproject.dao;

import java.util.Date;

import com.seoniproject.entity.ShiftTransaction;

public interface ShiftTransactionDAO {

	void saveOrUpdate(ShiftTransaction shiftTransaction);

	ShiftTransaction getShiftTransactionFromUserAndPunchInTime(Integer userId, Date punchInTime);

	ShiftTransaction getShiftTransactionForLaneDateShift(Integer laneId, String checkDate, Integer shiftId);

}
