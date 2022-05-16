
package acme.entities.patronage;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.patronagereport.PatronageReport;
import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import acme.roles.Inventor;
import acme.roles.Patron;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Patronage extends AbstractEntity {

	protected static final long serialVersionUID = 1L;


	public enum Status {
		PROPOSED, ACCEPTED, DENIED
	}


	@NotNull
	@Enumerated(EnumType.STRING)
	protected Status		status;

	@NotNull
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$")
	protected String		code;

	@NotBlank
	@Length(min = 1, max = 255)
	protected String		legal;

	@NotNull
	protected Money			budget;

	@NotNull
	protected LocalDateTime	creationDate;

	@NotNull
	protected LocalDateTime	endDate;

	@URL
	protected String		info;

	@NotNull
	@ManyToOne(optional = false)
	protected Patron		sponsor;

	@NotNull
	@ManyToOne(optional = false)
	protected Inventor		sponsee;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "patronage")
	protected List<PatronageReport> reports;

}
