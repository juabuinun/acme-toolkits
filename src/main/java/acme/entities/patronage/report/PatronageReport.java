
package acme.entities.patronage.report;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.entities.patronage.Patronage;
import acme.framework.entities.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class PatronageReport extends AbstractEntity {

	private static final long	serialVersionUID	= 3224167214427507153L;

	@NotNull
	@Past
	protected LocalDateTime		creationDate;

	@NotBlank
	@Size(max = 255)
	protected String			memorandum;

	@URL
	protected String			info;


	@NotNull
	@Column(unique = true)
	protected Long				serialNumber;


	@Transient
	public String getSequenceNumber() {
		return String.format("%s:%s", this.patronage.getCode(), String.format("%04d", this.serialNumber));
	}


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Patronage patronage;

}
