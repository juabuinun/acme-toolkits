
package acme.form.toolkititem;

import acme.form.item.ItemDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToolkitItemDto {
	
	protected ItemDto	item;
	protected int	quantity = 1;
}
