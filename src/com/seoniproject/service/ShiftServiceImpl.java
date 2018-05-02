package com.seoniproject.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seoniproject.dao.ShiftDAO;
import com.seoniproject.entity.Shift;

@Service
public class ShiftServiceImpl implements ShiftService {

	@Autowired 
	private ShiftDAO theShiftDAO;
	
	@Override
	@Transactional
	public void saveShiftList(List<Shift> theShifts) {
		theShiftDAO.saveShiftList(theShifts);
		
	}

	@Override
	@Transactional
	public List<Shift> getAllShifts(Integer tollPlazaId) {
		return theShiftDAO.getAllShifts(tollPlazaId);
	}

	@Override
	@Transactional
	public Shift getShift(String now, Integer plazaId) {
		return theShiftDAO.getShift(now, plazaId);
	}

	@Override
	@Transactional
	public Shift getShiftFromDesc(String plazaId, String shiftDesc) {
		return theShiftDAO.getShiftFromDesc(plazaId, shiftDesc);
	}

	@Override
	@Transactional
	public Shift getShiftFromId(Integer shiftId) {
		// TODO Auto-generated method stub
		return theShiftDAO.getShiftFromId(shiftId);
	}

	@Override
	@Transactional
	public List<Shift> getAllShiftsActiveAndDeActive(Integer thePlazaId) {
		// TODO Auto-generated method stub
		return theShiftDAO.getAllShiftsActiveAndDeActive(thePlazaId);
	}

}
