package com.seoniproject.dao;

import com.seoniproject.entity.CashupDeclaration;

public interface CashupDeclarationDAO {

	void saveDeclaration(CashupDeclaration theCashupDelaration);

	CashupDeclaration getDeclarationIfExist(String checkdate, Integer laneId, Integer shiftId);

}
