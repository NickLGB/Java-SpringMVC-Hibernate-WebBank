package bg.jwd.webbank.business;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class BankServiceImplTest {

	private static final String CURRENCY = "BGN";
	private static final String USER = "user";
	private static final String ACCOUNT_NUMBER = "1000";

	private BankService bankService;

	@Before
	public void setUp() {
		bankService = new BankServiceImpl();
	}

	// deposit tests
	@Test(expected = IllegalArgumentException.class)
	public void testDepositNegativeAmount() {
		bankService.deposit(USER, ACCOUNT_NUMBER, new BigDecimal(-1), CURRENCY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDepositZeroAmount() {
		bankService.deposit(USER, ACCOUNT_NUMBER, BigDecimal.ZERO, CURRENCY);
	}

	// withdraw tests
	@Test(expected = IllegalArgumentException.class)
	public void testWithdrawNegativeAmount() {
		bankService.withdraw(USER, ACCOUNT_NUMBER, new BigDecimal(-1), CURRENCY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithdrawZeroAmount() {
		bankService.withdraw(USER, ACCOUNT_NUMBER, BigDecimal.ZERO, CURRENCY);
	}
}
