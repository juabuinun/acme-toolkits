package acme.form.account;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfessionalProfileDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2216308845562942990L;
	
	protected String company;
	protected String statement;
	protected String info;
}
