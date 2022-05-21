
package acme.entities.toolkititem;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import acme.entities.item.Item;
import acme.entities.toolkit.Toolkit;
import acme.framework.entities.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ToolkitItem extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7542796390341916652L;
	
	public ToolkitItem(final Item item) {
		this.item = item;
		this.quantity = 1;
	}

	@NotNull
	@Positive
	protected Integer			quantity;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@NotNull
	protected Item				item;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@NotNull
	protected Toolkit			toolkit;


}
