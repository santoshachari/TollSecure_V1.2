package com.tollsecure.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tollsecure.dao.TollConfigDAO;
import com.tollsecure.entity.TollConfig;

@Service
public class TollConfigServiceImpl implements TollConfigService {

	//need to inject TollConfigDAO
	@Autowired
	private TollConfigDAO tollConfigDAO;
	
	@Override
	@Transactional
	public List<TollConfig> getTollConfigs() {
		return tollConfigDAO.getTollConfigs();
	}

	@Override
	@Transactional
	public HashMap<String, String> getRate(String vClass, String jType, Integer plazaId, Date now) {
		return tollConfigDAO.getRate(vClass, jType, plazaId, now);
	}

	@Override
	@Transactional
	public List<String> getUniqueJourneyTypes() {
		return tollConfigDAO.getUniqueJoutneyTypes();
	}

	@Override
	@Transactional
	public Map<String, String> getUniqueVehicleClasses() {
		return tollConfigDAO.getUniqueVehicleClasses();
	}

	@Override
	@Transactional
	public String getLastTrollTransactionVehicleClass(Integer vehicleClassId) {
		return tollConfigDAO.getLastTrollTransactionVehicleClass(vehicleClassId);
	}

	@Override
	@Transactional
	public List<TollConfig> getAllConfigs(Integer plazaId) {
		return tollConfigDAO.getAllConfigs(plazaId);
	}

	@Override
	@Transactional
	public TollConfig getTollConfig(Integer vehicleClassId) {
		return tollConfigDAO.getTollConfig(vehicleClassId);
	}

	@Override
	@Transactional
	public TollConfig getTollConfigForUpdate(Integer plazaId, String vehicleClass, String journeyType) {
		return tollConfigDAO.getTollConfigForUpdate(plazaId, vehicleClass, journeyType);
	}

	@Override
	@Transactional
	public void saveTollConfig(TollConfig tollConfig) {
		tollConfigDAO.saveTollConfig(tollConfig);
		
	}

	@Override
	@Transactional
	public HashMap<String, String> getRate(String vClass, String jType, Integer plazaId, String startDate,
			String endDate) {
		return tollConfigDAO.getRate(vClass, jType, plazaId, startDate, endDate);
	}

	@Override
	@Transactional
	public List<TollConfig> getTollConfigForUpdate(Integer plazaId, String vehicleClass, String journeyType, String startDate,
			String endDate) {		
		 return tollConfigDAO.getTollConfigForUpdate(plazaId, vehicleClass, journeyType, startDate, endDate);
	}

	@Override
	@Transactional
	public Integer getVehicleClassId(String vehicleClass, String laneId) {
		return tollConfigDAO.getVehicleClassId(vehicleClass, laneId);
	}

	@Override
	@Transactional
	public Float getRateFromClassAndJType(String vehicleClass, String journeyType) {
		return tollConfigDAO.getRateFromClassAndJType(vehicleClass, journeyType);
	}

	@Override
	@Transactional
	public Integer getVehicleClassIdFrom(String vehicleClass, String journeyType) {
		return tollConfigDAO.getVehicleClassIdFrom(vehicleClass, journeyType);
	}

}
