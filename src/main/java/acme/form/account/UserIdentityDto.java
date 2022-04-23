package acme.form.account;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserIdentityDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7134165788281021645L;

	protected String fullName;
	protected String name;
	protected String surname;
	protected String email;
}
