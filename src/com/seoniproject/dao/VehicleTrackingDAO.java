package com.seoniproject.dao;

import com.seoniproject.entity.VehicleTracking;

public interface VehicleTrackingDAO {

	VehicleTracking getVehicleTrack(String vnum);

	void saveVehicleTrack(VehicleTracking vehicleTracking);

	boolean getReturnStatus(String vehicleNumber, Integer laneId, Integer userId);

	boolean checkButNotUpdateReturn(String vnumber, String laneId);

}
