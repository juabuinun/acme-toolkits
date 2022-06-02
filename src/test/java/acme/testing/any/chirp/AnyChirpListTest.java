
package acme.testing.any.chirp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class AnyChirpListTest extends TestHarness {

	protected int actualIndex = 0;
	
	@ParameterizedTest
	@CsvFileSource(resources = "/any/chirp/list-chirp.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(1)
	public void anyChirpList(final int recordIndex, final String creationDate, final String title, final String author, final String body, final String email) {

		if (LocalDateTime.parse(creationDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME).plus(1,ChronoUnit.MONTHS).isAfter(LocalDateTime.now())) {

			super.navigateHome();
			super.clickOnMenu("Chirps", "View chirps");

			super.checkListingExists();
			super.sortListing(0, "asc");

			super.checkColumnHasValue(this.actualIndex, 0, creationDate);
			super.checkColumnHasValue(this.actualIndex, 1, title);
			super.checkColumnHasValue(this.actualIndex, 2, author);
			super.checkColumnHasValue(this.actualIndex, 3, body);
			super.checkColumnHasValue(this.actualIndex, 4, email);
			
			this.actualIndex++;
		}

	}

}
