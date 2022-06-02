
package acme.testing.administrator.announcement;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class AdministratorAnnouncementCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/announcement/create-positive-announcement.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(7)
	public void positiveAdministratorAnnouncementCreate(final String title, final String body, final String link, final String critical, final String confirm) {
		super.signIn("administrator", "administrator");

		super.clickOnMenu("Announcements", "Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("critical", critical);
		super.fillInputBoxIn("confirmation", confirm);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Announcements", "View announcements");
		super.checkListingExists();
		super.sortListing(0, "desc");

		super.checkColumnHasValue(0, 1, title);
		super.checkColumnHasValue(0, 2, body);
		
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/announcement/create-negative-announcement.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(8)
	public void negativeAdministratorAnnouncementCreate(final String title, final String body, final String link, final String critical, final String confirm) {
		super.signIn("administrator", "administrator");

		super.clickOnMenu("Announcements", "Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("critical", critical);
		super.fillInputBoxIn("confirmation", confirm);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();
		
		super.signOut();
	}
}
