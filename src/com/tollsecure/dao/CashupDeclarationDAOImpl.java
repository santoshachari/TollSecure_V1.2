package com.tollsecure.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tollsecure.entity.CashupDeclaration;

@Repository
public class CashupDeclarationDAOImpl implements CashupDeclarationDAO {

	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveDeclaration(CashupDeclaration theCashupDelaration) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(theCashupDelaration);
	}

	@Override
	public CashupDeclaration getDeclarationIfExist(String checkDate, Integer laneId, Integer shiftId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<CashupDeclaration> theQuery = 
				currentSession.createQuery("from CashupDeclaration where checkDate='"+checkDate+"' and laneId='"+laneId+"' and shiftId='"+shiftId+"'", CashupDeclaration.class);
	
		List<CashupDeclaration> existingCashupDeclaration = theQuery.getResultList();
		
		if (existingCashupDeclaration.size()==0) return null;
		
		return existingCashupDeclaration.get(0);
	}
}
