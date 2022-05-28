package acme.form.chimpum;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SaveChimpumDto {

	int id;
	int version;
	protected String code;
	protected LocalDate startDate;
	protected LocalDate endDate;
	protected String title;
	protected String description;
	protected String info;
	
}
