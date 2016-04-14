package bg.jwd.webbank.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.jwd.webbank.exceptions.CurrencyConversionException;

public final class ExchangeRateLoader {

	private static final Logger logger = LoggerFactory.getLogger(ExchangeRateLoader.class);

	public double getRate(String fromCurrency, String toCurrency) {
		try {
			URL url = new URL("http://quote.yahoo.com/d/quotes.csv?f=l1&s=" + fromCurrency + toCurrency + "=X");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = reader.readLine();
			if (line.length() > 0) {
				return Double.parseDouble(line);
			}
			reader.close();
		} catch (IOException e) {
			logger.error("Currency conversion failed because of IOException", e);
			throw new CurrencyConversionException("Currency conversion cannot be done at this moment.");
		}

		return 0;
	}
}
