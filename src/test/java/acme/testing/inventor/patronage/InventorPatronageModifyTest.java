
package acme.testing.inventor.patronage;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorPatronageModifyTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/patronage/modify-patronage.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void patronPatronageModify(final int index, final String code, final String state) {
		super.signIn("inventor1", "inventor1");
		
		super.navigateHome();
		super.clickOnMenu("Patronages", "View mine");
		
		super.sortListing(0, "asc");
		super.checkColumnHasValue(index, 0, code);
		super.checkColumnHasValue(index, 1, "PROPOSED");
		super.clickOnListingRecord(index);
		if(state.equals("ACCEPTED")) {
			super.clickOnButton("Accept");
		}else {
			super.clickOnButton("Deny");
		}
		
		super.clickOnMenu("Patronages","View mine");
		
		super.sortListing(0, "asc");
		super.checkColumnHasValue(index, 0, code);
		super.checkColumnHasValue(index, 1, state);
	}
}
