
package acme.entities.luster;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.entities.item.Item;
import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Luster extends AbstractEntity {

	/**
	* 
	*/
	private static final long serialVersionUID = -6605709675337645584L;

//	@ManyToOne(optional = false)
//	@NotNull
//	protected Inventor owner;

	@NotBlank
	@Pattern(regexp ="^\\w{2}[0-9]{2}[0-9]{2}[0-9]{2}\\w{2}$")
	protected String	code;

	@NotNull
	@Past
	protected LocalDateTime creationDate;
	
	@NotNull
	protected LocalDate	startDate;

	@NotNull
	protected LocalDate	endDate;

	@NotBlank
	@Size(max = 100)
	protected String	title;

	@NotBlank
	@Size(max = 255)
	protected String	description;

	@Valid
	@NotNull
	protected Money		budget;

	@URL
	protected String	info;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	protected Item		item;


	@Transient
	public String getDuration() {
		String res = "";
		if (this.endDate != null) {
			final Period period = Period.between(this.startDate, this.endDate.plus(1, ChronoUnit.DAYS));
			res = String.format("%d$y %d$m %d$d ", period.getYears(), period.getMonths(), period.getDays());
		}
		return res;
	}
}
