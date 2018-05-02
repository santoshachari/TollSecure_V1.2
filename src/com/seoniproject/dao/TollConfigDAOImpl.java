package com.seoniproject.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seoniproject.entity.Lane;
import com.seoniproject.entity.TollConfig;


@Repository
public class TollConfigDAOImpl implements TollConfigDAO {

	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	//this method is to be updated with effective dates and toll plaza id
	@Override
	public List<TollConfig> getTollConfigs() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		Query<TollConfig> theQuery = currentSession.createQuery("from TollConfig", TollConfig.class);
		
		//execute query and get result list
		List<TollConfig> tollConfigs = theQuery.getResultList();
				
		return tollConfigs;
	}

	//this method is to be updated with effective dates and toll plaza id
	@Override
	public HashMap<String, String> getRate(String vClass, String jType, Integer plazaID, Date now) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String nowDate = sdf.format(now);
		
		//create a query
		Query<TollConfig> theQuery = 
				currentSession.createQuery("from TollConfig where vehicleClass='"+vClass+"' and journeyType='"+jType+"' and tollPlazaId='"+plazaID+"' and effectiveFrom <='"+nowDate+"' and effectiveTo>='"+nowDate+"' and statusFlag!='DEACTIVE'", TollConfig.class);
		
		List<TollConfig> tollConfigs = theQuery.getResultList();
		
		if(tollConfigs.size()==0) return null;
		
		HashMap<String, String> forRate = new HashMap<String, String>();
		
		forRate.put(tollConfigs.get(0).getVehicleClassId()+"", tollConfigs.get(0).getTollAmt().toString());
		
		return forRate;
	}
	
	@Override
	public HashMap<String, String> getRate(String vClass, String jType, Integer plazaId, String startDate,
			String endDate) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//create a query
		Query<TollConfig> theQuery = 
					currentSession.createQuery("from TollConfig where vehicleClass='"+vClass+"' and journeyType='"+jType+"' and tollPlazaId='"+plazaId+"' and effectiveFrom <='"+startDate+"' and effectiveTo>='"+endDate+"' and statusFlag!='DEACTIVE'", TollConfig.class);
			
		List<TollConfig> tollConfigs = theQuery.getResultList();
		
		if(tollConfigs.size()==0) return null;
		
		HashMap<String, String> forRate = new HashMap<String, String>();
		
		forRate.put(tollConfigs.get(0).getVehicleClassId()+"", tollConfigs.get(0).getTollAmt().toString());
		
		return forRate;
	}
	
	@Override
	public Integer getVehicleClassId(String vehicleClass, String laneId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//get the plazaId from laneId
		Query<Lane> theLaneQuery = currentSession.createQuery("from Lane where laneId='"+laneId+"'", Lane.class);
		
		List<Lane> lanes = theLaneQuery.getResultList();
		
		if (lanes.size()==0) return null;
		
		Integer plazaId = lanes.get(0).getTollPlazaId();
		
		Date now1 = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String now = sdf.format(now1);
		
		//now the real query
		Query<TollConfig> theQuery = currentSession.createQuery("from TollConfig where tollPlazaId='"+plazaId+"' and vehicleClass='"+vehicleClass+"' and journeyType='S' and effectiveFrom<='"+now+"' and effectiveTo>='"+now+"' and statusFlag!='DEACTIVE'", TollConfig.class);
		List<TollConfig> tollConfigs = theQuery.getResultList();
		
		if (tollConfigs.isEmpty()) return null;
		
		return tollConfigs.get(0).getVehicleClassId();
	}

	//this method is to be updated with effective dates and toll plaza id
	@Override
	public List<String> getUniqueJoutneyTypes() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//create a query
		Query<TollConfig> theQuery = currentSession.createQuery("from TollConfig", TollConfig.class);
		
		//execute query and get result list
		List<TollConfig> tollConfigs = theQuery.getResultList();
		
		//need unique journey types
		List<String> uniqueJourneyTypes = new ArrayList<String>();
		
		for (int i = 0; i < tollConfigs.size(); i++) {
			uniqueJourneyTypes.add(tollConfigs.get(i).getJourneyType());
		}
		
		//insert them into a hash set and make them unique (delete duplicates)
		Set<String> hs = new HashSet<>();
		hs.addAll(uniqueJourneyTypes);
		uniqueJourneyTypes.clear();
		uniqueJourneyTypes.addAll(hs);
		
		return uniqueJourneyTypes;
	}

	//this method is to be updated with effective dates and toll plaza id
	@Override
	public Map<String, String> getUniqueVehicleClasses() {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
						
		//create a query
		Query<TollConfig> theQuery = currentSession.createQuery("from TollConfig where coalesce(statusFlag)='ACTIVE'", TollConfig.class);
				
		//execute query and get result list
		List<TollConfig> tollConfigs = theQuery.getResultList();
				
		//need unique journey types
		Map<String, String> uniqueVehicleClasses = new HashMap<String, String>();
		
		//get the values as vehicleClassId_vehicleClass
		//we are keeping vahicle class as key as it should be unique value
		for (int i = 0; i < tollConfigs.size(); i++) {
			uniqueVehicleClasses.put(tollConfigs.get(i).getVehicleClass(), tollConfigs.get(i).getVehicleClassId()+"");
		}
		
		//insert them into a hash set and make them unique (delete duplicates)
		/*Set<String> hs = new HashSet<>();
		hs.addAll(uniqueVehicleClasses);
		uniqueVehicleClasses.clear();
		uniqueVehicleClasses.addAll(hs);*/
		
		return uniqueVehicleClasses;
	}

	//this method is to be updated with effective dates and toll plaza id
	@Override
	public String getLastTrollTransactionVehicleClass(Integer vehicleClassId) {
		if (vehicleClassId == null) return null;
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
								
		//create a query
		Query<TollConfig> theQuery = currentSession.createQuery("from TollConfig where vehicleClassId="+vehicleClassId, TollConfig.class);
	
		//for returning
		TollConfig lastVehicleClass = theQuery.getSingleResult();
		
		return lastVehicleClass.getVehicleClass();
	}

	@Override
	public List<TollConfig> getAllConfigs(Integer plazaId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		Query<TollConfig> theQuery = currentSession.createQuery("from TollConfig where tollPlazaId='"+plazaId+"'", TollConfig.class);
		
		//for returning
		List<TollConfig> tollConfigs = theQuery.getResultList();
		
		return tollConfigs;
	}

	@Override
	public TollConfig getTollConfig(Integer vehicleClassId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		Query<TollConfig> theQuery = currentSession.createQuery("from TollConfig where vehicleClassId='"+vehicleClassId+"'", TollConfig.class);
		
		//for returning
		List<TollConfig> tollConfigs = theQuery.getResultList();
				
		return tollConfigs.get(0);
	}

	@Override
	public TollConfig getTollConfigForUpdate(Integer plazaId, String vehicleClass, String journeyType) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//get the toll config for vehicleClassId and updateits amount and save or update
		Query<TollConfig> theQuery = currentSession.createQuery("from TollConfig where vehicleClass='"+vehicleClass+"' and tollPlazaId='"+plazaId+"' and journeyType='"+journeyType+"'", TollConfig.class);
	
		List<TollConfig> tollConfigs = theQuery.getResultList();
		
		if (tollConfigs.size()==0) return null;
		
		return tollConfigs.get(0);
	}
	
	@Override
	public List<TollConfig> getTollConfigForUpdate(Integer plazaId, String vehicleClass, String journeyType, String startDate,
			String endDate) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
						
		//get the toll config for vehicleClassId and updateits amount and save or update
		Query<TollConfig> theQuery = currentSession.createQuery("from TollConfig where vehicleClass='"+vehicleClass+"' and tollPlazaId='"+plazaId+"' and journeyType='"+journeyType+"' and effectiveFrom<='"+startDate+"' and statusFlag!='DEACTIVE'", TollConfig.class);
	
		List<TollConfig> tollConfigs = theQuery.getResultList();
		
		if (tollConfigs.size()==0) return null;
		
		return tollConfigs;
		
	}

	@Override
	public void saveTollConfig(TollConfig tollConfig) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//save or update object
		currentSession.saveOrUpdate(tollConfig);
	}

	@Override
	public Float getRateFromClassAndJType(String vehicleClass, String journeyType) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//get the toll config for vehicleClassId and updateits amount and save or update
		Query<TollConfig> theQuery = currentSession.createQuery("from TollConfig where vehicleClass='"+vehicleClass+"'  and journeyType='"+journeyType+"' and statusFlag!='DEACTIVE'", TollConfig.class);
	
		List<TollConfig> theTollConfigs = theQuery.getResultList();
		
		if (theTollConfigs.size()==0) return null;
		return theTollConfigs.get(0).getTollAmt();
	}

	@Override
	public Integer getVehicleClassIdFrom(String vehicleClass, String journeyType) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//get the toll config for vehicleClassId and updateits amount and save or update
		Query<TollConfig> theQuery = currentSession.createQuery("from TollConfig where vehicleClass='"+vehicleClass+"'  and journeyType='"+journeyType+"' and statusFlag!='DEACTIVE'", TollConfig.class);
	
		List<TollConfig> theTollConfigs = theQuery.getResultList();
		
		if (theTollConfigs.size()==0) return null;
		return theTollConfigs.get(0).getVehicleClassId();
	}

}
