package acme.entities.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
		
		public Configuration(){
			this.defaultCurrency = "";
			this.acceptedCurrencies = "";
			this.strongSpamThreshold = 0;
			this.weakSpamThreshold = 0;
			this.spamWords = new ArrayList<>();
		}

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
		
		@OneToMany(mappedBy="configuration", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
		protected List<SpamWord> spamWords;
		
}
