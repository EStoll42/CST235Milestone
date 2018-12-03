package edu.gcu.bootcamp.ericstoll.cst235milestone;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class TestUnit {

	Account acct = new Account();
	
	@Test
	public void testDoDeposit() {
		
		BigDecimal amount = new BigDecimal(1000.00);
		acct.setAccount_balance(amount);
		BigDecimal expected = new BigDecimal(1000.00);
		BigDecimal actual = acct.getAccount_balance();
		assertEquals(actual,expected);
	}
	
	@Test
	public void testDoWithdraw() {
		
		BigDecimal amount = new BigDecimal(1000.00);
		acct.setAccount_balance(amount);
		BigDecimal expected = new BigDecimal(1000.00);
		BigDecimal actual = acct.getAccount_balance();
		assertEquals(actual,expected);
	}
}