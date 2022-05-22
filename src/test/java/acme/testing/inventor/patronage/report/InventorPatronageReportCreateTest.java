
package acme.testing.inventor.patronage.report;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorPatronageReportCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/patronage/report/create-positive-report.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveInventorPatronageReportCreate(final int patronageIndex, final String memorandum, final String info, final String confirm) {

		super.signIn("inventor2", "inventor2");

		super.navigateHome();

		super.clickOnMenu("Patronages", "View mine");
		super.sortListing(0, "desc");
		this.clickOnListingRecord(patronageIndex);
		super.clickOnButton("Create report");
		
		super.checkFormExists();
		super.fillInputBoxIn("memorandum", memorandum);
		super.fillInputBoxIn("info", info);
		super.fillInputBoxIn("confirmation", confirm);
		
		super.clickOnSubmit("Create");
		
		super.checkFormExists();
		super.clickOnButton("View reports");
		super.sortListing(0, "desc");
		super.clickOnListingRecord(0);
		
		super.checkFormExists();
		super.checkInputBoxHasValue("memorandum", memorandum);
		super.checkInputBoxHasValue("info", info);
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/patronage/report/create-negative-report.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeInventorPatronageReportCreate(final int patronageIndex, final String memorandum, final String info, final String confirm) {

		super.signIn("inventor2", "inventor2");

		super.navigateHome();

		super.clickOnMenu("Patronages", "View mine");
		super.sortListing(0, "desc");
		this.clickOnListingRecord(patronageIndex);
		super.clickOnButton("Create report");
		
		super.checkFormExists();
		super.fillInputBoxIn("memorandum", memorandum);
		super.fillInputBoxIn("info", info);
		super.fillInputBoxIn("confirmation", confirm);
		
		super.clickOnSubmit("Create");
		super.checkErrorsExist();
		
		super.signOut();
	}
}
