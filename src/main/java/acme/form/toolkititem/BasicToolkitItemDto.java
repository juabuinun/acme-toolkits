package acme.form.toolkititem;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasicToolkitItemDto {
	
	protected int id;
	protected int quantity = 1;
}
