package com.tollsecure.dao;

import java.util.List;

import com.tollsecure.entity.Lane;

public interface LaneDAO {

	List<Lane> getAllLanes(Integer tollPlazaId);

	List<Lane> getLaneFromLaneId(Integer laneId);

	void saveLane(Lane theLane);

	void deleteLane(Integer laneId);

	List<Lane> getLaneAssociatedWithUser(Integer userId);

	List<Lane> getLaneWithSameDirection(String laneDirection, Integer tollPId);

	List<Lane> getDistinctLaneDirections(Integer tollPlazaId);

	Lane getLaneFromLaneCode(String string);

	Lane getLaneFromLaneCode(String laneCode, String plazaId);

}
