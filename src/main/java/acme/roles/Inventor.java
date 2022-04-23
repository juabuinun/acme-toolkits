package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.roles.UserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class Inventor extends UserRole{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1765951964365226875L;

	@NotBlank
	@Length(max = 100)
	protected String			company;

	@NotBlank
	@Length(max = 255)
	protected String			statement;
	
	@URL
	protected String 			info;
}
