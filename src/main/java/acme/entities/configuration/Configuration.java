package acme.entities.configuration;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import acme.framework.entities.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class Configuration extends AbstractEntity{
	
	// Serialisation identifier -----------------------------------------------

		protected static final long	serialVersionUID	= 1L;

		// Attributes -------------------------------------------------------------
		@NotBlank
		protected String defaultCurrency;
		
		@NotBlank
		protected String acceptedCurrencies;
		
		@Range(max = 100, min = 0)
		@NotNull
		protected Integer strongSpamThreshold;
		
		@Range(max = 100, min = 0)
		@NotNull
		protected Integer weakSpamThreshold;
		
		@OneToMany
		protected List<SpamWord> spamWords;
		
}
