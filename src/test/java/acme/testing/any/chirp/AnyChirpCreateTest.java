
package acme.testing.any.chirp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class AnyChirpCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/chirp/create-positive-chirp.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(2)
	public void positiveAnyChirpCreate(final int index, final String title, final String author, final String body, final String email, final String confirm) {
		super.clickOnMenu("Chirps", "Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("confirmation", confirm);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Chirps", "View chirps");
		super.checkListingExists();
		super.sortListing(0, "desc");

		super.checkColumnHasValue(0, 1, title);
		super.checkColumnHasValue(0, 2, author);
		super.checkColumnHasValue(0, 3, body);
		super.checkColumnHasValue(0, 4, email);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/any/chirp/create-negative-chirp.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(3)
	public void negativeAnyChirpCreate(final int index, final String title, final String author, final String body, final String email, final String confirm) {
		super.clickOnMenu("Chirps", "Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("confirmation", confirm);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();
	}

}
