package bg.jwd.webbank.business;

import java.math.BigDecimal;

public final class CurrencyConverter {

	ExchangeRateLoader rateLoader = new ExchangeRateLoader();

	public BigDecimal convert(BigDecimal value, String fromCurrency, String toCurrency) {
		double rate = rateLoader.getRate(fromCurrency, toCurrency);

		return value.multiply(BigDecimal.valueOf(rate));
	}
}
