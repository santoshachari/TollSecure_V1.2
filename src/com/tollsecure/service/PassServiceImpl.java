package com.tollsecure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tollsecure.dao.PassDAO;
import com.tollsecure.entity.Pass;
import com.tollsecure.entity.PassConfiguration;

@Service
public class PassServiceImpl implements PassService {

	@Autowired
	private PassDAO thePassDAO;
	
	@Override
	@Transactional
	public PassConfiguration getPassConfiguration(String plazaId, String effectiveTo, String vehicleClass,
			String passType) {
		return thePassDAO.getPassConfiguration(plazaId, effectiveTo, vehicleClass, passType);
	}

	@Override
	@Transactional
	public List<PassConfiguration> getAllActivePassConfigurations(String tollPlazaId, String effectiveTo1,
			String vehicleClass, String passType) {
		return thePassDAO.getAllActivePassConfigurations(tollPlazaId, effectiveTo1, vehicleClass, passType);
	}

	@Override
	@Transactional
	public void saveOrUpdate(PassConfiguration passConf) {
		thePassDAO.saveOrUpdate(passConf);
	}

	@Override
	@Transactional
	public Pass getSavedOne(String vehicleNumber) {
		// TODO Auto-generated method stub
		return thePassDAO.getSavedOne(vehicleNumber);
	}

	@Override
	@Transactional
	public List<Pass> getPasses(String vehicleNumber) {
		// TODO Auto-generated method stub
		return thePassDAO.getPasses(vehicleNumber);
	}

	@Override
	@Transactional
	public Pass lastPassForTheVehicle(String vehicleNumber) {
		// TODO Auto-generated method stub
		return thePassDAO.lastPassForTheVehicle(vehicleNumber);
	}

}
