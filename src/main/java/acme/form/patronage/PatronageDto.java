
package acme.form.patronage;

import java.time.LocalDateTime;

import acme.entities.patronage.Patronage.Status;
import acme.framework.datatypes.Money;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatronageDto {

	protected int			id;
	protected int			version;
	protected Status		status;
	protected String		code;

	protected String		legal;

	protected Money			budget;

	protected LocalDateTime	creationDate;

	protected LocalDateTime	endDate;

	protected String		info;

	protected int			sponsorId;

	protected int			sponseeId;
	
}
