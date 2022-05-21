package acme.form.chirp;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChirpDto {

	@NotNull
	@Past
	protected LocalDateTime		creationDate;

	@NotBlank
	@Length(max = 100)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			author;

	@NotBlank
	@Length(max = 255)
	protected String			body;

	@Email
	protected String			email;
}
