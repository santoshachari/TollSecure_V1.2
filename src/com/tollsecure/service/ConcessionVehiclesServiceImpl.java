package com.tollsecure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tollsecure.dao.ConcessionVehiclesDAO;
import com.tollsecure.entity.ConcessionVehicles;

@Service
public class ConcessionVehiclesServiceImpl implements ConcessionVehiclesService {

	@Autowired
	private ConcessionVehiclesDAO theConcessionVehiclesDAO;

	@Override
	@Transactional
	public void saveVehicle(ConcessionVehicles theConcessionVehicle) {
		theConcessionVehiclesDAO.saveVehicle(theConcessionVehicle);
	}

	@Override
	@Transactional
	public ConcessionVehicles getLastRecord(String vnum) {
		return theConcessionVehiclesDAO.getLastRecord(vnum);
	}
}
