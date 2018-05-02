package com.seoniproject.dao;

import java.util.List;

import com.seoniproject.entity.TollPlaza;

public interface TollPlazaDAO {

	List<TollPlaza> getAllTollPlazas();

	List<TollPlaza> getTollPlaza(int theId);

	void saveTollPlaza(TollPlaza theTollPlaza);

	void deletePlaza(int theId);

	List<TollPlaza> getTollPlazaFromName(String tollPlazaName);

}
