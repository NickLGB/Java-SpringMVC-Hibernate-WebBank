package bg.jwd.webbank.business;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.jwd.webbank.dao.AccountDao;
import bg.jwd.webbank.dao.OperationDao;
import bg.jwd.webbank.dto.AccountDto;
import bg.jwd.webbank.security.UserUtils;

@Service
public final class BankServiceImpl implements BankService {

	private final CurrencyConverter currencyConverter;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private OperationDao operationDao;

	public BankServiceImpl() {
		this.currencyConverter = new CurrencyConverter();
	}

	@Override
	public void deposit(String username, String accountNumber, BigDecimal amount, String currency) {
		AccountDto account = prepareAccount(username, accountNumber, amount);

		String accountCurrency = account.getCurrency();
		BigDecimal newAmount = getAmountInAccountCurrency(amount, currency, accountCurrency);

		BigDecimal amountAfterDeposit = account.getAmount().add(newAmount);
		account.setAmount(amountAfterDeposit);

		accountDao.updateAccount(account);

		String performedBy = UserUtils.getUser().getUsername();
		operationDao.addOperation("deposit", username, accountNumber, amount, currency, performedBy);
	}

	@Override
	public void withdraw(String username, String accountNumber, BigDecimal amount, String currency) {
		AccountDto account = prepareAccount(username, accountNumber, amount);

		String accountCurrency = account.getCurrency();
		BigDecimal amountAfterConversion = getAmountInAccountCurrency(amount, currency, accountCurrency);

		if (amountAfterConversion.compareTo(account.getAmount()) > 0) {
			throw new IllegalArgumentException("The amount of withdraw cannot be more than balance");
		}

		BigDecimal amountAfterWithdraw = account.getAmount().subtract(amountAfterConversion);
		account.setAmount(amountAfterWithdraw);

		accountDao.updateAccount(account);

		String performedBy = UserUtils.getUser().getUsername();
		operationDao.addOperation("withdraw", username, accountNumber, amount, currency, performedBy);
	}

	private AccountDto prepareAccount(String username, String accountNumber, BigDecimal amount) {
		validate(username);
		validate(accountNumber);
		validateAmount(amount);
		ensureAccountExist(username, accountNumber);

		return accountDao.getAccountsByUsername(username).stream()
				.filter(a -> a.getAccountNumber().equals(accountNumber)).findFirst().get();
	}

	private BigDecimal getAmountInAccountCurrency(BigDecimal amount, String currency, String accountCurrency) {
		if (!accountCurrency.equals(currency)) {
			return currencyConverter.convert(amount, currency, accountCurrency);
		}

		return amount;
	}

	private void validate(String str) {
		if (str == null || str.trim().isEmpty()) {
			throw new IllegalArgumentException(str + " cannot be null or empty.");
		}
	}

	private void validateAmount(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("The amount should be positive.");
		}
	}

	private void ensureAccountExist(String username, String accountNumber) {
		if (!accountDao.getAccountsByUsername(username).stream().filter(a -> accountNumber.equals(a.getAccountNumber()))
				.findFirst().isPresent()) {
			throw new IllegalArgumentException("This account does not exist in database.");
		}
	}
}
