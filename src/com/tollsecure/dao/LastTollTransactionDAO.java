package com.tollsecure.dao;

import org.springframework.stereotype.Repository;

import com.tollsecure.entity.LastTollTransaction;

public interface LastTollTransactionDAO {

	LastTollTransaction getLastTollTransactionForLane(String laneCode);

	void saveOrUpdate(LastTollTransaction theLastTollTransaction);


}
