package com.seoniproject.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seoniproject.entity.Shift;

@Repository
public class ShiftDAOImpl implements ShiftDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveShiftList(List<Shift> theShifts) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		for (int i=0; i<4; i++) {
			currentSession.saveOrUpdate(theShifts.get(i));
		}
	}

	@Override
	public List<Shift> getAllShifts(Integer tollPlazaId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		Query<Shift> theQuery = currentSession.createQuery("from Shift where tollPlazaId='"+tollPlazaId+"' and statusFlag='ACTIVE'", Shift.class);
	
		List<Shift> shifts = theQuery.getResultList();
		
		return shifts;
	}
	
	@Override
	public List<Shift> getAllShiftsActiveAndDeActive(Integer thePlazaId) {
		
		// get the current hibernate session
				Session currentSession = sessionFactory.getCurrentSession();
				
				//create a query
				Query<Shift> theQuery = currentSession.createQuery("from Shift where tollPlazaId='"+thePlazaId+"'", Shift.class);
			
				List<Shift> shifts = theQuery.getResultList();
				
				return shifts;
	}

	@Override
	public Shift getShift(String now, Integer plazaId) {

		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//try to get the shift with general query
		Query<Shift> theQuery = 
				currentSession.createQuery("from Shift where startTime<='"+now+"' and endTime>='"+now+"' and tollPlazaId='"+plazaId+"'", Shift.class);
		
		List<Shift> shifts = theQuery.getResultList();
		
		//if size of the list is 0 then probably the shift is day changing sift
		if (shifts.size()==0) {
			theQuery = 
					currentSession.createQuery("from Shift where startTime > endTime and tollPlazaId='"+plazaId+"'", Shift.class);
			//this should be the resultant list
			shifts = theQuery.getResultList();
		}
		
		
		//if again list is null then shifts are not set
		if (shifts.size()==0) {
			return null;
		}
		
		//return shifts.get(0).getShiftDesc()+"_"+shifts.get(0).getShiftId();
		return shifts.get(0);
	}

	@Override
	public Shift getShiftFromDesc(String plazaId, String shiftDesc) {
		if (plazaId==null || shiftDesc==null) return null;
		
		//get current hibernate session
				Session currentSession = sessionFactory.getCurrentSession();
				
		//try to get the shift with general query
		Query<Shift> theQuery = 
				currentSession.createQuery("from Shift where shiftDesc='"+shiftDesc+"' and tollPlazaId='"+plazaId+"' statusFlag='ACTIVE'", Shift.class);
	
		List<Shift> shifts = theQuery.getResultList();
		
		if (shifts.size()==0)return null;
		else return shifts.get(0);
	}

	//here we are not checking for active or deactive as query may be after deactivtion also
	@Override
	public Shift getShiftFromId(Integer shiftId) {
		
		//get current hibernate session
				Session currentSession = sessionFactory.getCurrentSession();
				
		//try to get the shift with general query
		Query<Shift> theQuery = 
				currentSession.createQuery("from Shift where shiftId='"+shiftId+"'", Shift.class);
		
		Shift theShift = theQuery.getSingleResult();
		
		return theShift;
	}

}





















