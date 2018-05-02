package com.tollsecure.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tollsecure.entity.FloatAmountDetails;



@Repository
public class FloatAmountDetailsDAOImpl implements FloatAmountDetailsDAO {
	
	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public FloatAmountDetails getFloatAmountAndOperatorFrom(String plazaId, String laneId, String date,
			String shiftId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		Query<FloatAmountDetails> theQuery = 
				currentSession.createQuery("from FloatAmountDetails where tollPlazaId='"+plazaId+"' and laneId='"+laneId+"' and tDate='"+date+"' and shiftId='"+shiftId+"'", FloatAmountDetails.class);
		
		List<FloatAmountDetails> result = theQuery.getResultList();
		
		if (result.size()==0)return null;
		
		return result.get(0);
	}

	@Override
	public List<FloatAmountDetails> getSameDetailIfExist(String shiftDate1, String tollPlazaId1, String laneId1, String shiftId1) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		Query<FloatAmountDetails> theQuery = 
				currentSession.createQuery("from FloatAmountDetails where tDate='"+shiftDate1+"' and tollPlazaId='"+tollPlazaId1+"' and laneId='"+laneId1+"' and shiftId='"+shiftId1+"'", FloatAmountDetails.class);
		
		List<FloatAmountDetails> floatAmountDetails = theQuery.getResultList();
		
		return floatAmountDetails;
	}

	@Override
	public void saveOrUpdate(FloatAmountDetails sameDetails) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(sameDetails);
		
	}

}
