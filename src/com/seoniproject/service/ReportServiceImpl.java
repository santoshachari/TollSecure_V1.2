package com.seoniproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seoniproject.dao.ReportDAO;
import com.seoniproject.entity.TollTransaction;

@Service
public class ReportServiceImpl implements ReportService{

	@Autowired
	private ReportDAO theReportDAO;
	
	@Override
	@Transactional
	public List<TollTransaction> getTollTransactionsFrom(String from, String to) {
		return theReportDAO.getTollTransactionsFrom(from, to);
	}

	@Override
	@Transactional
	public List<TollTransaction> getTollTransactionsFromLaneId(String from, String to, String laneId) {
		return theReportDAO.getTollTransactionsFromLaneId(from, to, laneId);
	}

	@Override
	@Transactional
	public List<TollTransaction> getTollTransactionsFromUserId(String from, String to, String userId) {
		return theReportDAO.getTollTransactionsFromUserId(from, to, userId);
	}

	@Override
	@Transactional
	public List<TollTransaction> getTollTransactionsFromshiftId(String from, String to, String shiftId) {
		return theReportDAO.getTollTransactionsFromshiftId(from, to, shiftId);
	}

	@Override
	@Transactional
	public List<TollTransaction> getTollTransactionsFromLaneIdUserId(String from, String to, String laneId,
			String userId) {
		return theReportDAO.getTollTransactionsFromLaneIdUserId(from, to, laneId, userId);
	}

	@Override
	@Transactional
	public List<TollTransaction> getTollTransactionsFromShiftIdUserId(String from, String to, String shiftId,
			String userId) {
		return theReportDAO.getTollTransactionsFromShiftIdUserId(from, to, shiftId, userId);
	}

	@Override
	@Transactional
	public List<TollTransaction> getTollTransactionsFromShiftIdLaneId(String from, String to, String shiftId,
			String laneId) {
		return theReportDAO.getTollTransactionsFromShiftIdLaneId(from, to, shiftId, laneId);
	}

	@Override
	@Transactional
	public List<TollTransaction> getTollTransactionsFromShiftIdLaneIdUserId(String from, String to, String shiftId,
			String laneId, String userId) {
		return theReportDAO.getTollTransactionsFromShiftIdLaneIdUserId(from, to, shiftId, laneId, userId);
	}

}
