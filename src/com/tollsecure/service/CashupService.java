package com.tollsecure.service;

import java.util.List;

import com.tollsecure.entity.Cashup;

public interface CashupService {

	Cashup getCashUpIfExists(String checkdate, Integer laneId, Integer shiftId);

	void saveOrUpdate(Cashup cashup);

	List<Object[]> getTcs(String date, String plazaId, String shiftId);

}
