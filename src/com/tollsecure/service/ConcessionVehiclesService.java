package com.tollsecure.service;

import com.tollsecure.entity.ConcessionVehicles;

public interface ConcessionVehiclesService {

	void saveVehicle(ConcessionVehicles theConcessionVehicle);

	ConcessionVehicles getLastRecord(String vnum);

}
