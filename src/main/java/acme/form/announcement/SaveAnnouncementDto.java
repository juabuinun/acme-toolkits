package acme.form.announcement;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveAnnouncementDto {

	protected String title;
	protected String body;
	protected Boolean critical;
	protected String link;
}
