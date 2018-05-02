package com.tollsecure.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tollsecure.entity.Pass;
import com.tollsecure.entity.Shift;
import com.tollsecure.service.ShiftService;

@Repository
public class PasDAOImpl implements PasDAO {

	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ShiftService theShiftService;
		
	@Override
	public void saveOrUpdate(Pass pass) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(pass);
	}

	@Override
	public List<Object[]> getPassShifts(String from, String to, String shiftId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//get shift for shift id
		Shift shift = theShiftService.getShiftFromId(Integer.parseInt(shiftId)); 
		String shiftDesc = shift.getShiftDesc();
		
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
		String startTime = stf.format(shift.getStartTime());
		String endTime = stf.format(shift.getEndTime());

		Query<Object[]> theQuery = currentSession.createQuery("select DATE(createTimeStamp),"
				+ "vehicleClass, vehicleNumber, '"+shiftDesc+"', period, amount, passType from Pass"
				+ " where TIME(createTimeStamp) between '"+startTime+"' and '"+endTime+"' and"
				+ " createTimeStamp between '"+from+"' and '"+to+"'"
				+ " and cancellationCode is null");
		
		List<Object[]> passShifts = theQuery.list();
		
		return passShifts;
	}

	@Override
	public List<Object[]> getPassShifts(String from, String to) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		String AStart = "00:00:00";
		String AEnd = "07:59:59";
		String BStart = "08:00:00";
		String BEnd = "15:59:59";
		String CStart = "16:00:00";
		String CEnd = "23:59:59";
				
		Query<Object[]> theQuery = currentSession.createQuery("select DATE(createTimeStamp),"
				+ "vehicleClass, vehicleNumber, CASE WHEN TIME(createTimeStamp)"
				+ " BETWEEN '"+AStart+"' AND '"+AEnd+"' THEN 'A' "
				+ " WHEN TIME(createTimeStamp) BETWEEN '"+BStart+"' AND '"+BEnd+"' THEN 'B' "
				+ " WHEN TIME(createTimeStamp) BETWEEN '"+CStart+"' AND '"+CEnd+"' THEN 'C' "
				+ " END, "
				+ " period, amount, passType from Pass where createTimeStamp between '"+from+"' and '"+to+"'"
				+ " and cancellationCode is null");
				
		List<Object[]> passShifts = theQuery.list();
		
		return passShifts;
	}

	@Override
	public Pass getPassFromId(String passId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = currentSession.createQuery("from Pass where passId='"+passId+"' and cancellationCode is null");
		
		List<Pass> thePassList = theQuery.getResultList();
		
		if (thePassList.isEmpty()) return null;
		
		return thePassList.get(0);
	}

}
