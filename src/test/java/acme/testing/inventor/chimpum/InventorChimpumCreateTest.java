
package acme.testing.inventor.chimpum;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-positive-chimpum.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveInventorChimpumCreate(final int itemIndex, final String code, final String title, final String description, final String start, final String end, final String budget, final String info) {
		super.signIn("inventor1", "inventor1");

		super.navigateHome();
		super.clickOnMenu("Components", "View mine");

		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(itemIndex);

		super.clickOnButton("Create chimpum");
		super.checkFormExists();
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("info", info);
		
		super.fillInputBoxIn("startDate", LocalDate.now().plusDays(Long.valueOf(start)).format(DateTimeFormatter.ISO_LOCAL_DATE));
		super.fillInputBoxIn("endDate", LocalDate.now().plusDays(Long.valueOf(end)).format(DateTimeFormatter.ISO_LOCAL_DATE));
		
		super.clickOnSubmit("Create");
		
		super.navigateHome();
		super.clickOnMenu("Components", "View mine");

		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(itemIndex);
		super.checkFormExists();
		super.clickOnButton("View chimpums");

		super.checkListingExists();
		super.sortListing(0, "desc");
		super.checkColumnHasValue(0, 1, code);
		super.checkColumnHasValue(0, 2, title);
		super.checkColumnHasValue(0, 4, budget);
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-negative-chimpum.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeInventorChimpumCreate(final int itemIndex, final String code, final String title, final String description, final String start, final String end, final String budget, final String info) {
		super.signIn("inventor1", "inventor1");

		super.navigateHome();
		super.clickOnMenu("Components", "View mine");

		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(itemIndex);

		super.clickOnButton("Create chimpum");
		super.checkFormExists();
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("info", info);
		
		super.fillInputBoxIn("startDate", LocalDate.now().plusDays(Long.valueOf(start)).format(DateTimeFormatter.ISO_LOCAL_DATE));
		super.fillInputBoxIn("endDate", LocalDate.now().plusDays(Long.valueOf(end)).format(DateTimeFormatter.ISO_LOCAL_DATE));
		
		super.clickOnSubmit("Create");
		
		super.checkErrorsExist();
		super.signOut();
	}
}
