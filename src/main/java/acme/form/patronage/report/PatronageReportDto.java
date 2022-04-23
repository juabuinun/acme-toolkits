package acme.form.patronage.report;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatronageReportDto implements Serializable {
	
	private static final long serialVersionUID = 7611882032972355650L;

	protected int id;
	protected int version;
	protected LocalDateTime creationDate;
	protected String memorandum;
	protected String info;

	public String sequenceNumber;
	protected int patronageId;
}
