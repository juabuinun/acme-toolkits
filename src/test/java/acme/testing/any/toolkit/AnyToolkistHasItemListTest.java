
package acme.testing.any.toolkit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class AnyToolkistHasItemListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/toolkit/list-toolkit-with-item.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveListToolkitWithItemTest(final int itemIndex,final int toolkitIndex, final String code, final String itemCode, final String itemType, final String title, final String description) {

			super.navigateHome();
			super.clickOnMenu("Components", String.format("View %s", itemType));

			super.checkListingExists();
			super.sortListing(0, "desc");
			
			super.clickOnListingRecord(itemIndex);
			super.checkInputBoxHasValue("code", itemCode);
			
			super.clickOnButton("View toolkits");
			
			super.checkListingExists();
			super.sortListing(0, "asc");
			
			
			super.checkColumnHasValue(toolkitIndex, 0, code);
			super.checkColumnHasValue(toolkitIndex, 1, title);

		}

}
