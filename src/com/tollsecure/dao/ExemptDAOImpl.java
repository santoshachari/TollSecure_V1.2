package com.tollsecure.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tollsecure.entity.Exempt;

@Repository
public class ExemptDAOImpl implements ExemptDAO{

	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveOrUpdate(Exempt exempt) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(exempt);
	}

	@Override
	public Exempt getLastExempt(String vehicleNumber) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Exempt> theQuery = 
				currentSession.createQuery("from Exempt where vehicleNumber='"+vehicleNumber+"'order by exemptId desc", Exempt.class);
		theQuery.setMaxResults(1);	
		
		List<Exempt> exempts = theQuery.getResultList();
		
		if (exempts.isEmpty()) return null;
		
		return exempts.get(0);
	}

}






 