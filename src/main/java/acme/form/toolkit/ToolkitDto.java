package acme.form.toolkit;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToolkitDto {
	
	protected int				id;
	protected int				version;
	protected String			code;
	protected String			title;
	protected String			description;
	protected String			notes;
	protected String			info;
	protected String			price;
}
