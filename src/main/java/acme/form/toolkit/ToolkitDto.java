
package acme.form.toolkit;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToolkitDto implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3109044278493738098L;

	protected int				id;
	protected int				version;
	protected String			code;
	protected String			title;
	protected String			description;
	protected String			notes;
	protected String			info;
	protected Double			price;
	protected boolean			published;
}
