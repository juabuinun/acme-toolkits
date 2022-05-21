
package acme.entities.toolkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
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

import acme.entities.toolkititem.ToolkitItem;
import acme.framework.datatypes.Money;
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

	public Toolkit() {
		this.items = new ArrayList<>();
		this.published = false;
	}
	
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "toolkit", cascade = CascadeType.ALL, orphanRemoval = true)
	protected List<ToolkitItem>	items;

	protected boolean			published;

	@SuppressWarnings("unused")
	private void setItems(final List<ToolkitItem> items) {
		this.items = items;
	}

	@Transient
	public String getPrice() {
		String res;
		try {
			final Map<String, Double> prices = new HashMap<>();
			this.items.stream().forEach(i -> {
				final Money price = i.getItem().getPrice();
				price.setAmount(price.getAmount() * i.getQuantity());
				if (prices.containsKey(price.getCurrency())) {
					prices.put(price.getCurrency(), prices.get(price.getCurrency()) + price.getAmount());
				} else {
					prices.put(price.getCurrency(), price.getAmount());
				}
			});

			res = prices.entrySet().stream().map(e -> String.format("%s %.2f", e.getKey(), e.getValue())).collect(Collectors.joining(", "));
		} catch (final Exception e) {
			res = "";
		}
		return res;
	}
}
