package com.tollsecure.dao;

import java.util.List;

import com.tollsecure.entity.FloatAmountDetails;

public interface FloatAmountDetailsDAO {
	FloatAmountDetails getFloatAmountAndOperatorFrom(String plazaId, String laneId, String date, String shiftId);

	List<FloatAmountDetails> getSameDetailIfExist(String shiftDate1, String tollPlazaId1, String laneId1, String shiftId1);

	void saveOrUpdate(FloatAmountDetails sameDetails);
}
