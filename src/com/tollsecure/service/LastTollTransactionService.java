package com.tollsecure.service;

import com.tollsecure.entity.LastTollTransaction;

public interface LastTollTransactionService {

	LastTollTransaction getLastTollTransactionForLane(String laneCode);

	void saveOrUpdate(LastTollTransaction theLastTollTransaction);

	
}
