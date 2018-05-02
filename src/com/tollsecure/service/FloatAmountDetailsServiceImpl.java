package com.tollsecure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tollsecure.dao.FloatAmountDetailsDAO;
import com.tollsecure.entity.FloatAmountDetails;


@Service
public class FloatAmountDetailsServiceImpl implements FloatAmountDetailsService {

	@Autowired
	private FloatAmountDetailsDAO theFloatAmounntDAO;
	
	@Override
	@Transactional
	public FloatAmountDetails getFloatAmountAndOperatorFrom(String plazaId, String laneId, String date,
			String shiftId) {
		return theFloatAmounntDAO.getFloatAmountAndOperatorFrom(plazaId, laneId, date, shiftId);
	}

	@Override
	@Transactional
	public List<FloatAmountDetails> getSameDetailIfExist(String shiftDate1, String tollPlazaId1, String laneId1, String shiftId1) {
		return theFloatAmounntDAO.getSameDetailIfExist(shiftDate1, tollPlazaId1, laneId1, shiftId1);
	}

	@Override
	@Transactional
	public void saveOrUpdate(FloatAmountDetails sameDetails) {
		theFloatAmounntDAO.saveOrUpdate(sameDetails);
	}

}
