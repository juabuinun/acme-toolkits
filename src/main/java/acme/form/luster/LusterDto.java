package acme.form.luster;

import java.time.LocalDate;
import java.time.LocalDateTime;

import acme.framework.datatypes.Money;
import lombok.Data;

@Data
public class LusterDto {

	int id;
	int version;
	protected String code;
	protected LocalDateTime creationDate;
	protected LocalDate startDate;
	protected LocalDate endDate;
	protected String title;
	protected String description;
	protected Money budget;
	protected String info;
	protected String duration;
	
}
