package com.tollsecure.dao;

import com.tollsecure.entity.CashupDeclaration;

public interface CashupDeclarationDAO {

	void saveDeclaration(CashupDeclaration theCashupDelaration);

	CashupDeclaration getDeclarationIfExist(String checkdate, Integer laneId, Integer shiftId);

}
