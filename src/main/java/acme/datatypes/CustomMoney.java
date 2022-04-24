package acme.datatypes;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomMoney implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4296421217887033636L;
	
	@Digits(integer = 10, fraction = 2)	
	protected Double			amount;

	@NotBlank
	protected String			currency;

	// Object interface -------------------------------------------------------


	@Override
	public String toString() {
		StringBuilder result;

		result = new StringBuilder();
		result.append("<<");
		result.append(this.currency);
		result.append(" ");
		result.append(String.format("%.2f", this.amount));
		result.append(">>");

		return result.toString();
	}

}
