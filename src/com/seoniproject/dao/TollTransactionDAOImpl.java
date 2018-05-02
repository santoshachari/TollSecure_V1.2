package com.seoniproject.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seoniproject.entity.Shift;
import com.seoniproject.entity.TollTransaction;
import com.seoniproject.entity.User;

@Repository
public class TollTransactionDAOImpl implements TollTransactionDAO {

	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<TollTransaction> getTollTransactions() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction", TollTransaction.class);
		
		//execute query and get result list
		List<TollTransaction> tollTransactions = theQuery.getResultList();
		
		//return the results
		return tollTransactions;
	}

	@Override
	public void saveTollTransaction(TollTransaction theTollTransaction) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//save the tollTransaction ... finally LOL
		currentSession.saveOrUpdate(theTollTransaction);
		
	}

	@Override
	public TollTransaction getLastTollTransaction(Integer laneId, Shift currentShift) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat hr = new SimpleDateFormat("HH");
		
		Date today = new Date();
		String today1 = sdf.format(today);
		Integer currentHr = Integer.parseInt(hr.format(today));
		
		String stDtTime = null;
		String edDtTime = null;
		
		String stTime = stf.format(currentShift.getStartTime());
		String edTime = stf.format(currentShift.getEndTime());
		
		Integer sthr = Integer.parseInt(stTime.split(":")[0]);
		Integer edhr = Integer.parseInt(edTime.split(":")[0]);
		
		//the problem is with sthr > edhr only as the day changes
		if (sthr > edhr && sthr > currentHr) { //day changed
			
			//once check current hour is greater or lesser than sthr so that we can decide 
			//whether to take previous day + today records or today records
			//records are in previous and todays
			Date yesterday = new Date(today.getTime() - (1000 * 60 * 60 * 24));
			String yesterday1 = sdf.format(yesterday);
			
			stDtTime = yesterday1+" "+stTime;
			edDtTime = today1+" "+edTime;
			
		} else if (sthr > edhr && sthr <= currentHr) { //that is asking in the same day
			
			Date nextDay = new Date(today.getTime() + (1000 * 60 * 60 * 24));
			String nextDay1 = sdf.format(nextDay);
			
			stDtTime = today1+" "+stTime;
			edDtTime = nextDay1+" "+edTime;
			
		} else { //remaining cases hour is in current day or entire shift is in one day
			stDtTime = today1+" "+stTime;
			edDtTime = today1+" "+edTime;
		}
		
		System.out.println("-=-=-=-=-=======>>>>start time: "+stDtTime+" end time: "+edDtTime);
		//create a query
		//Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where laneId = '"+laneId+"' and transactionTimeStamp >='"+stDtTime+"' and transactionTimeStamp <= '"+edDtTime+"' order by transactionId DESC", TollTransaction.class);
		
		//the above query is modified for performance
		Query<TollTransaction> theQuery = 
				currentSession.createQuery("from TollTransaction order by transactionId desc", TollTransaction.class);
		theQuery.setMaxResults(5000);
		
		List<TollTransaction> lastTollTransaction = theQuery.getResultList();

		SimpleDateFormat sddf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date stDtTime1=null;
		try {
			stDtTime1 = sddf.parse(stDtTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date edDtTime1=null;
		try {
			edDtTime1 = sddf.parse(edDtTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TollTransaction lastOne = null;
		
		for (TollTransaction tolltract: lastTollTransaction) {

			Date tranctTime = tolltract.getTransactionTimeStamp();
			if (tranctTime.equals(stDtTime1) || tranctTime.after(stDtTime1)) {
				if (tranctTime.equals(edDtTime1) || tranctTime.before(edDtTime1)) {
					if (laneId==tolltract.getLaneId()) {
						lastOne = tolltract;
						break;
					}
				}
			}
		}
		
		// TODO Auto-generated method stub
		return lastOne;
	}

	
	@Override
	public List<TollTransaction> getTollTransactionsForCurrentShift(Integer laneId, Shift currentShift, Date checkingDate) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String cDate = sdf.format(checkingDate);

		if(currentShift == null) return null;
		
		//get the timings of that shift
		Date startTime1 = currentShift.getStartTime();
		Date endTime1 = currentShift.getEndTime();
		
		if(startTime1==null || endTime1==null || currentShift.getStatusFlag().equals("DEACTIVE")) return null;
		
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
		String startTime = stf.format(startTime1);
		String endTime = stf.format(endTime1);
		
		//also get the hour in which the list is queried
		SimpleDateFormat hr = new SimpleDateFormat("HH");
		Integer chr = Integer.parseInt(hr.format(checkingDate));
		
		//if end time is less than start time hours the day has changed
		Integer sthr = Integer.parseInt(startTime.split(":")[0]);
		Integer edhr = Integer.parseInt(endTime.split(":")[0]);
		
		String checkDate = sdf.format(checkingDate);
		String stDtTime = null;
		String edDtTime = null;
		
		if (sthr > edhr && chr >= sthr) {
			//start time and end times are today and tomorrow
			Date checkNextDate1 = new Date(checkingDate.getTime()+(1000 * 60 * 60 * 24));
			String checkNextDate = sdf.format(checkNextDate1);
			
			stDtTime = checkDate+" "+startTime;
			edDtTime = checkNextDate+" "+endTime;
		} else if (sthr > edhr && chr < sthr) {
			//start time and end time are today and yesterday
			Date checkPreviousDate1 = new Date(checkingDate.getTime()-(1000 * 60 * 60 * 24));
			String checkPreviousDate = sdf.format(checkPreviousDate1);
			
			stDtTime = checkPreviousDate+" "+startTime;
			edDtTime = checkDate+" "+endTime;
		} else {
			//both are same day
			stDtTime = checkDate+" "+startTime;
			edDtTime = checkDate+" "+endTime;
		}

		//create a query
		Query<TollTransaction> theQuery = 
				currentSession.createQuery("from TollTransaction where  laneId='"+laneId+"' and createTimeStamp >='"+stDtTime+"' and createTimeStamp <='"+edDtTime+"'", TollTransaction.class);
				
		List<TollTransaction> theTolltransactions = theQuery.getResultList();
		
		return theTolltransactions;
	}

	//this is for cashup
	@Override
	public List<TollTransaction> getTollTransactionsForShift(Integer laneId, Integer plazaId, String shiftId,
			Date checkingDate)  {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String cDate = sdf.format(checkingDate);
		
		//get all shifts for that tollPlaza
		Query<Shift> theShiftQuery = 
				currentSession.createQuery("from Shift where shiftId='"+shiftId+"'", Shift.class);
		
		//as there is only one (A, B ,C ,D) shift per tollPlaza 
		List<Shift> theShifts = theShiftQuery.getResultList();
		
		System.out.println("=====>>>>"+theShifts);
		
		if (theShifts.size() == 0) return null; //shifts may not be set or there is no that shift as it is coming from cash up
		
		
		if (theShifts.get(0).getStatusFlag()=="DEACTIVE") return null; //shift deactivated
		
		//get the timings of that shift
		Date startTime1 = theShifts.get(0).getStartTime();
		Date endTime1 = theShifts.get(0).getEndTime();
		String shiftDesc = theShifts.get(0).getShiftDesc();
		
		if (startTime1 == null || endTime1 == null) return null;
		
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
		String startTime = stf.format(startTime1);
		String endTime = stf.format(endTime1);
		
		//here request is coming from cashup so always return the transactions in 
		//the shift which started in that date only 
		//i.e never consider shifts started in previous day irrespective of current hour
		
		//also check that checkingDate should be less than or equal to current date
		//create date of today with hours and mins and secs as 0s
		Date today1 = new Date();
		String today = sdf.format(today1);
		
		SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date todayStart = null;
		try {
			 todayStart = sdf.parse(today+" 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (todayStart.before(checkingDate)) return null; //checking date is future 
		
		//now generate end and start date
		//also get the hour in which the list is queried
		SimpleDateFormat hr = new SimpleDateFormat("HH");
				
		//if end time is less than start time hours the day has changed
		Integer sthr = Integer.parseInt(startTime.split(":")[0]);
		Integer edhr = Integer.parseInt(endTime.split(":")[0]);
		
		String stDtTime = null;
		String edDtTime = null;
		
		if (sthr > edhr) {
			stDtTime = cDate+" "+startTime;
			//add 5 mins to end time for reports (cashup) and check for only given shiftDesc
			Date nextDay1 = new Date(checkingDate.getTime()+(1000 * 60 * 60 * 24));
			String nextDay = sdf.format(nextDay1);
			edDtTime = nextDay+" "+endTime;
		} else {
			stDtTime = cDate+" "+startTime; 
			edDtTime = cDate+" "+endTime;
		}
		
		//always add 5 mins to end date time and check for the shift description also
		Date endDateTime1 = null;
		try {
			endDateTime1 = sdtf.parse(edDtTime);
			endDateTime1 = new Date(endDateTime1.getTime()+(5*60*1000));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		edDtTime = sdtf.format(endDateTime1);
		
		//create a query
		Query<TollTransaction> theQuery = 
				currentSession.createQuery("from TollTransaction where  laneId='"+laneId+"' and createTimeStamp >='"+stDtTime+"' and createTimeStamp <='"+edDtTime+"' and shiftDescription='"+shiftDesc+"'", TollTransaction.class);
	
		
		List<TollTransaction> theTolltransactions = theQuery.getResultList();
				
		return theTolltransactions;
		
	}

	//this is not used as we re taking it from floating amount
	@Override
	public HashMap<String, String> getUserIdAndFNLNames(String stDateTime, String edDateTime, String laneId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//create a query
		Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where createTimeStamp>='"+stDateTime+"' and createTimeStamp<='"+edDateTime+"' and laneId='"+laneId+"'", TollTransaction.class);
	
		List<TollTransaction> tollTransactions = theQuery.getResultList();
		
		if (tollTransactions.size()==0) return null;
		
		//take the middle transaction while considering list
		Integer userId1 =  tollTransactions.get(tollTransactions.size()/2).getCreateUserID();
		
		Query<User> theUserQuery =
				currentSession.createQuery("from User where userId = '"+userId1+"'", User.class);
		
		List<User> theUser = theUserQuery.getResultList();
		
		if (theUser.size()==0) return null;
		
		String userFNLNames = theUser.get(0).getUserFirstName()+" "+theUser.get(0).getUserLastName();
		
		HashMap<String, String> hm = new HashMap<String, String>();
		String userId = userId1.toString();
		hm.put(userId, userFNLNames);
		
		System.out.println("in the thing=======>>>"+hm+"============>>>");
		
		return hm;
	}

	@Override
	public TollTransaction getTheSavedOne(String vnum, Integer laneId) {
		
		//get time which is 2 mins back from now
		Date twoMinsPast1 = new Date(new Date().getTime()-(2*60*1000));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String twoMinsPast = sdf.format(twoMinsPast1);
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
						
		//create a query
		//Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where createTimeStamp>='"+twoMinsPast+"' and laneId='"+laneId+"' and vehicleNumber='"+vnum+"'", TollTransaction.class);
		
		//the above query is modified to below
		Query<TollTransaction> theQuery = 
				currentSession.createQuery("from TollTransaction where vehicleNumber='"+vnum+"' order by transactionId desc", TollTransaction.class);
		theQuery.setMaxResults(1);
		
		List<TollTransaction> tollTransactions = theQuery.getResultList();
		
		if (tollTransactions.size()==0) return null;
		
		//return last entry in case of entered more than once
		return tollTransactions.get(tollTransactions.size()-1);

	}

	@Override
	public TollTransaction searchTicket(String ticketNumber) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
								
		//create a query
		Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where ticketCode='"+ticketNumber+"'", TollTransaction.class);
		
		List<TollTransaction> tollTransactions = theQuery.getResultList();
		
		if (tollTransactions.size()==0) return null;
		
		return tollTransactions.get(0);
	}

	@Override
	public boolean getReturnStatus(String vehicleNumber) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Date yesterday1 = new Date(new Date().getTime() - (1000*24*60*60));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String yesterday = sdf.format(yesterday1);
		
		//create a query
		Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where vehicleNumber='"+vehicleNumber+"' and createTimeStamp>='"+yesterday+"'", TollTransaction.class);
	
		List<TollTransaction> transaction = theQuery.getResultList();
		
		if(transaction.size()>0) {
			return true;
		}
		
		return false;
	}

	@Override
	public List<Object[]> getTransactionsOfAVehicle(String laneId, String startTime, String endTime, String date, String vnumber, String vehicleClass) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//String start = date+" "+startTime+":00";
		//String end = date+" "+endTime+":00";
		
		//create a query
		Query<Object[]> theQuery = null;
		/*if (!vnumber.equals("") && !laneId.equals("")) {
			theQuery = currentSession.createQuery("select tt.transactionId, tt.vehicleNumber, tt.ticketCode, tc.vehicleClass, tt.journeyType, tt.tollAmt "
					+ "from TollTransaction tt join TollConfig tc on tt.vehicleClassId = tc.vehicleClassId"
					+ " where tt.vehicleNumber='"+vnumber+"' and "
					+ "tt.laneId='"+laneId+"' and tt.createTimeStamp>='"+start+"' and tt.createTimeStamp<='"+end+"' "
					+ "and tt.cancellationCode is null");
		} else if(!laneId.equals("")) {
			theQuery = currentSession.createQuery("select tt.transactionId, tt.vehicleNumber, tt.ticketCode, tc.vehicleClass, tt.journeyType, tt.tollAmt from "
					+ "TollTransaction tt join TollConfig tc on tt.vehicleClassId = tc.vehicleClassId where tt.laneId ='"+laneId+"' and tt.createTimeStamp>='"+start+"' and "
					+ "tt.createTimeStamp<='"+end+"' and tt.cancellationCode is null");
		} else if(!vnumber.equals("")){
			theQuery = currentSession.createQuery("select tt.transactionId, tt.vehicleNumber, tt.ticketCode, tc.vehicleClass, tt.journeyType, tt.tollAmt from "
					+ "TollTransaction tt join TollConfig tc on tt.vehicleClassId = tc.vehicleClassId where tt.vehicleNumber='"+vnumber+"' and tt.createTimeStamp>='"+start+"' and "
					+ "tt.createTimeStamp<='"+end+"' and tt.cancellationCode is null");
		} else {
			theQuery = currentSession.createQuery("select tt.transactionId, tt.vehicleNumber, tt.ticketCode, tc.vehicleClass, tt.journeyType, tt.tollAmt from "
					+ "TollTransaction tt join TollConfig tc on tt.vehicleClassId = tc.vehicleClassId where tt.createTimeStamp>='"+start+"' and "
					+ "tt.createTimeStamp<='"+end+"' and tt.cancellationCode is null");
		}*/
		
		theQuery = currentSession.createQuery("select tt.transactionId, tt.vehicleNumber, tt.ticketCode, tc.vehicleClass, tt.journeyType, tt.tollAmt, tt.createTimeStamp, "
				+ " tt.shiftDescription, tt.imageAddress, tu.userFirstName, tu.userLastName, COALESCE(tt.passId, '0'), COALESCE(tt.exemptId, '0'), COALESCE(tt.concessionId, '0') from TollTransaction tt join TollConfig tc on tt.vehicleClassId = tc.vehicleClassId"
				+ " join User tu on tu.userId = tt.createUserID "
				+ " where ('"+vnumber+"'='' or tt.vehicleNumber='"+vnumber+"') and ('"+date+"' = '' or date(tt.createTimeStamp)=date('"+date+"'))"
				+ " and ('"+startTime+"'='' or time('"+startTime+"')<=time(tt.createTimeStamp)) and ('"+endTime+"'='' or time('"+endTime+"')>=time(tt.createTimeStamp))"
				+ " and ('"+laneId+"'='' or tt.laneId='"+laneId+"') "
				+ " and tt.cancellationCode is null and ('"+vehicleClass+"'='' or tc.vehicleClass='"+vehicleClass+"')");
		
		List<Object[]> tollTransactions = theQuery.list();
		
		return tollTransactions;
	}

	@Override
	public TollTransaction getTollTransactionFromId(String transactionId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where transactionId='"+transactionId+"'", TollTransaction.class);
	
		List<TollTransaction> theTollTransaction = theQuery.getResultList();
		
		if (theTollTransaction.isEmpty()) return null;
		return theTollTransaction.get(0);
	}

	@Override
	public TollTransaction getImageTransaction() {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		Query<TollTransaction> theQuery = currentSession.createQuery("from TollTransaction where transactionId='1000185'", TollTransaction.class);
	
		List<TollTransaction> theTollTransaction = theQuery.getResultList();
		
		if (theTollTransaction.isEmpty()) return null;
		return theTollTransaction.get(0);
	}

	@Override
	public List<Object[]> getImageTransaction(String transactionId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query
		Query<Object[]> theQuery = currentSession.createQuery("select tt.ticketCode, tt.transactionTimeStamp,"
				+ "tt.vehicleNumber, u.userFirstName, u.userLastName, tt.shiftDescription, tt.tollAmt, tt.imageAddress,"
				+ "tc.vehicleClass, tt.journeyType,  COALESCE(tt.passId, '0'), COALESCE(tt.exemptId, '0'), COALESCE(tt.concessionId, '0') from User u join TollTransaction tt on tt.createUserID = u.userId join TollConfig tc"
				+ " on tc.vehicleClassId = tt.vehicleClassId where tt.transactionId='"+transactionId+"'");
		
		List<Object[]> transaction = theQuery.list();
		
		return transaction;
	}
}




















