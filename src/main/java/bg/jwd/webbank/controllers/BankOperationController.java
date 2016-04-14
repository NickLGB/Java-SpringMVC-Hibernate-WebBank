package bg.jwd.webbank.controllers;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import bg.jwd.webbank.business.BankService;
import bg.jwd.webbank.dao.AccountDao;
import bg.jwd.webbank.dto.AccountDto;
import bg.jwd.webbank.security.UserDetailsServiceImpl;
import bg.jwd.webbank.security.UserUtils;

@Controller
public class BankOperationController {

	private static final String BANK_REGISTER = "bankRegister";
	private static final String USER = "user";
	private static final String USERNAME = "username";
	private static final String ACCOUNT_NUMBER = "accountNumber";
	private static final String INITIAL_AMOUNT = "initialAmount";
	private static final String ACCOUNTS = "accounts";
	private static final String CURRENCY = "currency";

	private static final Logger logger = LoggerFactory.getLogger(BankOperationController.class);

	@Autowired
	private BankService webBank;

	@Autowired
	private AccountDao accountDao;

	@RequestMapping(value = { "", "/", "/bankRegister" }, method = RequestMethod.GET)
	public String loadBankRegister(Model model) {
		UserDetails loggedUser = UserUtils.getUser();
		model.addAttribute(USER, loggedUser);

		if (loggedUser.getAuthorities().contains(UserDetailsServiceImpl.BANK_EMPLOYEE_AUTHORITY)) {
			model.addAttribute(ACCOUNTS, accountDao.getAllAccounts());
		} else {
			String username = loggedUser.getUsername();
			model.addAttribute(ACCOUNTS, accountDao.getAccountsByUsername(username));
		}

		return BANK_REGISTER;
	}

	@Secured("ROLE_BANK_EMPLOYEE")
	@RequestMapping(value = "/createAccount", method = RequestMethod.GET)
	public String loadCreateAccount(Model model) {
		model.addAttribute(USER, UserUtils.getUser());

		return "createAccount";
	}

	@RequestMapping(value = "/operation", method = RequestMethod.GET)
	public String loadOperation(Model model) {
		model.addAttribute(USER, UserUtils.getUser());

		return "operation";
	}

	@RequestMapping(value = "/createAccountSave", method = RequestMethod.POST)
	public String createAccount(Model model, HttpServletRequest request) {
		AccountDto account = new AccountDto();

		account.setUsername(request.getParameter(USERNAME));
		account.setAccountNumber(request.getParameter(ACCOUNT_NUMBER));
		account.setAmount(new BigDecimal(request.getParameter(INITIAL_AMOUNT)));
		account.setCurrency(request.getParameter(CURRENCY));
		account.setCreatedBy(UserUtils.getUser().getUsername());

		accountDao.addAccount(account);

		model.addAttribute(USER, UserUtils.getUser());
		model.addAttribute(ACCOUNTS, accountDao.getAllAccounts());

		return BANK_REGISTER;
	}

	@RequestMapping(value = "/operation", method = RequestMethod.POST)
	public String doBanking(Model model, HttpServletRequest request) {

		String accountNumber = request.getParameter(ACCOUNT_NUMBER);
		BigDecimal amount = new BigDecimal(request.getParameter("amount"));
		String selectedOperation = request.getParameter("operation");
		String selectedCurrency = request.getParameter(CURRENCY);
		UserDetails loggedUser = UserUtils.getUser();
		String username = loggedUser.getUsername();

		if ("Withdraw".equals(selectedOperation)) {
			webBank.withdraw(username, accountNumber, amount, selectedCurrency);
		} else if ("Deposit".equals(selectedOperation)) {
			webBank.deposit(username, accountNumber, amount, selectedCurrency);
		}

		model.addAttribute(USER, loggedUser);
		model.addAttribute(ACCOUNTS, accountDao.getAccountsByUsername(username));

		return BANK_REGISTER;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest request, Exception exception) {
		logger.error("Request: " + request.getRequestURL() + " raised " + exception);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("errorMessage", exception.getMessage());
		modelAndView.setViewName("error");

		return modelAndView;
	}
}
