package com.tollsecure.service;

import java.util.List;

import com.tollsecure.entity.TollTransaction;

public interface ReportService {

	List<TollTransaction> getTollTransactionsFrom(String from, String to);

	List<TollTransaction> getTollTransactionsFromLaneId(String from, String to, String laneId);

	List<TollTransaction> getTollTransactionsFromUserId(String from, String to, String userId);

	List<TollTransaction> getTollTransactionsFromshiftId(String from, String to, String shiftId);

	List<TollTransaction> getTollTransactionsFromLaneIdUserId(String from, String to, String laneId, String userId);

	List<TollTransaction> getTollTransactionsFromShiftIdUserId(String from, String to, String shiftId, String userId);

	List<TollTransaction> getTollTransactionsFromShiftIdLaneId(String from, String to, String shiftId, String laneId);

	List<TollTransaction> getTollTransactionsFromShiftIdLaneIdUserId(String from, String to, String shiftId,
			String laneId, String userId);

}
