package acme.form.announcement;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAnnouncementDto {

	protected String title;
	protected LocalDateTime creationDate;
	protected String body;
	protected Boolean critical;
	protected String link;
}
