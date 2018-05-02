package com.seoniproject.service;

import java.util.Date;
import java.util.List;

import com.seoniproject.entity.Shift;

public interface ShiftService {

	void saveShiftList(List<Shift> theShifts);

	List<Shift> getAllShifts(Integer tollPlazaId);

	Shift getShift(String sCertDate, Integer plazaId);

	Shift getShiftFromDesc(String plazaId, String shiftDesc);

	Shift getShiftFromId(Integer shiftId);

	List<Shift> getAllShiftsActiveAndDeActive(Integer thePlazaId);

}
