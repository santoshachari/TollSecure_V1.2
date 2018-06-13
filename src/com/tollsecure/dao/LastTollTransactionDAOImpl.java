package com.tollsecure.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tollsecure.entity.LastTollTransaction;

@Repository
public class LastTollTransactionDAOImpl implements LastTollTransactionDAO {
	
	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public LastTollTransaction getLastTollTransactionForLane(String laneCode) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
	
		Query<LastTollTransaction> theQuery = currentSession.createQuery("from LastTollTransaction where laneCode='"+laneCode+"'", LastTollTransaction.class);
		
		List<LastTollTransaction> theLastTollTransaction = theQuery.getResultList();
		
		if (theLastTollTransaction.isEmpty()) return null;
		else return theLastTollTransaction.get(0);
	}

	@Override
	public void saveOrUpdate(LastTollTransaction theLastTollTransaction) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(theLastTollTransaction);
	}


}
