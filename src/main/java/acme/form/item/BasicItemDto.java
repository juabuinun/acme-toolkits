package acme.form.item;

import acme.framework.datatypes.Money;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasicItemDto{

	
	protected int id;
	protected int version;
	protected String name;
	protected String code;
	protected String technology;
	protected  Money price;
	protected Boolean published;

}
