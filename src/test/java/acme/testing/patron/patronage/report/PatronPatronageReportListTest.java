
package acme.testing.patron.patronage.report;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PatronPatronageReportListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/patron/patronage/report/list-patronage-report.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void patronPatronageReportList(final int patronageIndex, final int index, final String date, final String sequence, final String memorandum) {

		super.signIn("patron1", "patron1");

		super.navigateHome();

		super.clickOnMenu("Patronages", "View mine");
		super.checkListingExists();
		super.sortListing(0, "desc");
		
		super.clickOnListingRecord(patronageIndex);
		super.clickOnButton("View reports");
		
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(index, 0, sequence);
		super.checkColumnHasValue(index, 1, date);
		this.clickOnListingRecord(index);

		super.checkFormExists();
		super.checkInputBoxHasValue("memorandum", memorandum);

		super.signOut();

	}
}
