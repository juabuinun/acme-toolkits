
package acme.testing.spam;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class SpamFilterTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/spam/spam.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void spamFilterTest(final String body, final boolean expected) {
		super.clickOnMenu("Chirps", "Create");

		super.fillInputBoxIn("title", "test");
		super.fillInputBoxIn("author", "test");
		super.fillInputBoxIn("body", body.replace("\\n", "\n"));
		super.fillInputBoxIn("confirmation", "true");
		super.clickOnSubmit("Create");

		if (!expected) {
			super.checkErrorsExist();
		} else {
			super.clickOnMenu("Chirps", "View chirps");
			super.checkListingExists();
			super.sortListing(0, "desc");

			super.checkColumnHasValue(0, 3, body);
		}
	}
}
