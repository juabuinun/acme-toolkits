/*
 * TestHarness.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.hibernate.internal.util.StringHelper;

import acme.components.formatter.LocalDateTimeFormatter;
import acme.framework.testing.AbstractTest;

public abstract class TestHarness extends AbstractTest {
	
	DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	protected void signIn(final String username, final String password) {
		assert !StringHelper.isBlank(username);
		assert !StringHelper.isBlank(password);
		
		super.navigateHome();
		super.clickOnMenu("Sign in");
		super.fillInputBoxIn("username", username);
		super.fillInputBoxIn("password", password);
		super.fillInputBoxIn("remember", "true");
		super.clickOnSubmit("Sign in");
		super.checkCurrentPath("/master/welcome");
		super.checkLinkExists("Account");
	}

	protected void signOut() {
		super.navigateHome();
		super.clickOnMenu("Sign out");
		super.checkCurrentPath("/master/welcome");
		super.checkNotLinkExists("Account");
	}

	protected void signUp(final String username, final String password, final String name, final String surname, final String email) {
		assert !StringHelper.isBlank(username);
		assert !StringHelper.isBlank(password);
		assert !StringHelper.isBlank(name);
		assert !StringHelper.isBlank(surname);
		assert !StringHelper.isBlank(email);

		super.navigateHome();
		super.clickOnMenu("Sign up");	
		super.fillInputBoxIn("username", username);
		super.fillInputBoxIn("password", password);
		super.fillInputBoxIn("confirmation", password);
		super.fillInputBoxIn("identity.name", name);
		super.fillInputBoxIn("identity.surname", surname);
		super.fillInputBoxIn("identity.email", email);		
		super.fillInputBoxIn("accept", "true");
		super.clickOnSubmit("Sign up");
		super.checkCurrentPath("/master/welcome");
		super.checkNotLinkExists("Account");
	}
	
	protected String parseDateTime(final String string, final Locale locale) {
		final LocalDateTime ldt = LocalDateTime.parse(string,DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		return ldt.format(LocalDateTimeFormatter.getFormatter(locale));
	}

}
