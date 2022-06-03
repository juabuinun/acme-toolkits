package acme.testing.inventor.luster;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumDeleteTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/delete-chimpum.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void inventorChimpumDelete(final int index, final String title, final String description, final String end, final String budget, final String info) {
		super.signIn("inventor1", "inventor1");

		super.navigateHome();
		super.clickOnMenu("Chimpums", "View mine");

		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(index);
		super.checkFormExists();
		
		super.optionalValue("title", title, (i,v)-> super.fillInputBoxIn(i, v));
		super.optionalValue("description", description, (i,v)-> super.fillInputBoxIn(i, v));
		super.optionalValue("budget", budget, (i,v)-> super.fillInputBoxIn(i, v));
		super.optionalValue("info", info, (i,v)-> super.fillInputBoxIn(i, v));

		String endStr = "";
		try {
			endStr = LocalDate.now().plusDays(Long.valueOf(end)).format(DateTimeFormatter.ISO_LOCAL_DATE);
		}catch(final Exception e) {
			endStr = "*";
		}
		super.optionalValue("endDate", endStr, (i,v)-> super.fillInputBoxIn(i, v));
		
		super.clickOnSubmit("Delete");
		
		super.checkNotErrorsExist();
		super.signOut();
	}
}
