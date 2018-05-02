package com.tollsecure.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tollsecure.entity.TollConfig;

public interface TollConfigDAO {

	public List<TollConfig> getTollConfigs();

	public List<String> getUniqueJoutneyTypes();

	public Map<String, String> getUniqueVehicleClasses();

	public String getLastTrollTransactionVehicleClass(Integer vehicleClassId);

	public List<TollConfig> getAllConfigs(Integer plazaId);

	public TollConfig getTollConfig(Integer vehicleClassId);

	public TollConfig getTollConfigForUpdate(Integer plazaId, String vehicleClass, String journeyType);

	public void saveTollConfig(TollConfig tollConfig);

	public HashMap<String, String> getRate(String vClass, String jType, Integer plazaId, Date now);

	public HashMap<String, String> getRate(String vClass, String jType, Integer plazaId, String startDate,
			String endDate);

	public List<TollConfig> getTollConfigForUpdate(Integer plazaId, String vehicleClass, String journeyType, String startDate,
			String endDate);

	public Integer getVehicleClassId(String vehicleClass, String laneId);

	public Float getRateFromClassAndJType(String vehicleClass, String journeyType);

	public Integer getVehicleClassIdFrom(String vehicleClass, String journeyType);

}
