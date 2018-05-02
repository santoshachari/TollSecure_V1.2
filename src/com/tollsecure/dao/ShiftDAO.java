package com.tollsecure.dao;

import java.util.Date;
import java.util.List;

import com.tollsecure.entity.Shift;

public interface ShiftDAO {

	void saveShiftList(List<Shift> theShifts);

	List<Shift> getAllShifts(Integer tollPlazaId);

	Shift getShift(String now, Integer plazaId);

	Shift getShiftFromDesc(String plazaId, String shiftDesc);

	Shift getShiftFromId(Integer shiftId);

	List<Shift> getAllShiftsActiveAndDeActive(Integer thePlazaId);

}
