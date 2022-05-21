package acme.form.account;

import lombok.Data;

@Data
public class UserAccountDto{

	
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
