package acme.testing.patron.patronage;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PatronPatronagePublishTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/patron/patronage/publish-patronage.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(100)
	public void positivePatronPatronagePublish(final int index,  final String end, final String legal, final String budget, final String info) {
		super.signIn("patron4", "patron4");

		super.navigateHome();
		super.clickOnMenu("Patronages", "View mine");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(index);
		super.optionalValue("endDate", end, (i,v)-> super.fillInputBoxIn(i, v));
		super.optionalValue("legal", legal, (i,v)-> super.fillInputBoxIn(i, v));
		super.optionalValue("budget", budget, (i,v)-> super.fillInputBoxIn(i, v));
		super.optionalValue("info", info, (i,v)-> super.fillInputBoxIn(i, v));

		super.clickOnSubmit("Publish");
		
		super.clickOnMenu("Patronages", "View mine");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.checkColumnHasValue(index, 1, "patronage.status.proposed");
		super.optionalValue(index, 3, end, (r,c,v) -> super.checkColumnHasValue(r, c, v));
		super.optionalValue(index, 4, budget, (r,c,v) -> super.checkColumnHasValue(r, c, v));
		
		super.signOut();
	}
	
}
