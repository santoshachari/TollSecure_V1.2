package com.tollsecure.service;

import java.util.List;

import com.tollsecure.entity.Lane;

public interface LaneService {

	List<Lane> getAllLanes(Integer tollPlazaId);

	List<Lane> getLaneFromLaneId(Integer laneId);

	void saveLane(Lane theLane);

	void deleteLane(Integer laneId);

	List<Lane> getLaneAssociatedWithUser(Integer userId);

	List<Lane> getLaneWithSameDirection(String laneDirection, Integer tollPId);

	List<Lane> getDistinctLaneDirections(Integer tollPlazaId);

	Lane getLaneFromLaneCode(String string);

	Lane getLaneFromLaneCode(String string, String string2);

}
