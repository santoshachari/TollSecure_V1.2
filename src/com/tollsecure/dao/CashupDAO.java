package com.tollsecure.dao;

import java.util.List;

import com.tollsecure.entity.Cashup;

public interface CashupDAO {

	Cashup getCashUpIfExists(String checkdate, Integer laneId, Integer shiftId);

	void saveOrUpdate(Cashup cashup);

	List<Object[]> getTcs(String date, String plazaId, String shiftId);

}
