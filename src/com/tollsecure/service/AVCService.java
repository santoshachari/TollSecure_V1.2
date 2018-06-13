package com.tollsecure.service;

import com.tollsecure.entity.AVC;
import com.tollsecure.entity.LastTollTransaction;

public interface AVCService {

	boolean checkForTransactionIdIfExists(Integer transacionId);

	void save(AVC theAVC);

}
