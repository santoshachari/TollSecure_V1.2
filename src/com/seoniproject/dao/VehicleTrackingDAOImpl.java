package com.seoniproject.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seoniproject.entity.Lane;
import com.seoniproject.entity.VehicleTracking;

@Repository
public class VehicleTrackingDAOImpl implements VehicleTrackingDAO {

	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public VehicleTracking getVehicleTrack(String vnum) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//create a query
		Query<VehicleTracking> theQuery = 
				currentSession.createQuery("from VehicleTracking where vehicleNumber='"+vnum+"'", VehicleTracking.class);
	
		//execute query and get result list
		List<VehicleTracking> vehicleTracking = theQuery.getResultList();
		
		if (vehicleTracking.size()==0) return null;
		
		return vehicleTracking.get(0);
		
	}

	@Override
	public void saveVehicleTrack(VehicleTracking vehicleTracking) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(vehicleTracking);
		
	}

	@Override
	public boolean checkButNotUpdateReturn(String vnumber, String laneId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		Query<VehicleTracking> theQuery = 
				currentSession.createQuery("from VehicleTracking where vehicleNumber='"+vnumber+"'", VehicleTracking.class);
					
		//execute query and get result list
		List<VehicleTracking> vehicleTracking = theQuery.getResultList();
		
		if (vehicleTracking.size()==0) {
			System.out.println("-=-=-=>>>"+" vehicle not found");
			return false;
		}
		
		//check the lane direction
		Query<Lane> laneQuery = currentSession.createQuery("from Lane where laneId='"+laneId+"'", Lane.class);
		
		List<Lane> lane = laneQuery.getResultList();
				
		if (lane.size()==0) return false;
				
		String laneDirection = lane.get(0).getLaneDirection();
		Date modificationTimeStamp = vehicleTracking.get(0).getModificationTimeStamp();
		String jType = vehicleTracking.get(0).getJourneyType();
		
		//if same direction
		if (laneDirection.equals(vehicleTracking.get(0).getLaneDirection())) {
					
			Date fiveMinutesBack = new Date(new Date().getTime() - (5 * 60 * 1000));
					
			//check if modification time stamp is less than 5 mins from now
			//for 5 minutes it is a valid vehicle
			if (modificationTimeStamp != null && fiveMinutesBack.before(modificationTimeStamp) && jType==null) {
				System.out.println("-=-=-=>>>"+" updated after five minutes back to null");
				return true;
			}

			//same direction
			System.out.println("-=-=-======>>>>Same direction");
			return false;
		}

		//else different direction so check modification time stamp if null then create time stamp
        //for 24 hrs
		Date yesterday = new Date(new Date().getTime() - (24 * 60 * 60 * 1000));
		
		if (modificationTimeStamp==null) {
			//check with create time stamp
			Date createTimeStamp = vehicleTracking.get(0).getCreateTimeStamp();
			
			
			if (createTimeStamp.after(yesterday) && jType!=null && jType.equals("R")) {
				//update and return true
				System.out.println("-=-=-=====>>>Return type for the first time");
	
				return true;
			}
			
		} else {
			//check with modification time stamp
			
			if (modificationTimeStamp.after(yesterday) && jType!=null && jType.equals("R")) {
				//update and return true
				System.out.println("-=-=-=====>>>Return type for the nth time");

				return true;
			}
		}
		
		System.out.println("-=-=-=>>>"+" nope man from here");
		return false;
	}

	@Override
	public boolean getReturnStatus(String vehicleNumber, Integer laneId, Integer userId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//create a query
		Query<VehicleTracking> theQuery = 
				currentSession.createQuery("from VehicleTracking where vehicleNumber='"+vehicleNumber+"'", VehicleTracking.class);
			
		//execute query and get result list
		List<VehicleTracking> vehicleTracking = theQuery.getResultList();
						
		if (vehicleTracking.size()==0) {
			System.out.println("-=-=-=>>>"+" vehicle not found");
			return false;
		}
				
		//check the lane direction
		Query<Lane> laneQuery = currentSession.createQuery("from Lane where laneId='"+laneId+"'", Lane.class);
		
		List<Lane> lane = laneQuery.getResultList();
		
		if (lane.size()==0) return false;
		
		String laneDirection = lane.get(0).getLaneDirection();
		Date modificationTimeStamp = vehicleTracking.get(0).getModificationTimeStamp();
		String jType = vehicleTracking.get(0).getJourneyType();
		
		//if same direction
		if (laneDirection.equals(vehicleTracking.get(0).getLaneDirection())) {
			
			Date fiveMinutesBack = new Date(new Date().getTime() - (5 * 60 * 1000));
			
			//check if modification time stamp is less than 5 mins from now
			//for 5 minutes it is a valid vehicle
			if (modificationTimeStamp != null && fiveMinutesBack.before(modificationTimeStamp) && jType==null) {
				System.out.println("-=-=-=>>>"+" updated after five minutes back to null");
				return true;
			}

			//same direction
			System.out.println("-=-=-======>>>>Same direction");
			return false;
		}
		
		//else different direction so check modification time stamp if null then create time stamp
        //for 24 hrs
		Date yesterday = new Date(new Date().getTime() - (24 * 60 * 60 * 1000));
		
		if (modificationTimeStamp==null) {
			//check with create time stamp
			Date createTimeStamp = vehicleTracking.get(0).getCreateTimeStamp();
			
			
			if (createTimeStamp.after(yesterday) && jType!=null && jType.equals("R")) {
				//update and return true
				System.out.println("-=-=-=====>>>Return type for the first time");
				vehicleTracking.get(0).setJourneyType(null);
				vehicleTracking.get(0).setModificationTimeStamp(new Date());
				vehicleTracking.get(0).setModifiedUserId(userId);
				vehicleTracking.get(0).setLaneDirection(laneDirection);
				currentSession.saveOrUpdate(vehicleTracking.get(0));
				return true;
			}
			
		} else {
			//check with modification time stamp
			
			if (modificationTimeStamp.after(yesterday) && jType!=null && jType.equals("R")) {
				//update and return true
				System.out.println("-=-=-=====>>>Return type for the nth time");
				vehicleTracking.get(0).setJourneyType(null);
				vehicleTracking.get(0).setModificationTimeStamp(new Date());
				vehicleTracking.get(0).setModifiedUserId(userId);
				vehicleTracking.get(0).setLaneDirection(laneDirection);
				currentSession.saveOrUpdate(vehicleTracking.get(0));
				return true;
			}
		}
		
		System.out.println("-=-=-=>>>"+" nope man from here");
		return false;
	}

}
