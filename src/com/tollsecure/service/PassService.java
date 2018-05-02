package com.tollsecure.service;

import java.util.List;

import com.tollsecure.entity.Pass;
import com.tollsecure.entity.PassConfiguration;

public interface PassService {

	PassConfiguration getPassConfiguration(String plazaId, String effectiveTo, String vehicleClass, String passType);

	List<PassConfiguration> getAllActivePassConfigurations(String tollPlazaId, String effectiveTo1, String vehicleClass,
			String passType);

	void saveOrUpdate(PassConfiguration passConf);

	Pass getSavedOne(String vehicleNumber);

	List<Pass> getPasses(String vehicleNumber);

	Pass lastPassForTheVehicle(String vehicleNumber);

}
