package acme.form.patronagereport;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatronageReportDto{
	

	protected int id;
	protected int version;
	protected LocalDateTime creationDate;
	protected String memorandum;
	protected String info;

	public String sequenceNumber;
	protected int patronageId;
}
