package acme.form.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserIdentityDto {

	protected String fullName;
	protected String name;
	protected String surname;
	protected String email;
}
