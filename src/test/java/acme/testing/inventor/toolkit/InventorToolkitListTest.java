
package acme.testing.inventor.toolkit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class InventorToolkitListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/toolkit/list-toolkit.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void inventorToolkitsList(final int toolkitIndex, final String code, final String title, final String price, final String published) {

		super.navigateHome();
		super.clickOnMenu("Toolkits", "View toolkits");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(toolkitIndex, 0, code);
		super.checkColumnHasValue(toolkitIndex, 1, title);
		super.checkColumnHasValue(toolkitIndex, 2, price);
		super.checkColumnHasValue(toolkitIndex, 3, published);
	}

}
