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
public class Patron extends UserRole{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4705014907365732723L;

	@NotBlank
	@Length(max = 100)
	protected String			company;

	@NotBlank
	@Length(max = 255)
	protected String			statement;
	
	@URL
	protected String 			info;
}
