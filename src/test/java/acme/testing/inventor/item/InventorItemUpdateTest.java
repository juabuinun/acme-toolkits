package acme.testing.inventor.item;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.entities.item.Item;
import acme.testing.TestHarness;

public class InventorItemUpdateTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/item/update-positive-item.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(101)
	public void positiveInventorItemUpdate(final int index, final String itemType, final String name, final String technology, final String description, final String price, final String info) {
		super.signIn("inventor1", "inventor1");

		super.navigateHome();
		if (Item.Type.TOOL.getLabel().equals(itemType)) {
			super.clickOnMenu("Tools", "View mine");
		} else if (Item.Type.COMPONENT.getLabel().equals(itemType)) {
			super.clickOnMenu("Components", "View mine");
		}
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(index);
		
		super.checkFormExists();
		super.optionalValue("name", name, (i, v) -> super.fillInputBoxIn(i, v));
//		super.optionalValue("code", code, (i, v) -> super.fillInputBoxIn(i, v));
		super.optionalValue("technology", technology, (i, v) -> super.fillInputBoxIn(i, v));
		super.optionalValue("description", description, (i, v) -> super.fillInputBoxIn(i, v));
		super.optionalValue("price", price, (i, v) -> super.fillInputBoxIn(i, v));

		super.clickOnSubmit("Update");

		super.navigateHome();
		if (Item.Type.TOOL.getLabel().equals(itemType)) {
			super.clickOnMenu("Tools", "View mine");
		} else if (Item.Type.COMPONENT.getLabel().equals(itemType)) {
			super.clickOnMenu("Components", "View mine");
		}

		super.checkListingExists();
		super.sortListing(0, "desc");
//		super.checkColumnHasValue(0, 0, code);
		super.optionalValue(index, 1, name, (r,c,v) -> super.checkColumnHasValue(r, c, v));
		super.optionalValue(index, 2, technology, (r,c,v) -> super.checkColumnHasValue(r, c, v));
		super.optionalValue(index, 3, price, (r,c,v) -> super.checkColumnHasValue(r, c, v));
		super.checkColumnHasValue(index, 4, "false");

		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/item/update-negative-item.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(80)
	public void negativeInventorItemUpdate(final int index, final String itemType, final String name, final String technology, final String description, final String price, final String info) {
		super.signIn("inventor1", "inventor1");

		super.navigateHome();
		if (Item.Type.TOOL.getLabel().equals(itemType)) {
			super.clickOnMenu("Tools", "View mine");
		} else if (Item.Type.COMPONENT.getLabel().equals(itemType)) {
			super.clickOnMenu("Components", "View mine");
		}
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(index);
		
		super.checkFormExists();
		super.optionalValue("name", name, (i, v) -> super.fillInputBoxIn(i, v));
//		super.optionalValue("code", code, (i, v) -> super.fillInputBoxIn(i, v));
		super.optionalValue("technology", technology, (i, v) -> super.fillInputBoxIn(i, v));
		super.optionalValue("description", description, (i, v) -> super.fillInputBoxIn(i, v));
		super.optionalValue("price", price, (i, v) -> super.fillInputBoxIn(i, v));

		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}
}
