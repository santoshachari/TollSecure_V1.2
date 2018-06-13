package com.tollsecure.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tollsecure.entity.AVC;

@Repository
public class AVCDAOImpl implements AVCDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean checkForTransactionIdIfExists(Integer transactionId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<AVC> theQuery =
				currentSession.createQuery("from AVC where transactionId='"+transactionId+"'", AVC.class);
				
		List<AVC> AVCList = theQuery.getResultList();
		
		if (AVCList.size()==0)return false; //it does not exist
		return true; //exists
	}

	@Override
	public void save(AVC theAVC) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.save(theAVC);
	}

}
