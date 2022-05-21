package acme.components.util;

import java.util.ArrayList;
import java.util.List;

import acme.form.SpamWordDto;
import lombok.Data;

@Data
public class ConfigCache {

	public enum CacheStatus {
		OFF, LIVE, RELOAD;
	}


	protected List<SpamWordDto>	spamWords;
	protected String			defaultCurrency;
	protected String			acceptedCurrencies;
	protected Integer			strongSpamThreshold;
	protected Integer			weakSpamThreshold;
	protected CacheStatus		status;
	
	public ConfigCache() {
		this.defaultCurrency = "";
		this.acceptedCurrencies = "";
		this.strongSpamThreshold = 0;
		this.weakSpamThreshold = 0;
		this.spamWords = new ArrayList<>();
		this.status = CacheStatus.OFF;
	}
}
