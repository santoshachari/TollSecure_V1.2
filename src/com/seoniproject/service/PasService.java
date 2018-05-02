package com.seoniproject.service;

import java.util.List;

import com.seoniproject.entity.Pass;

public interface PasService {

	void saveOrUpdate(Pass pass);

	List<Object[]> getPassShifts(String from, String to, String shiftId);

	List<Object[]> getPassShifts(String from, String to);

	Pass getPassFromId(String passId);

}
