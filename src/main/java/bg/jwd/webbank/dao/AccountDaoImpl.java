package bg.jwd.webbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bg.jwd.webbank.dto.AccountDto;
import bg.jwd.webbank.entities.AccountEntity;
import bg.jwd.webbank.entities.User;
import bg.jwd.webbank.exceptions.DaoException;

@SuppressWarnings("unchecked")
@Repository
public final class AccountDaoImpl implements AccountDao {

	private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserDao userDao;

	@Override
	public List<AccountDto> getAllAccounts() {

		String sql = "FROM AccountEntity";
		Query query = sessionFactory.openSession().createQuery(sql);
		List<AccountEntity> accountEntities = query.list();
		List<AccountDto> accountDtos = new ArrayList<>();
		for (AccountEntity accountEntity : accountEntities) {
			accountDtos.add(mapEntityToDto(accountEntity));
		}

		return accountDtos;
	}

	@Override
	public List<AccountDto> getAccountsByUsername(String username) {
		Criteria criteria = sessionFactory.openSession().createCriteria(AccountEntity.class);
		User accountUser = userDao.findByUsername(username);
		criteria.add(Restrictions.like("accountUser", accountUser));

		List<AccountEntity> accountEntities = criteria.list();

		if (!accountEntities.stream().anyMatch(a -> a.getAccountUser().getUsername().equals(username))) {
			throw new IllegalArgumentException("This user do not have any accounts.");
		}

		List<AccountDto> accountDtos = new ArrayList<>();
		for (AccountEntity accountEntity : accountEntities) {
			accountDtos.add(mapEntityToDto(accountEntity));
		}

		return accountDtos;
	}

	@Override
	public AccountEntity getAccountByAccountNumber(String accountNumber) {
		Criteria criteria = sessionFactory.openSession().createCriteria(AccountEntity.class);
		criteria.add(Restrictions.like("accountNumber", accountNumber));

		List<AccountEntity> accounts = criteria.list();

		if (!accounts.isEmpty()) {
			return accounts.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public boolean addAccount(AccountDto accountDto) {
		List<AccountDto> allAccounts = getAllAccounts();

		if (allAccounts.stream().anyMatch(a -> a.getAccountNumber().equals(accountDto.getAccountNumber()))) {
			throw new IllegalArgumentException("This account already exist in database.");
		}

		AccountEntity account = mapDtoToEntity(accountDto);
		long idCounter = getAllAccounts().size();
		account.setId(++idCounter);

		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			session.save(account);
			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}

			logger.error("Error", e);
			throw new DaoException("Account cannot be created");
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return true;
	}

	@Override
	@Transactional
	public boolean updateAccount(AccountDto accountDto) {
		AccountEntity account = mapDtoToEntity(accountDto);

		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			session.update(account);
			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}

			logger.error("Error", e);
			throw new DaoException("Account cannot be created");
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return true;
	}

	private AccountEntity mapDtoToEntity(AccountDto accountDto) {
		AccountEntity account = new AccountEntity();

		account.setId(accountDto.getId());
		User accountUser = userDao.findByUsername(accountDto.getUsername());
		if (accountUser == null) {
			throw new IllegalArgumentException("Cannot create account: the specified user don't exist in database.");
		}

		account.setAccountUser(accountUser);
		account.setAccountNumber(accountDto.getAccountNumber());
		account.setAmount(accountDto.getAmount());
		account.setCurrency(accountDto.getCurrency());
		User createdBy = userDao.findByUsername(accountDto.getCreatedBy());
		account.setCreatedBy(createdBy);

		return account;
	}

	private AccountDto mapEntityToDto(AccountEntity accountEntity) {
		AccountDto account = new AccountDto();
		account.setId(accountEntity.getId());
		account.setUsername(accountEntity.getAccountUser().getUsername());
		account.setAccountNumber(accountEntity.getAccountNumber());
		account.setAmount(accountEntity.getAmount());
		account.setCurrency(accountEntity.getCurrency());
		account.setCreatedBy(accountEntity.getCreatedBy().getUsername());

		return account;
	}
}
