package acme.form.patronagereport;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SavePatronageReportDto{
	

	protected int id;
	protected int version;
	protected String memorandum;
	protected String info;
}
