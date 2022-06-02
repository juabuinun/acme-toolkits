package acme.entities.configuration;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
	@Size(max=100)
	protected String word;

	protected boolean strong;
	
	@NotBlank
	@Size(max=2)
	protected String language;

}
