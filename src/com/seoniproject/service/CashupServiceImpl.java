package com.seoniproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seoniproject.dao.CashupDAO;
import com.seoniproject.entity.Cashup;

@Service
public class CashupServiceImpl implements CashupService {

	@Autowired
	private CashupDAO theCashupDAO;
	
	@Override
	@Transactional
	public Cashup getCashUpIfExists(String checkdate, Integer laneId, Integer shiftId) {
		return theCashupDAO.getCashUpIfExists(checkdate, laneId, shiftId);
	}

	@Override
	@Transactional
	public void saveOrUpdate(Cashup cashup) {
		theCashupDAO.saveOrUpdate(cashup);
		
	}

	@Override
	@Transactional
	public List<Object[]> getTcs(String date, String plazaId, String shiftId) {
		return theCashupDAO.getTcs(date, plazaId, shiftId);
	}

}
