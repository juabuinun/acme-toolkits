package acme.testing.authenticated.announcement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class AuthenticatedAnnouncementListTest extends TestHarness{

	protected int actualIndex = 0;
	
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/announcement/list-announcement.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void authenticatedAnnouncementList(final String title, final String creationDate, final String body) {

		super.signIn("patron1", "patron1");
		
		if (LocalDateTime.parse(creationDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME).plus(1,ChronoUnit.MONTHS).isAfter(LocalDateTime.now())) {

			super.navigateHome();
			super.clickOnMenu("Announcements", "View announcements");

			super.checkListingExists();
			super.sortListing(0, "asc");

			super.checkColumnHasValue(this.actualIndex, 0, creationDate);
			super.checkColumnHasValue(this.actualIndex, 1, title);
			super.checkColumnHasValue(this.actualIndex, 2, body);

			
			this.actualIndex++;
		}

		super.signOut();
	}
}
