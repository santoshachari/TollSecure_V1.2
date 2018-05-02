package com.seoniproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seoniproject.dao.TollPlazaDAO;
import com.seoniproject.entity.TollPlaza;

@Service
public class TollPlazaServiceImpl implements TollPlazaService {

	@Autowired
	TollPlazaDAO tollPlazaDAO;
	
	@Override
	@Transactional
	public List<TollPlaza> getAllTollPlazas() {	
		return tollPlazaDAO.getAllTollPlazas();
	}

	@Override
	@Transactional
	public List<TollPlaza> getTollPlaza(int theId) {
		return tollPlazaDAO.getTollPlaza(theId);
	}

	@Override
	@Transactional
	public void saveTollPlaza(TollPlaza theTollPlaza) {
		tollPlazaDAO.saveTollPlaza(theTollPlaza);
	}

	@Override
	@Transactional
	public void deletePlaza(int theId) {
		tollPlazaDAO.deletePlaza(theId);
		
	}

	@Override
	@Transactional
	public List<TollPlaza> getTollPlazaFromName(String tollPlazaName) {
		return tollPlazaDAO.getTollPlazaFromName(tollPlazaName);
	}

}
