package com.seoniproject.dao;

import com.seoniproject.entity.ConcessionVehicles;

public interface ConcessionVehiclesDAO {

	void saveVehicle(ConcessionVehicles theConcessionVehicle);

	ConcessionVehicles getLastRecord(String vnum);

}
