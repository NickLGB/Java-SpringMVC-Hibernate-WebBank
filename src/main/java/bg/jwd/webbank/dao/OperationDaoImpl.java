package bg.jwd.webbank.dao;

import java.math.BigDecimal;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bg.jwd.webbank.entities.AccountEntity;
import bg.jwd.webbank.entities.Operation;
import bg.jwd.webbank.entities.User;

@Repository
public class OperationDaoImpl implements OperationDao {

	private long idCounter;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private UserDao userDao;

	@Transactional
	@Override
	public boolean addOperation(String operationType, String username, String accountNumber, BigDecimal amount,
			String currency, String performedBy) {
		Operation operation = new Operation();
		operation.setId(++idCounter);

		AccountEntity account = accountDao.getAccountByAccountNumber(accountNumber);
		operation.setAccount(account);

		operation.setOperationType(operationType);
		operation.setAmount(amount);
		operation.setCurrency(currency);

		User user = userDao.findByUsername(performedBy);
		operation.setUser(user);

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		session.save(operation);
		tx.commit();
		session.close();

		return true;
	}

}
