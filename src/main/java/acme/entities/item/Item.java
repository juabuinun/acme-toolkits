
package acme.entities.item;

import java.util.ArrayList;
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

import acme.entities.toolkititem.ToolkitItem;
import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import acme.roles.Inventor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Item extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5776904326587526514L;
	
	public enum Type {

		COMPONENT("item.component"), TOOL("item.tool");


		private String label;


		Type(final String label) {
			this.label = label;
		}

		public String getLabel() {
			return this.label;
		}

		public static Type of(final String label) {
			for (final Type b : Type.values()) {
				if (b.label.equalsIgnoreCase(label)) {
					return b;
				}
			}
			return null;
		}
	}
	
	public Item() {
		this.toolkits = new ArrayList<>();
		this.published = false;
	}

	@Length(min = 1, max = 100)
	@NotBlank
	protected String			name;

	@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$")
	@NotNull
	@Column(unique = true)
	protected String			code;

	@NotNull
	@Enumerated(EnumType.STRING)
	protected Type				itemType;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			technology;

	@NotBlank
	@Length(min = 1, max = 255)
	protected String			description;

	@Valid
	@NotNull
	protected Money				price;

	@ManyToOne(optional = false)
	@NotNull
	protected Inventor			owner;

	@URL
	protected String			info;

	protected boolean			published;

	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	protected List<ToolkitItem>	toolkits;
}
