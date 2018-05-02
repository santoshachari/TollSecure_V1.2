package com.seoniproject.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seoniproject.entity.ConcessionVehicles;

@Repository
public class ConcessionVehiclesDAOImpl implements ConcessionVehiclesDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveVehicle(ConcessionVehicles theConcessionVehicle) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		currentSession.saveOrUpdate(theConcessionVehicle);
		
	}

	@Override
	public ConcessionVehicles getLastRecord(String vnum) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<ConcessionVehicles> theQuery = 
				currentSession.createQuery("from ConcessionVehicles where vehicleNumber='"+vnum+"' order by concessionId desc", ConcessionVehicles.class);
		
		theQuery.setMaxResults(1);
		
		List<ConcessionVehicles> concessionVehiclesList = theQuery.getResultList();
		
		if (concessionVehiclesList.isEmpty()) return null;
		
		return concessionVehiclesList.get(0);
	}
	
	
	
}
