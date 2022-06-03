package acme.form.chimpum;

import java.time.LocalDate;

import acme.framework.datatypes.Money;
import lombok.Data;

@Data
public class NewChimpumDto {

	int id;
	int version;
	protected String code;
	protected LocalDate startDate;
	protected LocalDate endDate;
	protected String title;
	protected String description;
	protected Money budget;
	protected String info;
	
}
