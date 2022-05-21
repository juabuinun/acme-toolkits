
package acme.form.toolkit;

import java.util.ArrayList;
import java.util.List;

import acme.form.toolkititem.ToolkitItemDto;
import lombok.Data;

@Data
public class DetailToolkitDto {


	protected int					id;
	protected int					version;
	protected String				code;
	protected String				title;
	protected String				description;
	protected String				price;
	protected String				notes;
	protected String				info;
	
	protected List<ToolkitItemDto>	availableComponents;
	protected List<ToolkitItemDto>	bindedComponents;
	protected List<ToolkitItemDto>	availableTools;
	protected List<ToolkitItemDto>	bindedTools;
	
	public DetailToolkitDto() {
		this.availableComponents = new ArrayList<>();
		this.bindedComponents = new ArrayList<>();
		this.availableTools = new ArrayList<>();
		this.bindedTools = new ArrayList<>();
	}
}
