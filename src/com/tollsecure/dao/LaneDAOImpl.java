package com.tollsecure.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tollsecure.entity.Lane;

@Repository
public class LaneDAOImpl implements LaneDAO {
	
	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Lane> getAllLanes(Integer tollPlazaId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		Query<Lane> theQuery = currentSession.createQuery("from Lane where tollPlazaId='"+tollPlazaId+"' and statusFlag='ACTIVE'", Lane.class);	
		
		List<Lane> lanes = theQuery.getResultList();
		
		return lanes;
	}

	@Override
	public List<Lane> getLaneFromLaneId(Integer laneId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//create a query
		Query<Lane> theQuery = currentSession.createQuery("from Lane where laneId='"+laneId+"'", Lane.class);	
				
		List<Lane> theLane = theQuery.getResultList(); 
				
		return theLane;
	}

	@Override
	public void saveLane(Lane theLane) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theLane);
	}

	@Override
	public void deleteLane(Integer laneId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		Query theQuery = 
				currentSession.createQuery("delete from Lane where laneId =:theLaneId");
		
		theQuery.setParameter("theLaneId", laneId);
		
		theQuery.executeUpdate();
	}

	@Override
	public List<Lane> getLaneAssociatedWithUser(Integer userId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		Query<Lane> theQuery = 
				currentSession.createQuery("from Lane where userId='"+userId+"' and statusFlag='ACTIVE'", Lane.class);	
		
		List<Lane> lanes = theQuery.getResultList();
		
		return lanes;
	}

	@Override
	public List<Lane> getLaneWithSameDirection(String laneDirection, Integer tollPId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		Query<Lane> theQuery = 
				currentSession.createQuery("from Lane where tollPlazaId='"+tollPId+"' and laneDirection='"+laneDirection+"' and statusFlag='ACTIVE'", Lane.class);	
	
		List<Lane> lanes = theQuery.getResultList();
		
		return lanes;
		
	}

	@Override
	public List<Lane> getDistinctLaneDirections(Integer tollPlazaId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//create a query
		Query<Lane> theQuery = currentSession.createQuery("from Lane where tollPlazaId='"+tollPlazaId+"' and statusFlag='ACTIVE'", Lane.class);	
				
		List<Lane> lanes = theQuery.getResultList();
				
		
     	Map<String, Lane> removeDuplicates = new HashMap<String, Lane>();
		
		for (Lane l: lanes) {
			removeDuplicates.put(l.getLaneDirection(), l);
		}
		
		lanes.clear();
		
		 Iterator it = removeDuplicates.entrySet().iterator();
		 while (it.hasNext()) {
		     Map.Entry pair = (Map.Entry)it.next();
		     lanes.add((Lane) pair.getValue());
		     it.remove(); // avoids a ConcurrentModificationException
		 }

		return lanes;
	}

	@Override
	public Lane getLaneFromLaneCode(String string) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//create a query
		Query<Lane> theQuery = currentSession.createQuery("from Lane where laneCode='"+string+"' and statusFlag='ACTIVE'", Lane.class);	
		
		List<Lane> lanes = theQuery.getResultList();
		
		if (lanes.size()==0)return null;
		
		return lanes.get(0);
	}

	@Override
	public Lane getLaneFromLaneCode(String laneCode, String plazaId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//create a query
		Query<Lane> theQuery = currentSession.createQuery("from Lane where laneCode='"+laneCode+"' and tollPlazaId='"+plazaId+"' and statusFlag='ACTIVE'", Lane.class);	
		
		List<Lane> lanes = theQuery.getResultList();
		
		if (lanes.size()==0)return null;
		
		return lanes.get(0);
	}


}
