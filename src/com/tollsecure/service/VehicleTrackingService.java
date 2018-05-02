package com.tollsecure.service;

import javax.servlet.http.HttpSession;

import com.tollsecure.entity.VehicleTracking;

public interface VehicleTrackingService {

	VehicleTracking getVehicleTrack(String vnum);

	void saveVehicleTrack(VehicleTracking vehicleTracking);

	boolean getReturnStatus(String vehicleNumber, Integer laneId, Integer userId);

	boolean checkButNotUpdateReturn(String vnumber, String laneId);

}
