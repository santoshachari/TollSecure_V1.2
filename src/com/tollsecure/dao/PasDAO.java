package com.tollsecure.dao;

import java.util.List;

import com.tollsecure.entity.Pass;

public interface PasDAO {

	void saveOrUpdate(Pass pass);

	List<Object[]> getPassShifts(String from, String to, String shiftId);

	List<Object[]> getPassShifts(String from, String to);

	Pass getPassFromId(String passId);

}
