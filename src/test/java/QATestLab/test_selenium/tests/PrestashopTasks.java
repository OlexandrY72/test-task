package QATestLab.test_selenium.tests;

import org.testng.annotations.Test;

import QATestLab.test_selenium.pages.MainPage;
import QATestLab.test_selenium.pages.SearchPage;
import QATestLab.test_selenium.utils.BrowserUtils;

public class PrestashopTasks extends BrowserUtils {

	@Test
	public void justTest() throws InterruptedException {
			MainPage mainPage = new MainPage();
			mainPage.setCurrency("USD"); 			// setting new currency USD
			mainPage.comparingCurrency();			// comparing currency
			mainPage.searchGoods("dress"); 			// searching items with key "dress"
			SearchPage searchPage = new SearchPage();
			searchPage.comparingNumberOfItems(); 	// verifying number of items after search
			mainPage.comparingCurrency();			// comparing currency
			searchPage.setSortByPriceHighToLow(); 	// sorting list of items by price high to low
			searchPage.checkBounderyPriceSorting("$"); // verifying that list is sorted in the right way
			searchPage.comparingPriceDiscount(); 	// verifying that the price before and after the discount matches the indicated discount size
	}
}
