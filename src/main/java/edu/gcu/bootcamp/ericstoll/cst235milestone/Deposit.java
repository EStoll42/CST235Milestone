package edu.gcu.bootcamp.ericstoll.cst235milestone;

import java.math.BigDecimal;

public interface Deposit {
	
	void doDeposit(BigDecimal amount, Account account);
}