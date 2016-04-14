package bg.jwd.webbank.business;

import java.math.BigDecimal;

public interface BankService {

	void withdraw(String username, String accountNumber, BigDecimal amount, String currency);

	void deposit(String username, String accountNumber, BigDecimal amount, String currency);
}
