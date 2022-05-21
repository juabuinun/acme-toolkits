package acme.testing.patron.patronage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PatronPatronagePublishTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/patron/patronage/create-positive-patronage.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positivePatronPatronagePublish(final int userIndex, final String code, final String end, final String legal,final String budget, final String info) {
		super.signIn("patron1", "patron1");
		
		super.navigateHome();
		super.clickOnMenu("Users", "View users");
		super.clickOnListingRecord(userIndex);
		super.clickOnButton("Sponsor");
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("endDate", LocalDateTime.parse(end,DateTimeFormatter.ISO_LOCAL_DATE_TIME).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
		super.fillInputBoxIn("legal", legal);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("info", info);
		
		super.clickOnSubmit("Create");
		
		super.clickOnMenu("Patronages", "View mine");
		super.sortListing(2, "asc");
		
		super.checkColumnHasValue(0, 0, code);
		super.checkColumnHasValue(0, 1, "PROPOSED");
		super.checkColumnHasValue(0, 4, budget);
		super.checkColumnHasValue(0, 5, "false");
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/patron/patronage/create-negative-patronage.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativePatronPatronagePublish(final int userIndex, final String code, final String end, final String legal,final String budget, final String info) {
		super.signIn("patron1", "patron1");
		
		super.navigateHome();
		super.clickOnMenu("Users", "View users");
		super.clickOnListingRecord(userIndex);
		super.clickOnButton("Sponsor");
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("endDate", LocalDateTime.parse(end,DateTimeFormatter.ISO_LOCAL_DATE_TIME).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
		super.fillInputBoxIn("legal", legal);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("info", info);
		
		super.clickOnSubmit("Create");
		super.checkErrorsExist();
	}
}
