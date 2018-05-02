package com.seoniproject.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seoniproject.entity.TollTransaction;

@Repository
public class ReportDAOImpl implements ReportDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<TollTransaction> getTollTransactionsFrom(String from, String to) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		from = from+" 00:00:00";
		to = to+" 23:59:59";
		
		Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where createTimeStamp>='"+from+"' and createTimeStamp<='"+to+"' and cancellationCode is null", TollTransaction.class);
	
		List<TollTransaction> tollTransactions = theQuery.getResultList();
		
		return tollTransactions;
	}

	@Override
	public List<TollTransaction> getTollTransactionsFromLaneId(String from, String to, String laneId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		from = from+" 00:00:00";
		to = to+" 23:59:59";
		
		Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where createTimeStamp>='"+from+"' and createTimeStamp<='"+to+"' and cancellationCode is null and laneId='"+laneId+"'", TollTransaction.class);
	
		List<TollTransaction> tollTransactions = theQuery.getResultList();
		
		return tollTransactions;
	}

	@Override
	public List<TollTransaction> getTollTransactionsFromUserId(String from, String to, String userId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		from = from+" 00:00:00";
		to = to+" 23:59:59";
		
		Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where createTimeStamp>='"+from+"' and createTimeStamp<='"+to+"' and cancellationCode is null and createUserID='"+userId+"'", TollTransaction.class);
	
		List<TollTransaction> tollTransactions = theQuery.getResultList();
		
		return tollTransactions;
	}

	@Override
	public List<TollTransaction> getTollTransactionsFromshiftId(String from, String to, String shiftId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		from = from+" 00:00:00";
		to = to+" 23:59:59";
		
		Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where createTimeStamp>='"+from+"' and createTimeStamp<='"+to+"' and cancellationCode is null and shiftId='"+shiftId+"'", TollTransaction.class);
	
		List<TollTransaction> tollTransactions = theQuery.getResultList();
		
		return tollTransactions;
	}

	@Override
	public List<TollTransaction> getTollTransactionsFromLaneIdUserId(String from, String to, String laneId,
			String userId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		from = from+" 00:00:00";
		to = to+" 23:59:59";
		
		Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where createTimeStamp>='"+from+"' and createTimeStamp<='"+to+"' and cancellationCode is null and laneId='"+laneId+"' and createUserID='"+userId+"'", TollTransaction.class);
	
		List<TollTransaction> tollTransactions = theQuery.getResultList();
		
		return tollTransactions;
	}

	@Override
	public List<TollTransaction> getTollTransactionsFromShiftIdUserId(String from, String to, String shiftId,
			String userId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		from = from+" 00:00:00";
		to = to+" 23:59:59";
		
		Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where createTimeStamp>='"+from+"' and createTimeStamp<='"+to+"' and cancellationCode is null and shiftId='"+shiftId+"' and createUserID='"+userId+"'", TollTransaction.class);
	
		List<TollTransaction> tollTransactions = theQuery.getResultList();
		
		return tollTransactions;
	}

	@Override
	public List<TollTransaction> getTollTransactionsFromShiftIdLaneId(String from, String to, String shiftId,
			String laneId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		from = from+" 00:00:00";
		to = to+" 23:59:59";
		
		Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where createTimeStamp>='"+from+"' and createTimeStamp<='"+to+"' and cancellationCode is null and shiftId='"+shiftId+"' and laneId='"+laneId+"'", TollTransaction.class);
	
		List<TollTransaction> tollTransactions = theQuery.getResultList();
		
		return tollTransactions;
	}

	@Override
	public List<TollTransaction> getTollTransactionsFromShiftIdLaneIdUserId(String from, String to, String shiftId,
			String laneId, String userId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		from = from+" 00:00:00";
		to = to+" 23:59:59";
		
		Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where createTimeStamp>='"+from+"' and createTimeStamp<='"+to+"' and cancellationCode is null and shiftId='"+shiftId+"' and laneId='"+laneId+"' and createUserID='"+userId+"'", TollTransaction.class);
	
		List<TollTransaction> tollTransactions = theQuery.getResultList();
		
		return tollTransactions;
	}
}





