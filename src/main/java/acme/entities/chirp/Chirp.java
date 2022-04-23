
package acme.entities.chirp;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

import acme.framework.entities.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class Chirp extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2572827077816343517L;

	@NotNull
	@Past
	protected LocalDateTime		creationDate;

	@NotBlank
	@Length(max = 100)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			author;

	@NotBlank
	@Length(max = 255)
	protected String			body;

	@Email
	protected String			email;

}
