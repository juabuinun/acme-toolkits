package acme.form.announcement;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnnouncementDto {

	protected String title;
	protected String body;
	protected Boolean critical;
	protected LocalDateTime creationDate;
	protected String link;
}
