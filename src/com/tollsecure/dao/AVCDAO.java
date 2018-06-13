package com.tollsecure.dao;

import com.tollsecure.entity.AVC;

public interface AVCDAO {

	boolean checkForTransactionIdIfExists(Integer transactionId);

	void save(AVC theAVC);

}
