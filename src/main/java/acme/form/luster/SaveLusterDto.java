package acme.form.luster;

import java.time.LocalDate;

import acme.framework.datatypes.Money;
import lombok.Data;

@Data
public class SaveLusterDto {

	int id;
	int version;
	protected LocalDate endDate;
	protected String title;
	protected String description;
	protected Money budget;
	protected String info;
	
}
