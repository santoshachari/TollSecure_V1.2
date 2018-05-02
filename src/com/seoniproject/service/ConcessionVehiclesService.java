package com.seoniproject.service;

import com.seoniproject.entity.ConcessionVehicles;

public interface ConcessionVehiclesService {

	void saveVehicle(ConcessionVehicles theConcessionVehicle);

	ConcessionVehicles getLastRecord(String vnum);

}
