
package acme.testing.any.toolkit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class AnyToolkitListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/toolkit/list-toolkit.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(4)
	public void anyToolkitsList(final int toolkitIndex, final String code, final String title) {

		super.navigateHome();
		super.clickOnMenu("Toolkits", "View toolkits");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(toolkitIndex, 0, code);
		super.checkColumnHasValue(toolkitIndex, 1, title);
	}

}
