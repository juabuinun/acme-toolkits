
package acme.form.patronage;

import java.time.LocalDateTime;

import acme.framework.datatypes.Money;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * USE THIS IN BIND METHODS
 */
@Data
@NoArgsConstructor
public class SavePatronageDto {

	protected int			id;
	protected int			version;
	protected String		code;

	protected String		legal;

	protected Money			budget;

	protected LocalDateTime	endDate;

	protected String		info;

}
