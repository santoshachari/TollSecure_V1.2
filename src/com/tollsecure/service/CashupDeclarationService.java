package com.tollsecure.service;

import org.springframework.stereotype.Service;

import com.tollsecure.entity.CashupDeclaration;

public interface CashupDeclarationService {

	void saveDeclaration(CashupDeclaration theCashupDelaration);

	CashupDeclaration getDeclarationIfExist(String checkdate, Integer laneId, Integer shiftId);

}
