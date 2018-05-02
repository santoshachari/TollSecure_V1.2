package com.seoniproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seoniproject.dao.VehicleTrackingDAO;
import com.seoniproject.entity.VehicleTracking;

@Service
public class VehicleTrackingServiceImpl implements VehicleTrackingService {

	@Autowired
	private VehicleTrackingDAO theVehicleTrackingDAO;
	
	@Override
	@Transactional
	public VehicleTracking getVehicleTrack(String vnum) {
		return theVehicleTrackingDAO.getVehicleTrack(vnum);
	}

	@Override
	@Transactional
	public void saveVehicleTrack(VehicleTracking vehicleTracking) {
		theVehicleTrackingDAO.saveVehicleTrack(vehicleTracking);
	}

	@Override
	@Transactional
	public boolean getReturnStatus(String vehicleNumber, Integer laneId, Integer userId) {
		return theVehicleTrackingDAO.getReturnStatus(vehicleNumber, laneId, userId);
	}

	@Override
	@Transactional
	public boolean checkButNotUpdateReturn(String vnumber, String laneId) {
		return theVehicleTrackingDAO.checkButNotUpdateReturn(vnumber, laneId);
	}

}
