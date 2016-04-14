package bg.jwd.webbank.dao;

import java.math.BigDecimal;

public interface OperationDao {

	boolean addOperation(String operation, String username, String accountNumber, BigDecimal amount, String currency,
			String performedBy);
}
