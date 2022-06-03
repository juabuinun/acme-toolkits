
package acme.testing.inventor.luster;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorLusterCreateTest extends TestHarness {

	public static LocalDate referenceDate = LocalDate.parse("2022-06-10");
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-positive-chimpum.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveInventorLusterCreate(final int itemIndex, final String title, final String description, final String start, final String end, final String budget, final String info) {
		super.signIn("inventor1", "inventor1");

		super.navigateHome();
		super.clickOnMenu("Components", "View mine");

		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(itemIndex);

		super.clickOnButton("Create luster");
		super.checkFormExists();
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("info", info);
		
		super.fillInputBoxIn("startDate", InventorLusterCreateTest.referenceDate.plusDays(Long.valueOf(start)).format(DateTimeFormatter.ISO_LOCAL_DATE));
		super.fillInputBoxIn("endDate", InventorLusterCreateTest.referenceDate.plusDays(Long.valueOf(end)).format(DateTimeFormatter.ISO_LOCAL_DATE));
		
		super.clickOnSubmit("Create");
		
		super.navigateHome();
		super.clickOnMenu("Components", "View mine");

		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(itemIndex);
		super.checkFormExists();
		super.clickOnButton("View luster");

		super.checkListingExists();
		super.sortListing(0, "desc");
		super.checkColumnHasValue(0, 2, title);
		super.checkColumnHasValue(0, 4, budget);
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-negative-chimpum.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeInventorLusterCreate(final int itemIndex, final String title, final String description, final String start, final String end, final String budget, final String info) {
		super.signIn("inventor1", "inventor1");

		
		
		super.navigateHome();
		super.clickOnMenu("Components", "View mine");

		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(itemIndex);

		super.clickOnButton("Create luster");
		super.checkFormExists();
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("info", info);
		
		super.fillInputBoxIn("startDate", InventorLusterCreateTest.referenceDate.plusDays(Long.valueOf(start)).format(DateTimeFormatter.ISO_LOCAL_DATE));
		super.fillInputBoxIn("endDate", InventorLusterCreateTest.referenceDate.plusDays(Long.valueOf(end)).format(DateTimeFormatter.ISO_LOCAL_DATE));
		
		super.clickOnSubmit("Create");
		
		super.checkErrorsExist();
		super.signOut();
	}
}
