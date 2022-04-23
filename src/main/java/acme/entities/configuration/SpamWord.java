package acme.entities.configuration;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import acme.framework.entities.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class SpamWord extends AbstractEntity{
	
	private static final long serialVersionUID = 7679622066378796910L;
	
	@ManyToOne
	protected Configuration configuration;
	
	@NotBlank
	protected String word;
	
	@NotNull
	protected Boolean strong;
	
	protected String language;

}
