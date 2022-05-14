
package acme.entities.toolkit;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.toolkit.item.ToolkitItem;
import acme.framework.entities.AbstractEntity;
import acme.roles.Inventor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Toolkit extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6936012221192441560L;

	@Column(unique = true)
	@NotNull
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$")
	protected String			code;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			title;

	@NotBlank
	@Length(min = 1, max = 255)
	protected String			description;

	@NotBlank
	@Length(min = 1, max = 255)
	protected String			notes;

	@ManyToOne(optional = false)
	@NotNull
	protected Inventor			owner;

	@URL
	protected String			info;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "toolkit")
	protected List<ToolkitItem>	items;


	@Transient
	public Double getPrice() {
		//currencies can differ, not worth checking. what would even be done if they differ?
		return this.items.stream().mapToDouble(ti -> ti.getItem().getPrice().getAmount() * ti.getQuantity()).sum();
	}
}
