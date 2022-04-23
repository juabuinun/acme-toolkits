package acme.entities.announcement;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.entities.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class Announcement extends AbstractEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3138058048810637343L;

	@NotBlank
	@Length(max = 100)
	protected String title;

	@Past
	@NotNull
	protected LocalDateTime creationDate;

	@NotBlank
	@Length(max = 255)
	protected String body;
	
	@NotNull
	protected Boolean critical;

	@URL
	protected String link;
}
