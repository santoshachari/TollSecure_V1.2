package com.seoniproject.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.seoniproject.entity.Shift;
import com.seoniproject.entity.TollTransaction;

public interface TollTransactionService {

	public List<TollTransaction> getTollTransactions();

	public void saveTollTransaction(TollTransaction theTollTransaction);

	public TollTransaction getLastTollTransaction(Integer laneId, Shift currentShift);

	public List<TollTransaction> getTollTransactionsForCurrentShift(Integer laneId, Shift currentShift,
			Date checkingDate);

	public List<TollTransaction> getTollTransactionsForShift(Integer laneId, Integer plazaId, String shiftId,
			Date checkingDate);

	public HashMap<String, String> getUserIdAndFNLNames(String stDateTime, String edDateTime, String laneId);

	public TollTransaction getTheSavedOne(String vnum, Integer laneId);

	public TollTransaction searchTicket(String ticketNumber);

	public boolean getReturnStatus(String vehicleNumber);

	public List<Object[]> getTransactionsOfAVehicle(String laneId, String startTime, String endTime, String date, String vnumber, String vehicleClass);

	public TollTransaction getTollTransactionFromId(String transactionId);

	public TollTransaction getImageTransaction();

	public List<Object[]> getTransactionDetails(String transactionId);


}
