package com.tollsecure.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tollsecure.entity.Cashup;

@Repository
public class CashupDAOImpl implements CashupDAO {

	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public Cashup getCashUpIfExists(String checkdate, Integer laneId, Integer shiftId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Cashup> theQuery =
				currentSession.createQuery("from Cashup where checkDate='"+checkdate+"' and laneId='"+laneId+"' and shiftId='"+shiftId+"'", Cashup.class);
	
		List<Cashup> theCashupList = theQuery.getResultList();
		
		if (theCashupList.size()==0)return null;
		return theCashupList.get(0);
	}
	@Override
	public void saveOrUpdate(Cashup cashup) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(cashup);
		
	}
	@Override
	public List<Object[]> getTcs(String date, String plazaId, String shiftId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
	
		//create a query
		Query<Object[]> theQuery = null;
		
		theQuery = currentSession.createQuery("select distinct u.userId ,u.userFirstName, u.userLastName from"
				+ " User u join CashupDeclaration c on u.userId = c.userId where c.tollPlazaId = '"+plazaId+"'"
				+ " and ('"+shiftId+"'='All' or c.shiftId='"+shiftId+"') and c.checkDate='"+date+"'");
		
		List<Object[]> cashUpTcs = theQuery.list();
		
		return cashUpTcs;
	}

}







