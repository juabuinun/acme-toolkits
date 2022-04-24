
package acme.testing.patron.patronage.report;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PatronPatronageReportListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/patron/patronage/report/list-patronage-report.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void patronPatronageReportList(final int reportIndex, final int recordIndex, final String creationDate, final String sequenceNumber,final String memorandum) {

		super.signIn("patron1", "patron1");

		super.navigateHome();

		super.clickOnMenu("Patronages", "View mine");
		this.clickOnListingRecord(reportIndex);
		super.clickOnButton("View reports");

		super.sortListing(1, "asc");
	
		super.checkColumnHasValue(recordIndex, 0, sequenceNumber);
		super.checkColumnHasValue(recordIndex, 1, creationDate);
		this.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("memorandum", memorandum);

		super.signOut();

	}
}
