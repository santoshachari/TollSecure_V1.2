package com.tollsecure.dao;

import com.tollsecure.entity.ConcessionVehicles;

public interface ConcessionVehiclesDAO {

	void saveVehicle(ConcessionVehicles theConcessionVehicle);

	ConcessionVehicles getLastRecord(String vnum);

}
