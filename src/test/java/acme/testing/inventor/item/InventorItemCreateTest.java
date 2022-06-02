
package acme.testing.inventor.item;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.entities.item.Item;
import acme.testing.TestHarness;

public class InventorItemCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/item/create-positive-item.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveInventorItemCreate(final String itemType, final String name, final String code, final String technology, final String description, final String price, final String info) {
		super.signIn("inventor1", "inventor1");

		super.navigateHome();
		if (Item.Type.TOOL.getLabel().equals(itemType)) {
			super.clickOnMenu("Tools", "Create");
		} else if (Item.Type.COMPONENT.getLabel().equals(itemType)) {
			super.clickOnMenu("Components", "Create");
		}
		super.checkFormExists();
		super.fillInputBoxIn("name", name);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("technology", technology);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("price", price);

		super.clickOnSubmit("Create");

		super.navigateHome();
		if (Item.Type.TOOL.getLabel().equals(itemType)) {
			super.clickOnMenu("Tools", "View mine");
		} else if (Item.Type.COMPONENT.getLabel().equals(itemType)) {
			super.clickOnMenu("Components", "View mine");
		}
		
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.checkColumnHasValue(0, 0, code);
		super.checkColumnHasValue(0, 1, name);
		super.checkColumnHasValue(0, 2, technology);
		super.checkColumnHasValue(0, 3, price);
		super.checkColumnHasValue(0, 4, "false");

		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/item/create-negative-item.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeInventorItemCreate(final String itemType, final String name, final String code, final String technology, final String description, final String price, final String info) {
		super.signIn("inventor1", "inventor1");

		super.navigateHome();
		if (Item.Type.TOOL.getLabel().equals(itemType)) {
			super.clickOnMenu("Tools", "Create");
		} else if (Item.Type.COMPONENT.getLabel().equals(itemType)) {
			super.clickOnMenu("Components", "Create");
		}
		super.checkFormExists();
		super.fillInputBoxIn("name", name);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("technology", technology);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("price", price);

		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}
}
