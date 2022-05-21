package acme.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpamWordDto {

	protected String word;

	protected boolean strong;
	
	protected String language;
}
