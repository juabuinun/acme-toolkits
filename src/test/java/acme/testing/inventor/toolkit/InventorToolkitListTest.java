
package acme.testing.inventor.toolkit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class InventorToolkitListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/toolkit/list-toolkit.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void inventorToolkitsList(final int index, final String code, final String title, final String published) {
		super.signIn("inventor1", "inventor1");
		super.navigateHome();
		
		super.clickOnMenu("Toolkits", "View mine");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(index, 0, code);
		super.checkColumnHasValue(index, 1, title);
		super.checkColumnHasValue(index, 3, published);
		
		super.signOut();
	}

}
