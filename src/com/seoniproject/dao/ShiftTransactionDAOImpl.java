package com.seoniproject.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seoniproject.entity.ShiftTransaction;


@Repository
public class ShiftTransactionDAOImpl implements ShiftTransactionDAO {

	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
		
	@Override
	public void saveOrUpdate(ShiftTransaction shiftTransaction) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(shiftTransaction);
	}

	@Override
	public ShiftTransaction getShiftTransactionFromUserAndPunchInTime(Integer userId, Date punchInTime) {
		
		//this method is modified and we no longer require punchInTime in it
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Date fiveBefore = new Date(punchInTime.getTime() - 1000*5); //5 seconds before punch in time
		Date fiveAfter = new Date(punchInTime.getTime() + 1000*5); //5 seconds after punch in time
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String pibef = sdf.format(fiveBefore);
		String piaft = sdf.format(fiveAfter);
		
		//check from 5 seconds back and 5 seconds front
		
		
		//create a query
		//Query<ShiftTransaction> theQuery = currentSession.createQuery("from ShiftTransaction where userId='"+userId+"' and punchInTime>='"+pibef+"' and punchInTime <='"+piaft+"'", ShiftTransaction.class);
		
		//the above query is modified for performance
		//Query<ShiftTransaction> theQuery = currentSession.createQuery("from ShiftTransaction order by shiftTransactionId desc", ShiftTransaction.class);
		
		Query<ShiftTransaction> theQuery = currentSession.createQuery("from ShiftTransaction where userId='"+userId+"' order by shiftTransactionId desc", ShiftTransaction.class);
		
		theQuery.setMaxResults(1);
		
		List<ShiftTransaction> shiftTransactions = theQuery.getResultList();
		
		/*ShiftTransaction thatOne = null;
		
		for (ShiftTransaction tempOne : shiftTransactions) {
			Date punchTime = tempOne.getPunchInTime();
			
			if (fiveBefore.before(punchTime)||fiveBefore.equals(punchTime)) {
				if (fiveAfter.after(punchTime)||fiveAfter.equals(punchTime)) {
					if(userId == tempOne.getUserId()) {
						thatOne = tempOne;
						break;
					}
				}
			}
		}
		
		System.out.println("hi all:-=-=-=-=>>>>>line 73"+thatOne);*/
		if (shiftTransactions.size()>0) return shiftTransactions.get(0);
		return null;
	}

	@Override
	public ShiftTransaction getShiftTransactionForLaneDateShift(Integer laneId, String checkDate, Integer shiftId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<ShiftTransaction> theQuery = currentSession.createQuery("from ShiftTransaction where laneId='"+laneId+"' and date(punchInTime) = '"+checkDate+"' and shiftId='"+shiftId+"' order by shiftTransactionId desc", ShiftTransaction.class);
		
		theQuery.setMaxResults(1);
		
		List<ShiftTransaction> shiftTransactions = theQuery.getResultList();
		
		if (shiftTransactions.size()>0) return shiftTransactions.get(0);
		return null;
	}

}
