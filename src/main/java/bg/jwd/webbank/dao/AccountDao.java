package bg.jwd.webbank.dao;

import java.util.List;

import bg.jwd.webbank.dto.AccountDto;
import bg.jwd.webbank.entities.AccountEntity;

public interface AccountDao {

	List<AccountDto> getAllAccounts();

	List<AccountDto> getAccountsByUsername(String username);

	AccountEntity getAccountByAccountNumber(String accountNumber);

	boolean addAccount(AccountDto account);

	boolean updateAccount(AccountDto account);

}
