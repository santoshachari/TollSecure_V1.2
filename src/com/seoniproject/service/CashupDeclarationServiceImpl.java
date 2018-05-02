package com.seoniproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seoniproject.dao.CashupDeclarationDAO;
import com.seoniproject.entity.CashupDeclaration;

@Service
public class CashupDeclarationServiceImpl implements CashupDeclarationService{

	@Autowired
	private CashupDeclarationDAO theCashupDeclarationDAO;
	
	@Override
	@Transactional
	public void saveDeclaration(CashupDeclaration theCashupDelaration) {
		theCashupDeclarationDAO.saveDeclaration(theCashupDelaration);
		
	}

	@Override
	@Transactional
	public CashupDeclaration getDeclarationIfExist(String checkdate, Integer laneId, Integer shiftId) {
		return theCashupDeclarationDAO.getDeclarationIfExist(checkdate, laneId, shiftId);
	}

}
