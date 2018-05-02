package com.seoniproject.dao;

import java.util.List;

import com.seoniproject.entity.Pass;
import com.seoniproject.entity.PassConfiguration;

public interface PassDAO {

	PassConfiguration getPassConfiguration(String plazaId, String effectiveTo, String vehicleClass, String passType);

	List<PassConfiguration> getAllActivePassConfigurations(String tollPlazaId, String effectiveTo1, String vehicleClass,
			String passType);

	void saveOrUpdate(PassConfiguration passConf);

	Pass getSavedOne(String vehicleNumber);

	List<Pass> getPasses(String vehicleNumber);

	Pass lastPassForTheVehicle(String vehicleNumber);

}
