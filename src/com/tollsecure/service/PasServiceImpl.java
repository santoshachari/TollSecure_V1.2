package com.tollsecure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tollsecure.dao.PasDAO;
import com.tollsecure.entity.Pass;

@Service
public class PasServiceImpl implements PasService {

	@Autowired
	private PasDAO thePasDAO;
	
	@Override
	@Transactional
	public void saveOrUpdate(Pass pass) {
		thePasDAO.saveOrUpdate(pass);
	}

	@Override
	@Transactional
	public List<Object[]> getPassShifts(String from, String to, String shiftId) {
		// TODO Auto-generated method stub
		return thePasDAO.getPassShifts(from, to, shiftId);
	}

	@Override
	@Transactional
	public List<Object[]> getPassShifts(String from, String to) {
		// TODO Auto-generated method stub
		return thePasDAO.getPassShifts(from, to);
	}

	@Override
	@Transactional
	public Pass getPassFromId(String passId) {
		// TODO Auto-generated method stub
		return thePasDAO.getPassFromId(passId);
	}

}
