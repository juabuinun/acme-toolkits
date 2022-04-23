package acme.form.account;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserAccountDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8148117901368307173L;
	
	public UserAccountDto() {
		this.professionalProfile = new ProfessionalProfileDto();
	}

	protected int id;
	protected int version;
	protected String			username;
	protected UserIdentityDto identity;
	protected String authorityString;
	protected ProfessionalProfileDto professionalProfile;
	
}
