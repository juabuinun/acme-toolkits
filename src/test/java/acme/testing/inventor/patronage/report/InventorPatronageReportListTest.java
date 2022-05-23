
package acme.testing.inventor.patronage.report;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorPatronageReportListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/patronage/report/list-patronage-report.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void inventorPatronageReportList(final int reportIndex, final int recordIndex, final String creationDate, final String sequenceNumber,final String memorandum) {

		super.signIn("inventor2", "inventor2");

		super.navigateHome();

		super.clickOnMenu("Patronages", "View mine");
		super.checkListingExists();
		super.sortListing(0, "desc");
		this.clickOnListingRecord(reportIndex);
		super.clickOnButton("View reports");
		super.checkListingExists();

		super.sortListing(1, "asc");
	
		super.checkColumnHasValue(recordIndex, 0, sequenceNumber);
		super.checkColumnHasValue(recordIndex, 1, creationDate);
		this.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("memorandum", memorandum);

		super.signOut();
	}
}
