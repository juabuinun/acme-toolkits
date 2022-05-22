package acme.testing.patron.patronage;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PatronPatronageDeleteTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/patron/patronage/delete-patronage.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void patronPatronageDelete(final int index,  final String end, final String legal, final String budget, final String info) {
		super.signIn("patron4", "patron4");

		super.navigateHome();
		super.clickOnMenu("Patronages", "View mine");
		super.sortListing(0, "desc");
		super.clickOnListingRecord(index);
		super.optionalValue("endDate", end, (i,v)-> super.fillInputBoxIn(i, v));
		super.optionalValue("legal", legal, (i,v)-> super.fillInputBoxIn(i, v));
		super.optionalValue("budget", budget, (i,v)-> super.fillInputBoxIn(i, v));
		super.optionalValue("info", info, (i,v)-> super.fillInputBoxIn(i, v));

		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		
		super.signOut();
	}
	
}
