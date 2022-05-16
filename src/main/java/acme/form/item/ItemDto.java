package acme.form.item;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.datatypes.Money;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2748923805521320960L;
	
	protected int id;
	protected int version;

	@Length(min=1,max=100)
	@NotBlank
	protected String name;
	
	@Pattern(regexp = "[A-Z]{3}-[0-9]{3}(-[A-Z])?$")
	@NotNull
	@Column(unique = true)
	protected String code;
	
	@NotBlank
	@Length(min=1,max=100)
	protected String technology;
	
	@NotBlank
	@Length(min=1,max=255)
	protected String description;
	
	@Valid
	@NotNull
	protected  Money price;
	
	@URL
	protected String info;

}
