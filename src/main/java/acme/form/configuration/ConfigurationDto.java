package acme.form.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfigurationDto {

	protected int id;
	protected int version;
	protected String defaultCurrency;
	protected String acceptedCurrencies;
	protected Integer strongSpamThreshold;
	protected Integer weakSpamThreshold;
}
