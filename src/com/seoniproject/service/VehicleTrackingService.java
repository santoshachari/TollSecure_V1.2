package com.seoniproject.service;

import javax.servlet.http.HttpSession;

import com.seoniproject.entity.VehicleTracking;

public interface VehicleTrackingService {

	VehicleTracking getVehicleTrack(String vnum);

	void saveVehicleTrack(VehicleTracking vehicleTracking);

	boolean getReturnStatus(String vehicleNumber, Integer laneId, Integer userId);

	boolean checkButNotUpdateReturn(String vnumber, String laneId);

}
