
package acme.form.toolkit;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasicToolkitDto{


	protected int				id;
	protected int				version;
	protected String			code;
	protected String			title;
	protected String			price;
	protected boolean			published;
}
