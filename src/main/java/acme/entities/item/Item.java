package acme.entities.item;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.toolkit.item.ToolkitItem;
import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import acme.roles.Inventor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class Item extends AbstractEntity{

	public enum Type{
		COMPONENT, TOOL
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -5776904326587526514L;

	@Length(min=1,max=100)
	@NotBlank
	protected String name;
	
	@Pattern(regexp = "[A-Z]{3}-[0-9]{3}(-[A-Z])?$")
	@NotNull
	@Column(unique = true)
	protected String code;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	protected Type itemType;
	
	@NotBlank
	@Length(min=1,max=100)
	protected String technology;
	
	@NotBlank
	@Length(min=1,max=255)
	protected String description;
	
	@Valid
	@NotNull
	protected  Money price;
	
	@ManyToOne(optional=false)
	@NotNull
	protected Inventor owner;
	
	@URL
	protected String info;
	
	@OneToMany(mappedBy="item", fetch=FetchType.LAZY)
	protected List<ToolkitItem> toolkits;
}
