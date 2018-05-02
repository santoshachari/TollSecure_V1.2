package com.tollsecure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tollsecure.dao.LaneDAO;
import com.tollsecure.entity.Lane;

@Service
public class LaneServiceImpl implements LaneService {
	
	@Autowired 
	private LaneDAO theLaneDAO;

	@Override
	@Transactional
	public List<Lane> getAllLanes(Integer tollPlazaId) {
		return theLaneDAO.getAllLanes(tollPlazaId);
	}

	@Override
	@Transactional
	public List<Lane> getLaneFromLaneId(Integer laneId) {
		return theLaneDAO.getLaneFromLaneId(laneId);
	}

	@Override
	@Transactional
	public void saveLane(Lane theLane) {	
		 theLaneDAO.saveLane(theLane);
	}

	@Override
	@Transactional
	public void deleteLane(Integer laneId) {
		theLaneDAO.deleteLane(laneId);
		
	}

	@Override
	@Transactional
	public List<Lane> getLaneAssociatedWithUser(Integer userId) {
		return theLaneDAO.getLaneAssociatedWithUser(userId);
	}

	@Override
	@Transactional
	public List<Lane> getLaneWithSameDirection(String laneDirection, Integer tollPId) {
		return theLaneDAO.getLaneWithSameDirection(laneDirection, tollPId);
	}

	@Override
	@Transactional
	public List<Lane> getDistinctLaneDirections(Integer tollPlazaId) {
		return theLaneDAO.getDistinctLaneDirections(tollPlazaId);
	}

	@Override
	@Transactional
	public Lane getLaneFromLaneCode(String string) {
		return theLaneDAO.getLaneFromLaneCode(string);
	}

	@Override
	@Transactional
	public Lane getLaneFromLaneCode(String laneCode, String plazaId) {
		return theLaneDAO.getLaneFromLaneCode(laneCode, plazaId);
	}

}
