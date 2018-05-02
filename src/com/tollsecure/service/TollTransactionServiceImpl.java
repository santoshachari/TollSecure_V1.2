package com.tollsecure.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tollsecure.dao.TollTransactionDAO;
import com.tollsecure.entity.Shift;
import com.tollsecure.entity.TollTransaction;

@Service
public class TollTransactionServiceImpl implements TollTransactionService {

	//need to inject TollTransaction DAO
	@Autowired
	private TollTransactionDAO tollTransactionDAO;
	
	
	@Override
	@Transactional
	public List<TollTransaction> getTollTransactions() {
		return tollTransactionDAO.getTollTransactions();
	}


	@Override
	@Transactional
	public void saveTollTransaction(TollTransaction theTollTransaction) {
		tollTransactionDAO.saveTollTransaction(theTollTransaction);
		
	}


	@Override
	@Transactional
	public TollTransaction getLastTollTransaction(Integer laneId, Shift currentShift) {
		return tollTransactionDAO.getLastTollTransaction(laneId, currentShift);
	}


	@Override
	@Transactional
	public List<TollTransaction> getTollTransactionsForCurrentShift(Integer laneId, Shift currentShift,
			Date checkingDate) {
		return tollTransactionDAO.getTollTransactionsForCurrentShift(laneId,  currentShift, checkingDate);
	}


	@Override
	@Transactional
	public List<TollTransaction> getTollTransactionsForShift(Integer laneId, Integer plazaId, String shiftId,
			Date checkingDate) {
		return tollTransactionDAO.getTollTransactionsForShift(laneId, plazaId, shiftId, checkingDate);
	}


	@Override
	@Transactional
	public HashMap<String, String> getUserIdAndFNLNames(String stDateTime, String edDateTime, String laneId) {
		return tollTransactionDAO.getUserIdAndFNLNames(stDateTime, edDateTime, laneId);
	}


	@Override
	@Transactional
	public TollTransaction getTheSavedOne(String vnum, Integer laneId) {
		return tollTransactionDAO.getTheSavedOne(vnum, laneId);
	}


	@Override
	@Transactional
	public TollTransaction searchTicket(String ticketNumber) {
		return tollTransactionDAO.searchTicket(ticketNumber);
	}


	@Override
	@Transactional
	public boolean getReturnStatus(String vehicleNumber) {
		return tollTransactionDAO.getReturnStatus(vehicleNumber);
	}


	@Override
	@Transactional
	public List<Object[]> getTransactionsOfAVehicle(String laneId, String startTime, String endTime, String date, String vnumber, String vehicleClass) {
		return tollTransactionDAO.getTransactionsOfAVehicle(laneId, startTime, endTime, date, vnumber, vehicleClass);
	}


	@Override
	@Transactional
	public TollTransaction getTollTransactionFromId(String transactionId) {
		return tollTransactionDAO.getTollTransactionFromId(transactionId);
	}


	@Override
	@Transactional
	public TollTransaction getImageTransaction() {
		return tollTransactionDAO.getImageTransaction();
	}


	@Override
	@Transactional
	public List<Object[]> getTransactionDetails(String transactionId) {
		// TODO Auto-generated method stub
		return tollTransactionDAO.getImageTransaction(transactionId);
	}

}
